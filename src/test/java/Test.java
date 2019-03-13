import java.io.IOException;

import static org.junit.Assert.*;

public class Test {



    public void testRoutenfinder()throws IOException {

        Routenfinder testNav = new Routenfinder();
        /*class Routenfinder

        method 1: getRoute(String origin, String destination, String profile): DirectionsRoute
         possibilities:
             input is a not existing location -> IOException */
             assertEquals(testNav.gibRoute("n", "s", "Fahrrad"), IOException );
            //coordinates are not on the land -> "Route not found"
            assertEquals("the returned route is not equal the expected", testNav.gibRoute("Hamburg", "Köln", "Auto"),
                         "[RouteLeg{distance=423875.4, duration=113074.2, summary=, steps=[], annotation=null}]");

     /*method 2: + gibDistanz(String origin, String destination, String profile): Int

            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testNav.gibDistanz("n", "s", "Fahrrad"), IOException)
                gibDistanz("Hamburg","Köln", "Fahrrad") -> 423875.4
                    assertEquals("the returned distance is not equal the expected", testNav.gibDistanz("Hamburg", "Köln", "Auto"),
                         "423875.4")


     Methode 3: gibZeit(String origin, String destination, String profile): String

            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testNav.gibZeit("n", "s", "Fahrrad"), IOException)
                gibZeit("Hamburg","Köln", "Fahrrad") -> 113074.2
                    assertEquals("the returned distance is not equal the expected", testNav.gibZeit("Hamburg", "Köln", "Auto"),
                         "113074.2")
*/
    }
    public void testEingabetransformator()throws IOException {

        Eingabetransformator testEt = new Eingabetransformator();
        /*
    class Eingabetransformator

        method 1: transformPoint(String point): Point
            possibilities:
                input is a not existing location -> IOException
                    assertEquals(testEt.transformPoint("no Point"), IOException)
                example 1: transformiereProfile("hamburg") -> [10.0, 53.55]
                example 2: transformiereProfile("köln") -> [6.95778, 50.94222]

        method 2: transformiereProfile(): String
            possibilities:
                input is not a point -> IOException
                example 1: transformPoint([10.0, 53.55]) -> "hamburg"
                example 2: transformierePunkt([6.95778, 50.94222]) -> "köln"
     */
    }
    /*
    class ProfileSwitcher
        method 1: switchProfile (String profile): void
            possibilities:
                switchProfile("car") -> "DirectionsCriteria.PROFILE_DRIVING"
                switchProfile("Auto") -> "DirectionsCriteria.PROFILE_DRIVING"
                switchProfile("traffic") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
                switchProfile("Stau") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
                switchProfile("walking") -> "DirectionsCriteria.PROFILE_WALKING"
                switchProfile("Laufen") -> "DirectionsCriteria.PROFILE_WALKING"
                switchProfile("Gehen") -> "DirectionsCriteria.PROFILE_WALKING"
                switchProfile("cycling") -> "DirectionsCriteria.PROFILE_CYCLING"
                switchProfile("Fahrrad fahren") -> "DirectionsCriteria.PROFILE_CYCLING"
                switchProfile("Fahrrad") -> "DirectionsCriteria.PROFILE_CYCLING"
     */

    /*
    class Schnittstelle
        method 1: getRoute(Point origin, Point destination): DirectionsRoute

        method 2: geocoder(String Eingabe): Point
     */
}


