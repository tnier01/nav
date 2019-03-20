package Navigation;

import ConnectionMapbox.IMapbox;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.List;


class transformInsertion {

   /**
    * Transform a Insert to an Point
    * @param point as String Streetname or String lat,lng
    * @return the resolved Point
    * @throws IOException
    */
   protected CarmenFeature transformPoint(String point) throws IOException {
       List<CarmenFeature> results;
       CarmenFeature resultFeature = null;
       IMapbox anIMapbox = new IMapbox();
       //check
       if(point.indexOf(',') != -1) {
           // Differs the Point in latitude and longitude
           String Lat = point.substring(0, point.indexOf(','));
           String Lng = point.substring(point.indexOf(',')+2);
           // Test if Lat and Lng are numbers
           if (Lat.matches("-?\\d+([.]{1}\\d+)?") && Lng.matches("-?\\d+([.]{1}\\d+)?"))
           {
               //transform String into Mapbox Point
               Double lng=Double.parseDouble(Lng);
               Double lat=Double.parseDouble(Lat);
               Point resultPoint = Point
                       .fromLngLat(lng,lat);

               results= anIMapbox.geocodeToAdress(resultPoint);


              //System.out.println(resultPoint);

           }
           else
           {

               results = anIMapbox.geocodeToObj(point); //Adresses were geocoded to a List of points
           }
       }
       else {
           results= anIMapbox.geocodeToObj(point); //Adresses were geocoded to a List of points
       }
       if (results.size() > 0) {
           resultFeature = results.get(0);
       }

       return resultFeature;
   }
   /**
    * Transform thr Profile witch the Navigation.ProfileSwitcher
    * @param profile to transform
    * @return the correct Profile
    */

   protected String transformProfile(String profile){
       ProfileSwitcher profileSwitcher= new ProfileSwitcher();
       return profileSwitcher.switchProfile(profile);
   }
}
