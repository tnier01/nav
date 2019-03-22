package Output;

import Navigation.RouteFinder;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for implementation of the Output.Prototype, at least our user-interface.
 */
public class Prototype {

    /**
     * Modularized method to check if the user is content with the by the program proposed location.
     * The user insert a location, then the program is printing the location which the API is recognizing with regard
     * to the input of the user. If the user is content with this location, the program use this location, if not
     * this method gets called recursively.
     *
     * @param input
     * @return the final input which gets used
     * @throws IOException
     */
    private static String likeInput(String input) throws IOException {
        RouteFinder navigation = new RouteFinder();
        // Create a new object for the input
        Scanner sc = new Scanner(System.in);

        System.out.println(navigation.getAdress(input));
        System.out.println("Is this the right adress (yes/no)?");

        String input9 = sc.nextLine();
        System.out.println("input: " + input9);

        input9 = ifInputWrong(input9, sc);

        if (input9.equals("yes")) {
            return input;
        }
        else {
            System.out.println("Insert a more detailed adress!");
            String input10 = sc.nextLine();
            System.out.println("input: " + input10);
            return likeInput(input10);
        }
    }

    /**
     * Modularized method, in case the input differs from "yes" and "no".
     *
     * @param x
     * @param scanner
     * @return correct input
     */
    private static String ifInputWrong(String x, Scanner scanner) {
        while (!x.equals("no") && !x.equals("yes")) {
            System.out.println("You used a word which differs from no or yes! Please select again!");
            x = scanner.nextLine();
            System.out.println("input: " + x);
        }
        return x;
    }

    /**
     * Modularized method, for a further input, a new waypoint
     *
     * @param scanner   the Scanner, which reads the input
     * @param waypoints the list of waypoints to visit
     */
    private static void inputWaypoint(Scanner scanner, List<String> waypoints) throws IOException {
        System.out.println("Please input the new waypoint!");
        String waypoint = scanner.nextLine();
        waypoint = likeInput(waypoint);

        System.out.println("new waypoint: " + waypoint);
        // the new waypoint gets insert at the last place before destination
        waypoints.add(waypoints.size() - 1, waypoint);
        System.out.println("actual route: " + waypoints);
    }

    /**
     * Main method which aks for different parameters for the route calculation and finally output the route description
     * with the map.
     * - input destination and origin
     * - input profile
     * - input different amount of waypoints (one in profile driving-traffic, up to 25 in the other profiles)
     * - input check/ restart/ exit
     * - output duration + distance of route, route description, map of the route with marked origin, destination,
     * waypoints
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args){
        RouteFinder navigation = new RouteFinder();

        DirectionsRoute naviList;
        String input1, input2, input3, input4, input5, input6, input7;
        List<String> waypoints = new ArrayList<>();
        boolean stillOnRoute;
        /*
        input
        If there is no exception the route is returned.
         */
        // Create a new object for the input.
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Höllennavigationsmaschine");
                System.out.println("Origin and Destination as Coordinate: " +
                        "Either you use lat,lng e.g. 51.9606649, 7.6261347\n" +
                        "or a city/ street/ postcode e.g. Martin-Luther-King-Weg 20 Münster");
                System.out.println("From where would you like to start your route?");

                // Read the first input in the string "input1"
                input1 = sc.nextLine();
                input1 = likeInput(input1);
                System.out.println("origin input: " + input1);
                waypoints.add(input1);

                System.out.println("Where do you would like to end the route?");
                // Read the second input in the string "input2"
                input2 = sc.nextLine();
                input2 = likeInput(input2);
                System.out.println("destination input: " + input2);
                waypoints.add(input2);

                System.out.println("Which profile are you using " +
                        "(selection: driving, driving-traffic, walking, cycling)?");
                // Read the third input in the string "input3"
                input3 = sc.nextLine();
                System.out.println("profile input: " + input3);

                boolean noValidProfile= true;

                while(noValidProfile){

                /*
                 If the input is driving, walking or cycling it is possible to enter 25 different waypoints and besides
                 one origin and one destination.
                  */
                if (input3.equals("driving") || input3.equals("walking") || input3.equals("cycling")) {
                    noValidProfile = false;

                    for (int i = 0; i < 23; i++) {

                        // input decision if there will be an further waypoint
                        System.out.println("Would you like to add another point (yes/no)?");
                        System.out.println("You are still able to add " +(23-i) +" Waypoints");
                        input5 = sc.nextLine();

                        // if there is a wrong word which differs from yes or no, repeat the selection
                        input5 = ifInputWrong(input5, sc);

                        // if the users wants a further waypoint he can insert it
                        if (input5.equals("yes")) {
                            // if the limit of waypoints is reached the user gets a notification
                            if (i > 21) {
                                System.out.println("Last possible waypoint!");
                            }
                            inputWaypoint(sc, waypoints);
                        }
                        // if the user wants no new waypoint the route is printed
                        if (input5.equals("no")) {
                            break;
                        }
                    }
                }

                /*
                 If the input is driving-traffic it is possible to enter one more waypoint and besides
                 one origin and one destination.
                  */
               else  if (input3.equals("driving-traffic")) {
                    noValidProfile = false;

                    // input decision if there will be an further waypoint
                    System.out.println("Would you like to add another point (yes/no)?");
                    input5 = sc.nextLine();

                    // if there is a wrong word which differs from yes or no, repeat the selection
                    input5 = ifInputWrong(input5, sc);

                    // if input is "yes" there is the possibility to add a new waypoint, if no the route gets calculated
                    if (input5.equals("yes")) {
                        inputWaypoint(sc, waypoints);
                    }
                }

               else
                {
                        System.out.println("No legal Profile, please select again");
                        input3 = sc.nextLine();

                    }
                }

                // route calculation
                System.out.println("route calculation:");
                naviList = navigation.getListRoute(waypoints, input3);
                break;

            } catch (Exception e) {
                    /*
                    If there is an exception the user is able to decide on his own
                    if he likes to restart or end the program.
                     */
                // returns the message concerning the actual exception
                System.err.println("The input is wrong cause: " + e.getMessage());
                System.out.println("Do you like to restart the program (yes/no)?");
                input4 = sc.nextLine();
                System.out.println("answer: " + input4);
                // waypoints must be cleared, because otherwise there are doubled destination and origin in the result
                waypoints.clear();

                // if the user do not wont to restart the program ends
                if (input4.equals("no")) {
                    System.exit(0);
                }

                // if there is a wrong word which differs from yes or no, repeat the selection
                ifInputWrong(input4, sc);

            }
        }

        // output with
        Output output = new Output();
        output.output(input1, input2, naviList);

        // creating map
        Map map = new Map();
        map.showMap();

        /*
        While-loop with three different possibilities. After you got your first route with the map you can decide if you
        want to check if you are still on the route, if you want to exit the program, or if you like to restart the
        program. If you like to check if you are still on the route, you insert your actual location and
        it will be checked if you are still on route. If you are still on route you receive the same route you already
        received before, when you are no longer on the route, you receive a new route to the destination from your
        actual location.
         */
        while (true) {
            try {
                // input by the user
                System.out.println("Insert " + (char) 34 + "check" + (char) 34 +
                        " to proof if you are still on the route, insert " + (char) 34 + "exit" + (char) 34 +
                        " to leave the program and insert " + (char) 34 + "restart" + (char) 34 +
                        " to restart the program!");
                input6 = sc.nextLine();
                System.out.println("answer: " + input6);

                // wrong input
                while (!input6.equals("check") && !input6.equals("exit") && !input6.equals("restart")) {
                    System.out.println("You used a word which differs from no or yes! Please select again!");
                    input6 = sc.nextLine();
                    System.out.println("answer: " + input6);
                }

                // restart
                if (input6.equals("restart")) {
                    main(args);
                }

                // exit
                if (input6.equals("exit")) {
                    System.exit(0);
                }

                // check
                if (input6.equals("check")) {

                    System.out.println("Please insert your actual position " +
                            "(the way you did it with origin, destination and waypoint)?");
                    input7 = sc.nextLine();
                    input7 = likeInput(input7);
                    System.out.println("answer: " + input7);
                    stillOnRoute = navigation.stillOnRoute(naviList, input7);

                    if (stillOnRoute) {
                        System.out.println("You are still on route!");
                    }

                    if (!stillOnRoute) {
                        System.out.println("You have left the route, " +
                                "the route gets recalculated from your actual destination!");
                        DirectionsRoute naviListNew = navigation.goneAstray(naviList, input7);

                        output.output(input7, input2, naviListNew);
                        map.showMap();
                    }
                }
            } catch (Exception e) {
                    /*
                    If there is an exception the program restarts this while-loop and the user is able to decide
                    if he like to check again, restart or end the program.
                    */
                System.err.println("The input is wrong cause: " + e.getMessage());
            }

        }
    }
}