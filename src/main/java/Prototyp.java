import com.mapbox.geojson.Point;

import java.io.IOException;

public class Prototyp {


    public static void main(String[] args) throws IOException{
        Routenfinder navigation =new Routenfinder();
        Point point1 = Point
                .fromLngLat(51.969173,7.595616);


        Point point2 = Point
                .fromLngLat(41.362023, 2.168638);
    navigation.gibRoute("hamburg","köln", "Fahrrad");
    }

}
