package Navigation;

import ConnectionMapbox.IMapbox;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.List;

class Offroute {

    private IMapbox geocoder = new IMapbox();
    private transformInsertion eingabetrans = new transformInsertion();

    /**
     * checks if the streetname of a point is part of the given route
     *
     * @param route route as DirectionsRoute to test if the point is part of it
     * @param point point as String to test if it is on the route
     * @return true if the point is on the route, false if not
     * @throws IOException if
     */
    protected boolean stillOnRoute(DirectionsRoute route, String point) throws IOException {
        boolean onRoute = false;

        CarmenFeature pointInfo = eingabetrans.transformPoint(point);
        // the streetname of the point
        String street = pointInfo.text();

        // all legs
        for (int j = 0; j < route.legs().size(); j++) {
            // every step per leg
            for (int i = 0; i < route.legs().get(j).steps().size(); i++) {
                // name of the street on the route
                String routeStreet = route.legs().get(j).steps().get(i).name();

                // is one of the streets in the route the same as of the point
                if (street.equals(routeStreet
                        .substring(0, Math.min(street.length(), routeStreet.length())))) {

                    // Equal Point on the route
                    Point routePoint = route
                            .legs()
                            .get(j)
                            .steps()
                            .get(i).maneuver()
                            .location();
                    List<CarmenFeature> routePointCarmen = geocoder.geocodeToAdress(routePoint);

                    // is it the street in the same city or in another
                    if (routePointCarmen.get(0).context().get(0).text().equals(pointInfo.context().get(0).text())) {
                        onRoute = true;
                    }


                }
            }
        }

        return onRoute;
    }
}
