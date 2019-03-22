package UserInterace;

import Navigation.RouteFinder;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for implementation of the UserInterace.Prototype, at least our user-interface.
 */
class Prototype {
    static final int MAX_WAYPOINTS = 23;
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
     * @param args arguments needed to start the main method
     */
    public static void main(String[] args) {
        RouteFinder navigation = new RouteFinder();

        DirectionsRoute naviList;

        String inputOrigin,
                inputDestination;
        List<String> waypoints = new ArrayList<>();
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

                // Read the first input in the string "inputOrigin"
                inputOrigin = sc.nextLine();
                inputOrigin = doYouLikeInput(inputOrigin);
                System.out.println("origin input: " + inputOrigin);
                waypoints.add(inputOrigin);

                System.out.println("Where do you would like to end the route?");
                // Read the second input in the string "inputDestination"
                inputDestination = sc.nextLine();
                inputDestination = doYouLikeInput(inputDestination);
                System.out.println("destination input: " + inputDestination);
                waypoints.add(inputDestination);

                System.out.println("Which profile are you using " +
                        "(selection: driving, driving-traffic, walking, cycling)?");
                // Read the third input in the string "inputProfile"
                String inputProfile = sc.nextLine();
                System.out.println("profile input: " + inputProfile);

                boolean validInput = false;
                while (!validInput) {
                    String inputLikeToAddNewWaypoint;
                /*
                 If the input is driving, walking or cycling it is possible to enter 25 different waypoints and besides
                 one origin and one destination.
                  */
                    if (inputProfile.equals("driving") || inputProfile.equals("walking")
                            || inputProfile.equals("cycling")) {
                        validInput = true;

                        for (int i = 0; i < MAX_WAYPOINTS; i++) {
                            // input decision if there will be an further waypoint
                            System.out.println("Would you like to add another point (yes/no)?");
                            System.out.println("You are still able to add " + (MAX_WAYPOINTS - i) + " waypoints");
                            inputLikeToAddNewWaypoint = sc.nextLine();

                            // if there is a wrong word which differs from yes or no, repeat the selection
                            inputLikeToAddNewWaypoint = ifInputNotYesOrNo(inputLikeToAddNewWaypoint, sc);

                            // if the users wants a further waypoint he can insert it
                            if (inputLikeToAddNewWaypoint.equals("yes")) {
                                // if the limit of waypoints is reached the user gets a notification
                                if (i > 21) {
                                    System.out.println("Last possible waypoint!");
                                }
                                inputWaypoint(sc, waypoints);
                            }
                            // if the user wants no new waypoint the route is printed
                            if (inputLikeToAddNewWaypoint.equals("no")) {
                                break;
                            }
                        }
                    }

                /*
                 If the input is driving-traffic it is possible to enter one more waypoint and besides
                 one origin and one destination.
                  */
                    else if (inputProfile.equals("driving-traffic")) {
                        validInput = true;

                        // input decision if there will be an further waypoint
                        System.out.println("Would you like to add another point (yes/no)?");
                        inputLikeToAddNewWaypoint = sc.nextLine();

                        // if there is a wrong word which differs from yes or no, repeat the selection
                        inputLikeToAddNewWaypoint = ifInputNotYesOrNo(inputLikeToAddNewWaypoint, sc);

                        // if input is "yes" there is the possibility to add a new waypoint, if no the route gets calculated
                        if (inputLikeToAddNewWaypoint.equals("yes")) {
                            inputWaypoint(sc, waypoints);
                        }
                    } else {
                        System.out.println("No legal profile! Please select again");
                        inputProfile = sc.nextLine();
                    }
                }

                // route calculation
                System.out.println("route calculation:");
                naviList = navigation.getListRoute(waypoints, inputProfile);
                break;

            } catch (Exception e) {
                    /*
                    If there is an exception the user is able to decide on his own
                    if he likes to restart or end the program.
                     */
                // returns the message concerning the actual exception
                System.err.println("The input is wrong cause: " + e.getMessage());
                System.out.println("Do you like to restart the program (yes/no)?");
                String inputLikeToRestart = sc.nextLine();
                System.out.println("answer: " + inputLikeToRestart);
                // waypoints must be cleared, because otherwise there are doubled destination and origin in the result
                waypoints.clear();

                // if the user do not wont to restart the program ends
                while (true) {
                    if (inputLikeToRestart.equals("no")) {
                        System.exit(0);
                    } else if (inputLikeToRestart.equals("yes")) {
                        break;
                    }
                    // if there is a wrong word which differs from yes or no, repeat the selection
                    inputLikeToRestart = ifInputNotYesOrNo(inputLikeToRestart, sc);
                }
            }
        }

        // output with
        Output output = new Output();
        output.output(inputOrigin, inputDestination, naviList);
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
                String inputWantToCheck = sc.nextLine();
                System.out.println("answer: " + inputWantToCheck);

                // wrong input
                while (!inputWantToCheck.equals("check") && !inputWantToCheck.equals("exit")
                        && !inputWantToCheck.equals("restart")) {
                    System.out.println("You used a word which differs from check,exit or restart! " +
                            "Please select again!");
                    inputWantToCheck = sc.nextLine();
                    System.out.println("answer: " + inputWantToCheck);
                }

                // restart
                if (inputWantToCheck.equals("restart")) {
                    main(args);
                }

                // exit
                if (inputWantToCheck.equals("exit")) {
                    System.exit(0);
                }

                String inputActualPosition;

                // check
                if (inputWantToCheck.equals("check")) {

                    System.out.println("Please insert your actual position " +
                            "(the way you did it with origin, destination and waypoint)?");
                    inputActualPosition = sc.nextLine();
                    inputActualPosition = doYouLikeInput(inputActualPosition);
                    System.out.println("answer: " + inputActualPosition);
                    boolean stillOnRoute = navigation.stillOnRoute(naviList, inputActualPosition);

                    if (stillOnRoute) {
                        System.out.println("You are still on route!");
                    }

                    if (!stillOnRoute) {
                        System.out.println("You have left the route, " +
                                "the route gets recalculated from your actual destination!");
                        DirectionsRoute naviListNew = navigation.goneAstray(naviList, inputActualPosition);

                        output.output(inputActualPosition, inputDestination, naviListNew);
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

    /**
     * Method to check if the user is content with the by the program proposed location.
     * The user insert a location, then the program is printing the location which the API is recognizing with regard
     * to the input of the user. If the user is content with this location, the program use this location, if not
     * this method gets called recursively.
     *
     * @param input the street/ coordinate of the actual address
     * @return the final input which gets used
     * @throws IOException if connection to mapbox fails
     */
    private static String doYouLikeInput(String input) throws IOException {
        Scanner sc = new Scanner(System.in);
        try {
            RouteFinder navigation = new RouteFinder();
            // Create a new object for the input
            System.out.println(navigation.getAddress(input));
            System.out.println("Is this the right address (yes/no)?");

            String inputLikeTheAddress = sc.nextLine();
            System.out.println("input: " + inputLikeTheAddress);

            inputLikeTheAddress = ifInputNotYesOrNo(inputLikeTheAddress, sc);

            if (inputLikeTheAddress.equals("yes")) {
                return input;
            } else {
                System.out.println("Insert a more detailed address!");
                String inputDetailedAddress = sc.nextLine();
                System.out.println("input: " + inputDetailedAddress);

                return doYouLikeInput(inputDetailedAddress);
            }

            /*
            If the input is not assignably to a location by the API,
            the user is able to end the program or the repeat the input.
             */
        } catch (IllegalArgumentException e) {
            System.out.println("The input is wrong because: " + e.getMessage());
            System.out.println("Please insert an other description of the location or end the program with "
                    + (char) 34 + "exit" + (char) 34 + " !");
            // Create a new object for the input
            input = sc.nextLine();

            if (input.equals("exit")) {
                System.exit(0);
            }

            return doYouLikeInput(input);
        }
    }

    /**
     * Method, in case the input differs from "yes" and "no".
     *
     * @param inputNotYesOrNo the input which differs from no or yes
     * @param scanner         the used scanner
     * @return correct input
     */
    private static String ifInputNotYesOrNo(String inputNotYesOrNo, Scanner scanner) {
        while (!inputNotYesOrNo.equals("no") && !inputNotYesOrNo.equals("yes")) {
            System.out.println("You used a word which differs from no or yes! Please select again!");
            inputNotYesOrNo = scanner.nextLine();
            System.out.println("input: " + inputNotYesOrNo);
        }

        return inputNotYesOrNo;
    }

    /**
     * Method, for a further input, a new waypoint
     *
     * @param scanner   the Scanner, which reads the input
     * @param waypoints the list of waypoints to visit
     */
    private static void inputWaypoint(Scanner scanner, List<String> waypoints) throws IOException {
        System.out.println("Please input the new waypoint!");
        String waypoint = scanner.nextLine();
        waypoint = doYouLikeInput(waypoint);

        System.out.println("new waypoint: " + waypoint);
        // the new waypoint gets insert at the last place before destination
        waypoints.add(waypoints.size() - 1, waypoint);
        System.out.println("actual route: " + waypoints);
    }
}

