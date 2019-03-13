import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

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
        Nav navigation =new Nav();


        DirectionsRoute navi = navigation.getRoute("raesfeld", "münster", "car");

        for(int i = 0; i < navi.legs().get(0).steps().size()-1; i++) {
            int instructionSize = navi.legs().get(0).steps().get(i).voiceInstructions().size();
            System.out.println("stay on the street for "+ Math.round((navi.legs().get(0).steps().get(i).distance())/10)*10 + " meters");
            System.out.println(navi.legs().get(0).steps().get(i).voiceInstructions().get(instructionSize-1).announcement());
        }


    }

}
