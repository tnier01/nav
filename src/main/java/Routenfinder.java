import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class Routenfinder {

    Eingabetransformator eing=new Eingabetransformator();
    Schnitstelle schnitstelle= new Schnitstelle();

    public DirectionsRoute gibRoute(String origin, String destination, String profile) throws IOException {

        Point originPoint = eing.transformPoint(origin);
        Point destinationPoint = eing.transformPoint(destination);
        String finalProfile= eing.transformProfile(profile);

        return schnitstelle.getRoute(originPoint,destinationPoint,finalProfile);

    }

    public DirectionsRoute gibListRoute(List<String> stringWaypoints, String profile) throws IOException {

        List waypoints = new ArrayList();
        Point pointToAdd;
        for (int i=0; i< stringWaypoints.size(); i++) {
            pointToAdd = eing.transformPoint(stringWaypoints.get(i));
            waypoints.add(pointToAdd);
        }
        String finalProfile= eing.transformProfile(profile);

        return schnitstelle.getListRoute(waypoints,finalProfile);

    }


}
