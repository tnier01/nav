package Navigation;

import ConnectionMapbox.IMapbox;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods to calculate routes out of different kinds of waypoints.
 */
public class RouteFinder {

    private TransformInsertion transformer = new TransformInsertion();
    private IMapbox anIMapbox = new IMapbox();
    private Offroute offroute = new Offroute();

    /**
     * Get the streetname of the input point.
     *
     * @param point String of a point to get the address from
     * @return the description / placeName of the point
     * @throws IOException cant connect to mapbox
     */
    public String getAddress(String point) throws IOException {
        if (point.length() == 0) {
            throw new IllegalArgumentException("No insertion for this Waypoint");
        }

        CarmenFeature feature = transformer.transformPoint(point);
        if (feature == null) {
            throw new IllegalArgumentException("The Point " + point + " was not found");
        }
        return feature.placeName();
    }

    /**
     * Calculates a DirectionsRoute route for the given waypoints and a profile.
     *
     * @param stringWaypoints List of String waypoints for the route calculation
     * @param profile         profile to use for calculation (driving, driving-traffic, cycling, walking)
     * @return calculated DirectionsRoute
     * @throws IOException cant connect to mapbox
     */
    public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException {

        List<Point> waypoints = new ArrayList<>(stringWaypoints.size());
        for (String waypoint : stringWaypoints) {
            waypoints.add(transformer.transformPoint(waypoint).center());
        }
        String finalProfile = transformer.transformProfile(profile);


        DirectionsRoute route = anIMapbox.getListRoute(waypoints, finalProfile);
        getMap(waypoints, route);
        return route;
    }

    /**
     * Checks if a point (its street name) is part of a route.
     * If its streetname is not part of the route, a new route from the input point to the destination of the
     * inputRoute will be calculated and returned. If the streetname of the point is on the route,
     * the input route will be returned.
     *
     * @param inputRoute route to check if the point is part of it
     * @param point      point to check if it is on the route
     * @return inputRoute if the point is on the route or a new route if not
     */
    public DirectionsRoute goneAstray(DirectionsRoute inputRoute, String point) throws IOException {

        List<Point> newWaypoints = new ArrayList<>();
        boolean onroute = offroute.stillOnRoute(inputRoute, point);
        Point origin = transformer.transformPoint(point).center();
        String profile = inputRoute.legs().get(0).steps().get(0).mode();

        if (!onroute) {
            int lastLegIndex = inputRoute
                    .legs()
                    .size() - 1;
            int destinationIndex = inputRoute
                    .legs()
                    .get(lastLegIndex)
                    .steps()
                    .size() - 1;
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
     * Returns a route depending on if a point is part of it or not.
     * Checks whether the street name of the point is part of the input route.
     * If its not a new route from the point to the destination of the input route is calculated.
     * If the point is part of the route, the input route is returned.
     *
     * @param route route to test if a point is part of it
     * @param point point to test whether its street name is part of the route
     * @return input route when the point is part of the route
     * new route from point to the destination of the input route
     * @throws IOException cant connect to mapbox
     */
    public boolean stillOnRoute(DirectionsRoute route, String point) throws IOException {
        return offroute.stillOnRoute(route, point);
    }


    /**
     * Open the ConnectionMapbox.IMapbox to save the map.
     *
     * @param waypoints the List of Waypoints
     * @param route     the calculated route
     */
    private void getMap(List<Point> waypoints, DirectionsRoute route) {
        anIMapbox.getMap(waypoints, route);
    }
}
