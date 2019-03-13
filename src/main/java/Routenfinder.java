import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import java.io.IOException;

public class Routenfinder {

    Eingabetransformator eing=new Eingabetransformator();
    Schnitstelle schnitstelle= new Schnitstelle();

    public DirectionsRoute gibRoute(String origin, String destination, String profile) throws IOException {

        Point originPoint = eing.transformPoint(origin);
        Point destinationPonit = eing.transformPoint(destination);
        String finalProfile= eing.transfromProfile(profile);

        return schnitstelle.getRoute(originPoint,destinationPonit,finalProfile);



    }
}
