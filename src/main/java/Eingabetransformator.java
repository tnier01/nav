import com.mapbox.geojson.Point;

import java.io.IOException;


public class Eingabetransformator {

    /**
     * Transform a Insert to an Point
     * @param point as String Strretname or String lat,lng
     * @return the resolved Point
     * @throws IOException
     */
    public Point transformPoint(String point) throws IOException {

        Point resultpoint = null;
        if(point.indexOf(',') != -1) {
            // Testet auf positive oder negative  Dezimal- und ganze Zahlen
            String Lat = point.substring(0, point.indexOf(','));
            String Lng = point.substring(point.indexOf(',')+1);

            if (Lat.matches("-?\\d+([.]{1}\\d+)?") && Lng.matches("-?\\d+([.]{1}\\d+)?"))
            {
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
                resultpoint = schnitstelle.geocoding(point); //Adresses were geocoded to GeoJson point
            }
        }
        else {
            Schnitstelle schnitstelle = new Schnitstelle();
            resultpoint = schnitstelle.geocoding(point); //Adresses were geocoded to GeoJson point
        }

        return resultpoint;
    }

    public String transfromProfile(String profile){
        ProfileSwitcher profileSwitcher= new ProfileSwitcher();
        return profileSwitcher.switchProfile(profile);
    }
}
