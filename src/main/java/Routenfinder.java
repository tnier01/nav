import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Routenfinder {

    Eingabetransformator eing=new Eingabetransformator();
    Schnitstelle schnitstelle= new Schnitstelle();
    Offroute offroute = new Offroute();

    /**
     * get the streetname of a chosen point
     *
     * @param point String of a point
     * @return the description / placeName of the point
     * @throws IOException
     */
    public String getAdress(String point) throws IOException {
        if(point.length()==0)
        {
            throw new IllegalArgumentException("No insertion for the this Waypoint");
        }

        CarmenFeature feature= eing.transformPoint(point);
        if(feature==null)
        {
            throw new IllegalArgumentException("The Point " +point +" was not found");
        }
        return feature.placeName();
    }



    public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException {

        List waypoints = new ArrayList();
        Point pointToAdd;
        for (int i=0; i< stringWaypoints.size(); i++) {
            pointToAdd = eing.transformPoint(stringWaypoints.get(i)).center();
            waypoints.add(pointToAdd);
        }
        String finalProfile= eing.transformProfile(profile);


        DirectionsRoute route=  schnitstelle.getListRoute(waypoints,finalProfile);
        getMap(waypoints,route);
        return route;
    }

    /**
     *
     * @param inputRoute
     * @param point
     * @return
     */
    public DirectionsRoute goneAstray (DirectionsRoute inputRoute, String point) throws IOException {

        List<Point> newWaypoints = new ArrayList<>();
        boolean onroute = offroute.stillOnRoute(inputRoute, point);
        Point origin = eing.transformPoint(point).center();
        String profile = inputRoute.legs().get(0).steps().get(0).mode();

        if (!onroute) {
            int lastLegIndex = inputRoute
                    .legs()
                    .size()-1;
            int destinationIndex = inputRoute
                    .legs()
                    .get(lastLegIndex)
                    .steps()
                    .size()-1;
            // ad offside point as origin
            newWaypoints.add(origin);
            // add destination
            newWaypoints.add(inputRoute
                    .legs()
                    .get(lastLegIndex)
                    .steps()
                    .get(destinationIndex)
                    .maneuver()
                    .location()
            );
            DirectionsRoute newRoute = schnitstelle.getListRoute(newWaypoints, profile);
            return newRoute;
        }
        return inputRoute;
    }


    /**
     * Open the Schnitstelle to save the map
     * @param waypoints the List of Waypoints
     * @param route the calculated route
     */
    public void getMap(List<Point> waypoints, DirectionsRoute route)
    {
        schnitstelle.getMap(waypoints, route);
    }
}
