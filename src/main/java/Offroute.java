import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;

import java.io.IOException;
import java.util.List;

public class Offroute {

    Schnitstelle geocoder = new Schnitstelle();

    public boolean stillOnRoute(DirectionsRoute route, String point) throws IOException {
        boolean onRoute = false;
        List<CarmenFeature> pointInfo = geocoder.geocodeToObj(point);
        // the streetname of the point
        String street = pointInfo.get(0).text();

        for (int j = 0; j < route.legs().size(); j++) {

            for (int i = 0; i < route.legs().get(j).steps().size() - 1; i++) {
                // is one of the streets in the route the same as of the point
                if (street.equals(route.legs().get(j).steps().get(0).name())) {
                    onRoute = true;
                }
            }
        }

        return onRoute;
    }
}
