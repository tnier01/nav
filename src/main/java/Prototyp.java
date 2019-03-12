import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

public class Prototyp {


    public static void main(String[] args){
        Nav navigation =new Nav();
        Point point1 = Point
                .fromLngLat(51.969173,7.595616);


        Point point2 = Point
                .fromLngLat(41.362023, 2.168638);
    navigation.getRoute("hamburg","köln", "car");
    }

}
