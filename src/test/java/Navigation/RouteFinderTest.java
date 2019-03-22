package Navigation;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RouteFinderTest {



    RouteFinder testNav = new RouteFinder();
    List waypoints = new ArrayList();






    @org.junit.jupiter.api.Test
    void getAddress() throws IOException {
       Throwable ex= Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getAddress(":(");
                },
                "no ArgumentException thrown in getListRoute");
        Assert.assertEquals(ex.getMessage(),"The Point :( was not found");

        waypoints.add("");
        ex= Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getAddress("");
                },
                "no ArgumentException thrown in getListRoute");
        Assert.assertEquals(ex.getMessage(),"No insertion for this Waypoint");

        Assert.assertEquals(testNav.getAddress("Köln"), "Köln, Nordrhein-Westfalen, Germany");

        Assert.assertEquals(testNav.getAddress("50.94222, 6.95778"), "a-Passage, 50667 Köln, Germany");

    }

    @org.junit.jupiter.api.Test
    void getListRoute() {
        waypoints.add("berlin");waypoints.add("washington");
        /*class Navigation.RouteFinder

        method 1: public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException
         possibilities:
             input is a not existing location -> IOException */
        Throwable ex= Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getListRoute(waypoints, "car");
                },
                "no ArgumentException thrown in getListRoute");
        Assert.assertEquals(ex.getMessage(),"no legal profile");
        ex= Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getListRoute(waypoints, "driving");
                },
                "no ArgumentException thrown in getListRoute");
        Assert.assertEquals(ex.getMessage(),"No routes found");
    }

    @org.junit.jupiter.api.Test
    void goneAstray() throws IOException {
        List<String> newWaypoints= new ArrayList();

        newWaypoints.add("Krefeld");newWaypoints.add("Raesfeld");
        DirectionsRoute route = testNav.getListRoute(newWaypoints, DirectionsCriteria.PROFILE_DRIVING);

        Assert.assertEquals(testNav.goneAstray(route,"Ostwall, Krefeld"), route);
    }

    @org.junit.jupiter.api.Test
    void stillOnRoute() throws IOException {
        RouteFinder naviTest = new RouteFinder();
        List<String> waypoints = new ArrayList<>();
        waypoints.add("Krefeld");
        waypoints.add("Raesfeld");

        DirectionsRoute route= naviTest.getListRoute(waypoints, DirectionsCriteria.PROFILE_DRIVING);

        Assert.assertEquals(naviTest.stillOnRoute(route, "Ostwall, Krefeld"), true);
        Assert.assertEquals(naviTest.stillOnRoute(route, "Ostwall, Berlin"), false);
    }
}