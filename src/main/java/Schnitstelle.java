import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import retrofit2.Response;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import java.io.IOException;
import java.util.List;



public class Schnitstelle {


    private DirectionsRoute currentRoute;

ProfileSwitcher profileSwitcher = new ProfileSwitcher();



    /**
     *Calculates the route from the origin to the destination
     * @param origin where to start
     * @param destination where to end
     * @param profile which transport is used
     * @throws IOException
     * @return the final route
     */
    public DirectionsRoute getRoute(Point origin, Point destination, String profile) throws IOException{

        // client with thr routing criteria is build

        MapboxDirections.Builder client = MapboxDirections.builder()
            .origin(origin)
            .destination(destination)
            .geometries("polyline")
            .steps(true)
            //.bannerInstructions(true)
            .voiceInstructions(true)
            .voiceUnits("metric")
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(profile) // call of the method switchprofile with the inputted profile to set the profile
            .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg");

        Response<DirectionsResponse> response = client.build().executeCall();

 if(response.isSuccessful()) {

     //if there is no reslut an Expection is thrown
     if (response.body() == null) {
         throw new IllegalArgumentException("No routes found, make sure you set the right user and access token.");
     } else if (response.body().routes().size() < 1) {
         throw new IllegalArgumentException("No routes found");
     }

     // Retrieve the directions route from the API response
     currentRoute = response.body().routes().get(0);
     System.out.println(currentRoute.legs());
 }
 else{
     throw new IllegalArgumentException("Anfrage ist geschietert.");
 }
    //route is returned
    return currentRoute;
    }

/**
 *
 * @param insert the city to geocode
 * @return the geocoded result
 * @throws IOException
 */
public Point geocoding(String insert) throws IOException
        {
        Point result = null;
        //build gecoder
        MapboxGeocoding.Builder mapboxGeocoding = MapboxGeocoding.builder()
        .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
        .query(insert);

//recive the callback
        Response<GeocodingResponse> response = mapboxGeocoding.build().executeCall();

        if (response.isSuccessful()) {
//work with the response

        List<CarmenFeature> results = response.body().features();


        if (results.size() > 0) {

        // Log the first results Points
        result = results.get(0).center();
        System.out.println("onResponse: " + result.toString());

        } else {

        // No result for your request were found
        System.out.println("onResponse: No result found");

        }
        }
        else
        {
        throw new IllegalArgumentException("Anfrage ist geschietert.");
        }
// return result
        return result;
        }
}



