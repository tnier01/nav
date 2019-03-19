import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.List;

public class Offroute {

    Schnitstelle geocoder = new Schnitstelle();

    /**
     *
     * @param route
     * @param point
     * @return
     * @throws IOException
     */
    public boolean stillOnRoute(DirectionsRoute route, String point) throws IOException {
        boolean onRoute = false;

        List<CarmenFeature> pointInfo = geocoder.geocodeToObj(point);
        // the streetname of the point
        String street = pointInfo.get(0).text();

        System.out.println(street);
        for (int j = 0; j < route.legs().size(); j++) {

            for (int i = 0; i < route.legs().get(j).steps().size(); i++) {
                // is one of the streets in the route the same as of the point
                if (street.equals(route.legs().get(j).steps().get(i).name()
                        .substring(0, Math.min(street.length()-1, route.legs().get(j).steps().get(i).name().length()-1)))) {
                    onRoute = true;

                    Point routePoint = route
                            .legs()
                            .get(j)
                            .steps()
                            .get(i).maneuver()
                            .location();
                    List<CarmenFeature> routePointCarmen = geocoder.geocodeToAdress(routePoint);

                    // is it the street in the same city or in another
                    if (routePointCarmen.get(0).context().get(0).text() == pointInfo.get(0).context().get(0).text()) {
                        onRoute = true;
                    }



                }
            }
        }

        return onRoute;
    }
}