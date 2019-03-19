import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Eingabetransformator {

    /**
     * Transform a Insert to an Point
     * @param point as String Streetname or String lat,lng
     * @return the resolved Point
     * @throws IOException
     */
    public Point transformPoint(String point) throws IOException {
        List<CarmenFeature> results= new ArrayList<>();
        Point resultPoint = null;
        //check
        if(point.indexOf(',') != -1) {
            // Differs the Point in latitude and longitude
            String Lat = point.substring(0, point.indexOf(','));
            String Lng = point.substring(point.indexOf(',')+1);
            // Test if Lat and Lng are numbers
            if (Lat.matches("-?\\d+([.]{1}\\d+)?") && Lng.matches("-?\\d+([.]{1}\\d+)?"))
            {
                //transform String into Mapbox Point
                Double lng=Double.parseDouble(Lng);
                Double lat=Double.parseDouble(Lat);
                resultPoint = Point
                        .fromLngLat(lng,lat);
               //System.out.println(resultPoint);

            }
            else
            {
                Schnitstelle schnitstelle = new Schnitstelle();
                results = schnitstelle.geocodeToObj(point); //Adresses were geocoded to a List of points
            }
        }
        else {
            Schnitstelle schnitstelle = new Schnitstelle();
            results= schnitstelle.geocodeToObj(point); //Adresses were geocoded to a List of points
        }

        if (results.size() > 0) {
            // Log the first results Point.
            resultPoint = results.get(0).center();
        }

        return resultPoint;
    }
    /**
     * Transform thr Profile witch the ProfileSwitcher
     * @param profile to transform
     * @return the correct Profile
     */

    public String transformProfile(String profile){
        ProfileSwitcher profileSwitcher= new ProfileSwitcher();
        return profileSwitcher.switchProfile(profile);
    }
}
