import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class Routenfinder {

    Eingabetransformator eing=new Eingabetransformator();
    Schnitstelle schnitstelle= new Schnitstelle();


    public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException {

        List waypoints = new ArrayList();
        Point pointToAdd;
        for (int i=0; i< stringWaypoints.size(); i++) {
            pointToAdd = eing.transformPoint(stringWaypoints.get(i));
            if(pointToAdd==null)
            {
                throw new IllegalArgumentException("The Point: " +stringWaypoints.get(i) +" was not found");
            }
            waypoints.add(pointToAdd);
        }
        String finalProfile= eing.transformProfile(profile);


        DirectionsRoute route=  schnitstelle.getListRoute(waypoints,finalProfile);
        getMap(waypoints,route);
        return route;
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
