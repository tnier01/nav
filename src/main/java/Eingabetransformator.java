import com.mapbox.geojson.Point;

import java.io.IOException;


public class Eingabetransformator {

    /**
     * Transform a Insert to an Point
     * @param point as String Strretname or String lat,lng
     * @return the resolved Point
     * @throws IOException
     */
    public Point transfromPoint(String point) throws IOException {

        Point resultpoint = null;
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
                resultpoint = Point
                        .fromLngLat(lng,lat);
                System.out.println(resultpoint);

            }
            else
            {
                System.out.println("keine Koordinaten eingegeben");
                Schnitstelle schnitstelle = new Schnitstelle();
                resultpoint = schnitstelle.geocoding(point); //Adresses were geocoded to Mapbox point
            }
        }
        else {
            Schnitstelle schnitstelle = new Schnitstelle();
            resultpoint = schnitstelle.geocoding(point); //Adresses were geocoded to MApbox point
        }

        return resultpoint;
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
