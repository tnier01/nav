// We love beer
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;
import java.util.Scanner;

public class Prototyp {


    /**
     * main method which controls the input and output on the console
     * asks for origin and destination and delivers navigation instructions
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Routenfinder navigation = new Routenfinder();

        DirectionsRoute navi = null;
        String input1 = null, input2 = null, input3 = null, input4 = null;
        /*
        input
        If there is no exception the route is returned
         */
        // Create a new object for the input
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
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
            catch (Exception e) {
                    /*
                    If there is an exception the user is able to decide on his own if he likes to restart or end the program.
                     */
                    System.err.println("The input is wrong cause: " + e.getMessage()); // returns the message concerning the actual exception
                    System.out.println("Do you like to restart the program (yes/no)?");
                    input4 = sc.nextLine();
                    System.out.println("profile input: " + input4);

                    if (input4.equals("no")) {
                        System.exit(1);

                    }
                    if (!input4.equals("no") && !input4.equals("yes")) {
                        System.out.println("you used a word which differs from no or yes! Please select again!");
                    }



                }
            }


            // print in which direction the user should drive on the first street
            System.out.println("navigation from " + input1 + " to " + input2 + ": \n" + navi.legs().get(0).steps().get(0).maneuver().instruction());

            // print how long the user should stay on the actual street and which street should be used next
            for (int i = 0; i < navi.legs().get(0).steps().size() - 1; i++) {
                int instructionSize = navi.legs().get(0).steps().get(i).voiceInstructions().size();  // count of instructions per step
                double distance = navi.legs().get(0).steps().get(i).distance();  // distance between steps
                // print the distance
                if (distance <= 1000) {
                    System.out.println("stay on the street for " + Math.round(distance / 10) * 10 + " meters");
                } else {
                    System.out.println("stay on the street for " + (double) Math.round(distance / 100) / 10 + " kilometers");
                }
                // print instruction at the end of the street
                System.out.println(navi.legs().get(0).steps().get(i).voiceInstructions().get(instructionSize - 1).announcement());
            }


            System.exit(0);
        }

        }


