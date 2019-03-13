import com.mapbox.geojson.Point;
import java.io.IOException;
import java.util.Scanner;

public class Prototyp {


    public static void main(String[] args) throws IOException{
        Routenfinder navigation =new Routenfinder();


        Point point2 = Point
                .fromLngLat(41.362023, 2.168638);

        /*
        input
         */

        // Create a new object for the input
        Scanner sc = new Scanner(System.in);

        System.out.println("From where would you like to start your route?");
        // Read the first input in the string "input1"
        String input1 = sc.nextLine();
        System.out.println("origin input: " + input1);

        System.out.println("Where do you would like to end the route?");
        // Read the second input in the string "input2"
        String input2 = sc.nextLine();
        System.out.println("destination input: " + input2);

        System.out.println("which profile are you using (selection: driving, driving-traffic, walking, cycling)?");
        // Read the third input in the string "input3"
        String input3 = sc.nextLine();
        System.out.println("profile input: " + input3);

        System.out.println("route calculation:");

        //Close the scanner (not necessary, but recommended)
        sc.close();

        // route calculation
        navigation.gibRoute(input1, input2, input3);



    }
}
