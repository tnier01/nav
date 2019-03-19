import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

//import org.graalvm.compiler.loop.InductionVariable;

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

        DirectionsRoute naviList = navigation.getListRoute(waypoints, "driving");

        DirectionsRoute newRoute = navigation.goneAstray(naviList, "Weseler Straße (B 70)");

        //System.out.println(newRoute);

        List<CarmenFeature> pointInfo = geocode.geocodeToObj("Weseler Straße (B 70) Raesfeld");
        // the streetname of the point
        String street = pointInfo.get(0).placeName();
        //System.out.println(pointInfo);
        //System.out.println(naviList);
        String string1 = "Weseler Straße";
        String string2 = "abcdefg Straße B(70)";
        int compare = string1.compareTo(string2);
        System.out.println(compare);

        Offroute onroute = new Offroute();
        boolean ontheroute = onroute.stillOnRoute(naviList, "Weseler Straße (B 70) Raesfeld");
        System.out.println(ontheroute);


/*
        Point point = geocode.geocoding("paschenvenne raesfeld");
        System.out.println(point.toString());
*/
    }
}
