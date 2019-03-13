import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;
import java.util.Scanner;

public class Prototyp {


    public static void main(String[] args) throws IOException{
        Routenfinder navigation =new Routenfinder();

        DirectionsRoute navi;
        String input1, input2,input3;
        /*
        input
        If there is no exception the route is returned
         */

        while ( true )
        {
            try
            {
                // Create a new object for the input
                Scanner sc = new Scanner(System.in);
                System.out.println("Origin and Destination as  Coordinate: lat,lng or as Street");
                System.out.println("From where would you like to start your route?");

                // Read the first input in the string "input1"
                input1 = sc.nextLine();
                System.out.println("origin input: " + input1);

                System.out.println("Where do you would like to end the route?");
                // Read the second input in the string "input2"
                input2 = sc.nextLine();
                System.out.println("destination input: " + input2);

                System.out.println("which profile are you using (selection: driving, driving-traffic, walking, cycling)?");
                // Read the third input in the string "input3"
                input3 = sc.nextLine();
                System.out.println("profile input: " + input3);

                System.out.println("route calculation:");



        // route calculation
        navi = navigation.gibRoute(input1, input2, input3);

        break;
    }
            catch ( Exception e )
    {
        System.err.println(e.getMessage()); // returns the message concerning the actual exception

    }
}

        // print in which direction the user should drive on the first street
        System.out.println("navigation from " + input1 + " to " + input2 + ": \n" + navi.legs().get(0).steps().get(0).maneuver().instruction());
        // print how long the user should stay on the actual street and which street should be used next
        //System.out.println(navi.legs().get(0).steps().get(0).maneuver().instruction());
        for(int i = 0; i < navi.legs().get(0).steps().size()-1; i++) {
            int instructionSize = navi.legs().get(0).steps().get(i).voiceInstructions().size();
            double distance = navi.legs().get(0).steps().get(i).distance();
            if (distance <= 1000){
                System.out.println("stay on the street for "+ Math.round(distance/10)*10 + " meters");
            } else {
                System.out.println("stay on the street for "+ (double) Math.round(distance/100) /10 + " kilometers");
            }

            System.out.println(navi.legs().get(0).steps().get(i).voiceInstructions().get(instructionSize-1).announcement());
        }


        System.exit( 0 );




    }
}
