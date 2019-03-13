import java.io.IOException;

import static org.junit.Assert.*;

public class Test {



    public void testRoutenfinder()throws IOException {

        Nav testNav = new Nav();
        /*class Routenfinder
        method 1: getRoute(String origin, String destination, String profile): DirectionsRoute

         possibilities:
             input is a not existing location -> IOException */
            // assertEquals(testNav.getRoute("n", "s", "Fahrrad"), IOException );
            //coordinates are not on the land -> "Route not found"
            //assertEquals(testNav.getRoute("Hamburg", "Köln", "Auto"), "[RouteLeg{distance=423875.4, duration=113074.2, summary=, steps=[], annotation=null}]");

     /*method 2: + gibDistanz(String origin, String destination, String profile): Int

            possibilities:
                input is a not existing location -> IOException
                gibDistanz("Hamburg","Köln", "Fahrrad") -> 423875.4


     Methode 3: gibZeit(String origin, String destination, String profile): String

            possibilities:
                input is a not existing location -> IOException
                gibZeit("Hamburg","Köln", "Fahrrad") -> 113074.2
*/
    }
    /*
    class Eingabetransformator
        method 1: transformierePunkt(): Point
            possibilities:
                input is a not existing location -> IOException
                example 1: transformiereProfile("hamburg") -> [10.0, 53.55]
                example 2: transformiereProfile("köln") -> [6.95778, 50.94222]

        method 2: transformiereProfile(): String
            possibilities:
                input is not a point
                example 1: transformierePunkt([10.0, 53.55]) -> "hamburg"
                example 2: transformierePunkt([6.95778, 50.94222]) -> "köln"
     */

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


