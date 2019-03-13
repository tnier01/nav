import com.mapbox.geojson.Point;

import java.io.IOException;

public class Prototyp {


    public static void main(String[] args) throws IOException{
        Nav navigation =new Nav();
        Point point1 = Point
                .fromLngLat(51.969173,7.595616);


        Point point2 = Point
                .fromLngLat(41.362023, 2.168638);
    navigation.getRoute("hamburg","köln", "Fahrrad");
    }

}
