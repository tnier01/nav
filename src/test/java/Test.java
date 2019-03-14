import java.io.IOException;

import static org.junit.Assert.*;

public class Test {


    public void testRoutenfinder() throws IOException {

        Routenfinder testNav = new Routenfinder();
        /*class Routenfinder

        method 1: getRoute(String origin, String destination, String profile): DirectionsRoute
         possibilities:
             input is a not existing location -> IOException */
        //assertEquals(testNav.gibRoute("n", "s", "Fahrrad"), IOException );
        //coordinates are not on the land -> "Route not found"
        //assertEquals("the returned route is not equal the expected", testNav.gibRoute("Hamburg", "Köln", "Auto"),
        //             "[RouteLeg{distance=423875.4, duration=113074.2, summary=, steps=[], annotation=null}]");

     /*method 2: + gibDistanz(String origin, String destination, String profile): Int

            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testNav.gibDistanz("n", "s", "Fahrrad"), IOException)
                gibDistanz("Hamburg","Köln", "Fahrrad") -> 423875.4
                    assertEquals("the returned distance is not equal the expected", testNav.gibDistanz("Hamburg", "Köln", "Auto"),
                         "423875.4")


     method 3: gibZeit(String origin, String destination, String profile): String

            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testNav.gibZeit("n", "s", "Fahrrad"), IOException)
                gibZeit("Hamburg","Köln", "Fahrrad") -> 113074.2
                    assertEquals("the returned distance is not equal the expected", testNav.gibZeit("Hamburg", "Köln", "Auto"),
                         "113074.2")
*/
    }

    public void testEingabetransformator() throws IOException {

        Eingabetransformator testEt = new Eingabetransformator();
        /*
    class Eingabetransformator

        method 1: transformPoint(String point): Point
            possibilities:
                input is a not a point or a existing location -> IOException
                    assertEquals(testEt.transformPoint("no Point"), IOException)
                example 1: transformPoint("hamburg") -> [10.0, 53.55]
                    assertEquals(testEt.transformPoint("hamburg"), [10.0, 53.55])
                example 2: transformPoint("köln") -> [6.95778, 50.94222]
                    assertEquals(testEt.transformPoint("köln"), [6.95778, 50.94222])
                example 3: transformPoint("10.0, 53.55") -> [10.0, 53.55]
                    assertEquals(testEt.transformPoint("10.0, 53.55"), [10.0, 53.55])
                example 4: transformPoint("6.95778, 50.94222") -> [6.95778, 50.94222]
                    assertEquals(testEt.transformPoint("6.95778, 50.94222"), [6.95778, 50.94222])

        method 2: transformiereProfile(): String
            possibilities: only accesses the ProfileSwitcher -> therefore no tests necessary


     */
    }

    public void testProfileSwitcher() throws IOException {

        ProfileSwitcher testPS = new ProfileSwitcher();
    /*
    class ProfileSwitcher
        method 1: switchProfile (String profile): void
            possibilities:*/
                //switchProfile(" ") -> IllegalArgumentException("no legal profile")
                    //assertEquals(testPS.switchProfile(" "),IllegalArgumentException)
               // switchProfile("car") -> "DirectionsCriteria.PROFILE_DRIVING"
                    assertEquals(testPS.switchProfile("car"),"DirectionsCriteria.PROFILE_DRIVING");
                //switchProfile("Auto") -> "DirectionsCriteria.PROFILE_DRIVING"
                    assertEquals(testPS.switchProfile("Auto"),"DirectionsCriteria.PROFILE_DRIVING");
                //switchProfile("traffic") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
                    assertEquals(testPS.switchProfile("traffic"),"DirectionsCriteria.PROFILE_TRAFFIC");
                //switchProfile("Stau") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
                    assertEquals(testPS.switchProfile("Stau"),"DirectionsCriteria.PROFILE_TRAFFIC");
                //switchProfile("walking") -> "DirectionsCriteria.PROFILE_WALKING"
                    assertEquals(testPS.switchProfile("walking"),"DirectionsCriteria.PROFILE_WALKING");
                //switchProfile("Laufen") -> "DirectionsCriteria.PROFILE_WALKING"
                    assertEquals(testPS.switchProfile("Laufen"),"DirectionsCriteria.PROFILE_WALKING");
                //switchProfile("Gehen") -> "DirectionsCriteria.PROFILE_WALKING"
                    assertEquals(testPS.switchProfile("Gehen"),"DirectionsCriteria.PROFILE_WALKING");
                //switchProfile("cycling") -> "DirectionsCriteria.PROFILE_CYCLING"
                    assertEquals(testPS.switchProfile("cycling"),"DirectionsCriteria.PROFILE_WALKING");
                //switchProfile("Fahrrad fahren") -> "DirectionsCriteria.PROFILE_CYCLING"
                    assertEquals(testPS.switchProfile("Fahrrad fahren"),"DirectionsCriteria.PROFILE_CYCLING");
                //switchProfile("Fahrrad") -> "DirectionsCriteria.PROFILE_CYCLING"
                    assertEquals(testPS.switchProfile("Fahrrad"),"DirectionsCriteria.PROFILE_CYCLING");

    }

    public void testSchnittstelle() throws IOException {

        Schnitstelle testS = new Schnitstelle();
    /*
    class Schnittstelle
        method 1: getRoute(String origin, String destination, String profile): DirectionsRoute
         possibilities:
             input is a not existing location -> IOException */
        //assertEquals(testS.gibRoute("n", "s", "Fahrrad"), IOException );
        //coordinates are not on the land -> "Route not found"
        //assertEquals("the returned route is not equal the expected", testS.gibRoute("Hamburg", "Köln", "Auto"),
        //             "[RouteLeg{distance=423875.4, duration=113074.2, summary=, steps=[], annotation=null}]");

        /*method 2: geocoder(String Eingabe): Point
            input is a not a point or a existing location -> IOException
                    assertEquals(testEt.transformPoint("no Point"), IOException)
                example 1: geocoder("hamburg") -> [10.0, 53.55]
                    assertEquals(testS.geocoder("hamburg"), [10.0, 53.55])
                example 2: geocoder("köln") -> [6.95778, 50.94222]
                    assertEquals(testS.geocoder("köln"), [6.95778, 50.94222])
                example 3: geocoder("10.0, 53.55") -> [10.0, 53.55]
                    assertEquals(testS.geocoder("10.0, 53.55"), [10.0, 53.55])
                example 4: geocoder("6.95778, 50.94222") -> [6.95778, 50.94222]
                    assertEquals(testS.geocoder("6.95778, 50.94222"), [6.95778, 50.94222])
         */
    }
}


