import Navigation.Eingabetransformator;
import Navigation.Offroute;
import Navigation.ProfileSwitcher;
import Navigation.Routenfinder;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Testclass {

    @Test
    public void testRoutenfinder() throws IOException {

        Routenfinder testNav = new Routenfinder();
        List waypoints = new ArrayList();
        waypoints.add("berlin");waypoints.add("washington");
        /*class Navigation.Routenfinder

        method 1: public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException
         possibilities:
             input is a not existing location -> IOException */
        Throwable ex= assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getListRoute(waypoints, "car");
                },
                "no ArgumentException thrown in getListRoute");
        assertEquals(ex.getMessage(),"no legal profile");
        ex= assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getListRoute(waypoints, "driving");
                },
                "no ArgumentException thrown in getListRoute");
        assertEquals(ex.getMessage(),"No routes found");

        ex= assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getAdress(":(");
                },
                "no ArgumentException thrown in getListRoute");
        assertEquals(ex.getMessage(),"The Point :( was not found");

        waypoints.add("");
        ex= assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getAdress("");
                },
                "no ArgumentException thrown in getListRoute");
        assertEquals(ex.getMessage(),"No insertion for this Waypoint");

        assertEquals(testNav.getAdress("Köln"), "Köln, Nordrhein-Westfalen, Germany");

        assertEquals(testNav.getAdress("50.94222, 6.95778"), "a-Passage, 50667 Köln, Germany");

        List<String> newWaypoints= new ArrayList();

        newWaypoints.add("Krefeld");newWaypoints.add("Raesfeld");
        DirectionsRoute route = testNav.getListRoute(newWaypoints, DirectionsCriteria.PROFILE_DRIVING);

        assertEquals(testNav.goneAstray(route,"Ostwall, Krefeld"), route);

        List<String> newWaypoints2 = new ArrayList<>();
        newWaypoints2.add("Ostwall, Berlin");newWaypoints2.add("Raesfeld");
        DirectionsRoute route3 = testNav.getListRoute(newWaypoints2,DirectionsCriteria.PROFILE_DRIVING);
        DirectionsRoute route2 = testNav.getListRoute(newWaypoints2,DirectionsCriteria.PROFILE_DRIVING);
        //assertEquals(route3, route2);
        //assertEquals(testNav.goneAstray(route, "Ostwall, Berlin"), route2);

        //coordinates are not on the land -> "Route not found"

        //assertEquals("the returned route is not equal the expected", testNav.gibRoute("Hamburg", "Köln", "Auto"),
        //              "[RouteLeg{distance=423875.4, duration=113074.2, summary=, steps=[], annotation=null}]");

     /*method 2: + gibDistanz(String origin, String destination, String profile): Int

            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testNav.gibDistanz("n", "s", "Fahrrad"), IOException)
                gibDistanz("Hamburg","Köln", "Fahrrad") -> 423875.4
                    assertEquals("the returned distance is not equal the expected", testNav.gibDistanz("Hamburg", "Köln", "Auto"),
                         "423875.4");


     method 3: gibZeit(String origin, String destination, String profile): String

            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testNav.gibZeit("n", "s", "Fahrrad"), IOException)
                gibZeit("Hamburg","Köln", "Fahrrad") -> 113074.2
                    assertEquals("the returned distance is not equal the expected", testNav.gibZeit("Hamburg", "Köln", "Auto"),
                         "113074.2")
*/
    }

    @Test
    public void testEingabetransformator() throws IOException {

        Eingabetransformator testEt = new Eingabetransformator();
        /*
    class Navigation.Eingabetransformator

        method 1: transformPoint(String point): Point
            possibilities:*/
        // input is not a point or a existing location -> IOException
        //assertEquals(testEt.transformPoint("no Point"), IOException)
        Throwable ex= assertThrows(ServicesException.class,
                () -> {
                    testEt.transformPoint("");
                },
                "no IOException thrown in transformPoint");
        assertEquals(ex.getMessage(), "A query with at least one character or digit is required.");
        // example 1: transformPoint("hamburg") -> [10.0, 53.55]
        Point p=Point.fromLngLat(10.0, 53.55);

        assertEquals(testEt.transformPoint("hamburg").center(), p);
        p=Point.fromLngLat(9.999952, 53.54987);
        // example 2: transformPoint("10.0, 53.55") -> [9.999953, 53.54978]
        assertEquals(testEt.transformPoint("53.55, 10.0").center(), p);
        // example 3: transformPoint("köln") -> [6.95778, 50.94222]
        p= Point.fromLngLat(6.95778, 50.94222);
        assertEquals(testEt.transformPoint("köln").center(), p);

        p=Point.fromLngLat(6.958112, 50.94232);

        // example 4: transformPoint("6.95778, 50.94222") -> [6.95778, 50.94222]
        assertEquals(testEt.transformPoint("50.94222, 6.95778").center(), p);

        p= Point.fromLngLat( 7.640063, 51.95173);
        assertEquals(testEt.transformPoint("Hafen, Münster").center(), p);
        assertEquals(testEt.transformPoint("Hafen Münster").center(), p);

/*
        method 2: transformiereProfile(): String
            possibilities: only accesses the Navigation.ProfileSwitcher -> therefore no tests necessary


     */
    }

    @Test
    public void testOffroute() throws  IOException{

        Offroute offTester = new Offroute();
        Routenfinder naviTest = new Routenfinder();
        List<String> waypoints = new ArrayList<>();
        waypoints.add("Krefeld");
        waypoints.add("Raesfeld");

        DirectionsRoute route= naviTest.getListRoute(waypoints, DirectionsCriteria.PROFILE_DRIVING);

        assertEquals(offTester.stillOnRoute(route, "Ostwall, Krefeld"), true);
        assertEquals(offTester.stillOnRoute(route, "Ostwall, Berlin"), false);

    }

    @Test
    public void testProfileSwitcher() {

        ProfileSwitcher testPS = new ProfileSwitcher();
    /*
    class Navigation.ProfileSwitcher
        method 1: switchProfile (String profile): void
            possibilities:*/
        //switchProfile(" ") -> IllegalArgumentException("no legal profile")
        //assertEquals(testPS.switchProfile(" "),IllegalArgumentException)
        Throwable ex = assertThrows(IllegalArgumentException.class,
                () -> {
                    testPS.switchProfile("car");
                },"no IllegalArgumentException thrown");
        assertEquals(ex.getMessage(),"no legal profile");
        // switchProfile("driving") -> "DirectionsCriteria.PROFILE_DRIVING"
            assertEquals(testPS.switchProfile("driving"), DirectionsCriteria.PROFILE_DRIVING);
        //switchProfile("driving-traffic") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
            assertEquals(testPS.switchProfile("driving-traffic"), DirectionsCriteria.PROFILE_DRIVING_TRAFFIC);
        //switchProfile("walking") -> "DirectionsCriteria.PROFILE_WALKING"
            assertEquals(testPS.switchProfile("walking"),DirectionsCriteria.PROFILE_WALKING);
        //switchProfile("cycling") -> "DirectionsCriteria.PROFILE_CYCLING"
            assertEquals(testPS.switchProfile("cycling"),DirectionsCriteria.PROFILE_CYCLING);

    }
/*
    @Test
    public void testSchnittstelle() throws IOException {

        Navigation.Schnitstelle testS = new Navigation.Schnitstelle();
        List waypoints = new ArrayList();
        waypoints.add("");waypoints.add("");
   class Schnittstelle
        method 1: getRoute(String origin, String destination, String profile): DirectionsRoute
             possibilities:
                  input is a not existing location -> IOException
    assertThrows(IOException.class,
            () -> {
                testS.getListRoute(waypoints, "");
            },
            "no IOException thrown in getListRoute");
                //more tests are not necessary, because we assume that the API returns the correct Route

       /method 2: geocoder(String Eingabe): Point
                //input is a not a point or a existing location -> IOException
                //assertEquals(testEt.transformPoint("no Point"), IOException)
                //example 1: geocoder("hamburg") -> [10.0, 53.55]
    Point p= Point.fromLngLat(53.55,10.0);
        assertEquals(testS.geocode("hamburg"), p);
        //example 3: geocoder("köln") -> [6.95778, 50.94222]
        p= new Point() {
            @Override
            public String type() {
                return "Point";
            }

            @Override
            public BoundingBox bbox() {
                return null;
            }

            @Override
            public List<Double> coordinates() {
                List<Double> l= new ArrayList<>();
                l.add(6.95778);l.add(50.94222);
                return l;
            }
        };assertEquals(testS.geocodeToObj("köln"), p);

    }*/
}

