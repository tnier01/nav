import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.geojson.BoundingBox;
import com.mapbox.geojson.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Testclass {

    @Test
    public void testRoutenfinder() throws IOException {

        Routenfinder testNav = new Routenfinder();
        List waypoints = new ArrayList();
        waypoints.add("berlin");waypoints.add("washington");
        /*class Routenfinder

        method 1: public DirectionsRoute getListRoute(List<String> stringWaypoints, String profile) throws IOException
         possibilities:
             input is a not existing location -> IOException */
        assertThrows(IllegalArgumentException.class,
                () -> {
                    testNav.getListRoute(waypoints, "walking");
                },
                "no IOException thrown in getListRoute");

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
    class Eingabetransformator

        method 1: transformPoint(String point): Point
            possibilities:*/
        // input is not a point or a existing location -> IOException
        //assertEquals(testEt.transformPoint("no Point"), IOException)
        assertThrows(IOException.class,
                () -> {
                    testEt.transformPoint("");
                },
                "no IOException thrown in transformPoint");
        // example 1: transformPoint("hamburg") -> [10.0, 53.55]
        Point p=new Point() {
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
                l.add(10.0);l.add(53.55);
                return l;
            }
        };
        assertEquals(testEt.transformPoint("hamburg"), p);
        // example 2: transformPoint("10.0, 53.55") -> [10.0, 53.55]
        assertEquals(testEt.transformPoint("53.55,10.0"), p);
        // example 3: transformPoint("köln") -> [6.95778, 50.94222]
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
        };
        assertEquals(testEt.transformPoint("köln"), p);

        // example 4: transformPoint("6.95778, 50.94222") -> [6.95778, 50.94222]
        assertEquals(testEt.transformPoint("50.94222,6.95778"), p);

/*
        method 2: transformiereProfile(): String
            possibilities: only accesses the ProfileSwitcher -> therefore no tests necessary


     */
    }

    @Test
    public void testProfileSwitcher() {

        ProfileSwitcher testPS = new ProfileSwitcher();
    /*
    class ProfileSwitcher
        method 1: switchProfile (String profile): void
            possibilities:*/
        //switchProfile(" ") -> IllegalArgumentException("no legal profile")
        //assertEquals(testPS.switchProfile(" "),IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                () -> {
                    testPS.switchProfile("");
                },"no IllegalArgumentException thrown");
        // switchProfile("driving") -> "DirectionsCriteria.PROFILE_DRIVING"
            assertEquals(testPS.switchProfile("driving"), DirectionsCriteria.PROFILE_DRIVING);
        //switchProfile("driving-traffic") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
            assertEquals(testPS.switchProfile("driving-traffic"), DirectionsCriteria.PROFILE_DRIVING_TRAFFIC);
        //switchProfile("walking") -> "DirectionsCriteria.PROFILE_WALKING"
            assertEquals(testPS.switchProfile("walking"),DirectionsCriteria.PROFILE_WALKING);
        //switchProfile("cycling") -> "DirectionsCriteria.PROFILE_CYCLING"
            assertEquals(testPS.switchProfile("cycling"),DirectionsCriteria.PROFILE_CYCLING);

    }

    @Test
    public void testSchnittstelle() throws IOException {

        Schnitstelle testS = new Schnitstelle();
        List waypoints = new ArrayList();
        waypoints.add("");waypoints.add("");
  /*  class Schnittstelle
        method 1: getRoute(String origin, String destination, String profile): DirectionsRoute
             possibilities:
                  input is a not existing location -> IOException*/
    assertThrows(IOException.class,
            () -> {
                testS.getListRoute(waypoints, "");
            },
            "no IOException thrown in getListRoute");
                //more tests are not necessary, because we assume that the API returns the correct Route

       /*//method 2: geocoder(String Eingabe): Point
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
       */
    }
}

