package Navigation;

import ConnectionMapbox.IMapbox;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RouteFinder {

    private TransformInsertion transformer=new TransformInsertion();
    private IMapbox anIMapbox = new IMapbox();
    private Offroute offroute = new Offroute();

    /**
     * get the streetname of a chosen point
     *
     * @param point String of a point
     * @return the description / placeName of the point
     * @throws IOException
     */
    public String getAdress(String point) throws IOException {
        // Throws an Error if there is no insertion.
        if(point.length()==0)
        {
            throw new IllegalArgumentException("No insertion for this Waypoint");
        }

        CarmenFeature feature= transformer.transformPoint(point);
        //Throwas an Error if the Point was not found
        if(feature==null)
        {
            throw new IllegalArgumentException("The Point " +point +" was not found");
        }
        return feature.placeName();
    }

    /**
     * calculates a DirectionsRoute for the given waypoints and profile
     *
     * @param stringWaypoints List of String waypoints for the route calculation
     * @param profile profile to use for calculation (driving, driving-traffic, cycling, walking)
     * @return calculated DirectionsRoute
     * @throws IOException
     */
    public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException {

        List<Point> waypoints = new ArrayList(stringWaypoints.size());
        Point pointToAdd;
        //Transform all Waypoints from Sting into Point
        for (String waypoint: stringWaypoints) {
            waypoints.add(transformer.transformPoint(waypoint).center());
        }
        String finalProfile= transformer.transformProfile(profile);

        DirectionsRoute route=  anIMapbox.getListRoute(waypoints,finalProfile);
        getMap(waypoints,route);
        return route;
    }

    /**
     * checks if a point is on the input route
     * if its streetname is not part of the route, a new route from the input point to the destination of the inputRoute
     * will be calculated and returned
     * if the streetname of the point is on the route, the original route will be returned
     *
     * @param inputRoute route to check if the point is part of it
     * @param point point to check if it is on the route
     * @return inputRoute if the point is on the route or a new route if not
     */
    public DirectionsRoute goneAstray (DirectionsRoute inputRoute, String point) throws IOException {

        List<Point> newWaypoints = new ArrayList<>();
        boolean onroute = offroute.stillOnRoute(inputRoute, point);
        Point origin = transformer.transformPoint(point).center();
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
            DirectionsRoute newRoute = anIMapbox.getListRoute(newWaypoints, profile);
            getMap(newWaypoints, newRoute);
            return newRoute;
        }
        return inputRoute;
    }

    /**
            * checks if the streetname of a point is part of the given route
     *
     * @param route route as DirectionsRoute to test if the point is part of it
     * @param input point as String to test if it is on the route
     * @return true if the point is on the route, false if not
     * @throws IOException if
            */
    public boolean stillOnRoute(DirectionsRoute route, String input) throws IOException
    {
        return offroute.stillOnRoute(route, input);
    }


    /**
     * Open the ConnectionMapbox.IMapbox to save the map
     * @param waypoints the List of Waypoints
     * @param route the calculated route
     */
    private void getMap(List<Point> waypoints, DirectionsRoute route)
    {
        anIMapbox.getMap(waypoints, route);
    }
}
