import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
//import org.graalvm.compiler.loop.InductionVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ausgabe {

    /**
     * gives some navigation instructions for the route on the run console
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Routenfinder navigation = new Routenfinder();
        Schnitstelle geocode = new Schnitstelle();

/*
        DirectionsRoute navi = navigation.gibRoute("raesfeld", "münster", "car");

        for(int i = 0; i < navi.legs().get(0).steps().size()-1; i++) {
            int instructionSize = navi.legs().get(0).steps().get(i).voiceInstructions().size();
            double distance = navi.legs().get(0).steps().get(i).distance();
            if (distance < 1000){
                System.out.println("stay on the street for "+ Math.round(distance/10)*10 + " meters");
            } else {
                System.out.println("stay on the street for "+ Math.round(distance/100)/10 + " kilometers");
            }

            System.out.println(navi.legs().get(0).steps().get(i).voiceInstructions().get(instructionSize-1).announcement());
        }




        List<String> waypoints = new ArrayList<>();
        waypoints.add("Raesfeld");
        //waypoints.add("Borken");
        waypoints.add("Reken");
        //waypoints.add("Coesfeld");
        waypoints.add("Münster");

        DirectionsRoute naviList = navigation.gibListRoute(waypoints, "driving");

        for (int j = 0; j < naviList.legs().size(); j++) {

            for (int i = 0; i < naviList.legs().get(j).steps().size() - 1; i++) {
                int instructionSize = naviList.legs().get(j).steps().get(i).voiceInstructions().size();
                double distance = naviList.legs().get(j).steps().get(i).distance();
                if (distance < 1000) {
                    System.out.println("stay on the street for " + Math.round(distance / 10) * 10 + " meters");
                } else {
                    System.out.println("stay on the street for " + Math.round(distance / 100) / 10 + " kilometers");
                }

                System.out.println(naviList.legs().get(j).steps().get(i).voiceInstructions().get(instructionSize - 1).announcement());
            }

        }
*/
        List<String> waypoints = new ArrayList<>();
        waypoints.add("Raesfeld");
        waypoints.add("Münster");

        DirectionsRoute naviList = navigation.gibListRoute(waypoints, "driving");

        Offroute onroute = new Offroute();
        boolean amionroute = onroute.stillOnRoute(naviList, "Paschenvenne");
        System.out.println(amionroute);


        List<CarmenFeature> response = geocode.geocodeToObj("heisenbergstraße münster");
        System.out.println(response.get(0).context().get(0).text());
/*
        Point point = geocode.geocoding("paschenvenne raesfeld");
        System.out.println(point.toString());
*/
    }
}
