// We love beer

import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prototyp {

    private static String likeInput(String input) throws IOException {
        Routenfinder navigation = new Routenfinder();
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
     * modularized method, in case the input differs from "yes" and "no".
     *
     * @param x the input
     * @param scanner the Scanner which reads out the input
     */
    private static String ifInputWrong(String x, Scanner scanner) {
        while (!x.equals("no") && !x.equals("yes")) {
            System.out.println("you used a word which differs from no or yes! Please select again!");
            x = scanner.nextLine();
            System.out.println("input: " + x);
        }
        return x;
    }

    /**
     * modularized method, for a further input, a new waypoint
     *
     * @param scanner the Scanner, which reads the input
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
     * main method which controls the input and output on the console
     * asks for origin and destination and delivers navigation instructions
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Routenfinder navigation = new Routenfinder();

        Offroute offroute = new Offroute();
        DirectionsRoute naviList;
        String input1, input2, input3, input4, input5, input6, input7, input8;
        List<String> waypoints = new ArrayList<>();
        boolean stillOnRoute = false;
        boolean running = true;


        /*
        input
        If there is no exception the route is returned
         */
        // Create a new object for the input
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Höllennavigationsmaschiene");
                System.out.println("Origin and Destination as Coordinate: Either you use lat,lng e.g. 51.9606649, 7.6261347\n" +
                        "or a City/ Street/ postcode e.g. Martin-Luther-King-Weg 20 Münster");
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

                System.out.println("which profile are you using (selection: driving, driving-traffic, walking, cycling)?");
                // Read the third input in the string "input3"
                input3 = sc.nextLine();
                System.out.println("profile input: " + input3);

                /*
                 If the input is driving, walking or cycling it is possible to enter 25 different waypoints and besides
                 one origin and one destination.
                  */
                if (input3.equals("driving") || input3.equals("walking") || input3.equals("cycling")) {

                    for (int i = 0; i < 23; i++) {

                        // input decision if there will be an further waypoint
                        System.out.println("would you like to add another point? (yes/no)");
                        input5 = sc.nextLine();

                        // if there is a wrong word which differs from yes or no, repeat the selection
                        input5= ifInputWrong(input5, sc);

                        // if the users wants a further waypoint he can insert it
                        if (input5.equals("yes")) {
                            // if the limit of waypoints is reached the user gets a notification
                            if (i > 21) {
                                System.out.println("last possible waypoint!");
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
                if (input3.equals("driving-traffic")) {

                    // input decision if there will be an further waypoint
                    System.out.println("would you like to add another point? (yes/no)");
                    input5 = sc.nextLine();

                    // if there is a wrong word which differs from yes or no, repeat the selection
                    input5 = ifInputWrong(input5, sc);

                    // if input is "yes" there is the possibility to add a new waypoint, if no the route gets calculated
                    if (input5.equals("yes")) {
                        inputWaypoint(sc, waypoints);
                    }
                }

                // route calculation
                System.out.println("route calculation:");
                naviList = navigation.getListRoute(waypoints, input3);
                break;

            } catch (Exception e) {
                    /*
                    If there is an exception the user is able to decide on his own if he likes to restart or end the program.
                     */
                System.err.println("The input is wrong cause: " + e.getMessage()); // returns the message concerning the actual exception
                System.out.println("Do you like to restart the program (yes/no)?");
                input4 = sc.nextLine();
                System.out.println("answer: " + input4);
                // waypoints must be cleared, because otherwise there are doubled destination and origin in the result
                waypoints.clear();

                // if the user do not wont to restart the program ends
                if (input4.equals("no")) {
                    System.exit(1);
                }

                // if there is a wrong word which differs from yes or no, repeat the selection
                ifInputWrong(input4, sc);

            }
        }

       Ausgabe output = new Ausgabe();
        output.output(input1, input2, naviList);
        //
        Map map = new Map();
        map.showMap();

        while (running) {
            System.out.println("Insert " + (char) 34 + "check " + (char) 34 + "to proof if you are still on the route, insert " + (char) 34 + "exit " + (char) 34 + "to leave the program and insert " + (char) 34 + "restart " + (char) 34 + "to restart the program");
            input6 = sc.nextLine();
            System.out.println("answer: " + input6);

            while (!input6.equals("check") && !input6.equals("exit") && !input6.equals("restart")) {
                System.out.println("you used a word which differs from no or yes! Please select again!");
                input6 = sc.nextLine();
                System.out.println("answer: " + input6);
            }

            if (input6.equals("restart")) {
                main(args);
            }

            if (input6.equals("exit")) {
                System.exit(1);
            }

            if (input6.equals("check")) {

                System.out.println("Please insert your actual position (the way you did it with origin, destination and waypoint)?");
                input7 = sc.nextLine();
                System.out.println("answer: " + input7);
                stillOnRoute = offroute.stillOnRoute(naviList, input7);

                if (stillOnRoute == true) {
                    System.out.println("You are still on route!");
                }

                if (stillOnRoute == false) {
                    System.out.println("You have left the route, the route gets recalculated from your actual destination1");
                    DirectionsRoute naviListNew = navigation.goneAstray(naviList, input7);

                    output.output(input1,input2, naviListNew);
                    map.showMap();
                }
            }

        }
    }
}