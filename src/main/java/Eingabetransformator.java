import com.mapbox.geojson.Point;

import java.io.IOException;


public class Eingabetransformator {

    public Point transformPoint(String point) throws IOException {

        Point resultpoint = null;
        if(point.indexOf(',') != -1) {
            // Testet auf positive oder negative  Dezimal- und ganze Zahlen
            String Lat = point.substring(0, point.indexOf(','));
            String Lng = point.substring(point.indexOf(','));
            if (Lat.matches("-?\\d+([.]{1}\\d+)?") && Lng.matches("-?\\d+([.]{1}\\d+)?"))
                System.out.println("positive oder negative Dezimalzahl oder ganze Zahl!");
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
