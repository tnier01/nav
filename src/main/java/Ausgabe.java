import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;

public class Ausgabe {

    /**
     * gives some navigation instructions for the route on the run console
     *
     * @author Jan
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        Routenfinder navigation =new Routenfinder();


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


    }

}
