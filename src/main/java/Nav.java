import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;


public class Nav {


    private DirectionsRoute currentRoute;
    private Point result;

    public Point geocoder(String insert) throws IOException
    {
 //build gecoder
        MapboxGeocoding.Builder mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
                .query(insert);


        Response<GeocodingResponse> response = mapboxGeocoding.build().executeCall();


//recive the callback

                List<CarmenFeature> results = response.body().features();

                if (results.size() > 0) {

                    // Log the first results Points
                    result = results.get(0).center();
                    System.out.println("onResponse: " + result.toString());

                } else {

                    // No result for your request were found
                    System.out.println("onResponse: No result found");

                }

// return result
                return result;
            }

    /**
     * method which switch between the three different profiles driving, driving-traffic and walking depending on input.
     * @param profile
     * @return
     */
    public String switchProfile(String profile) {
        String finalprofile=null;
        switch (profile) {
            case "car":
            case "Auto":
                finalprofile = DirectionsCriteria.PROFILE_DRIVING;
                break;
            case "traffic":
            case "Stau":
                finalprofile = DirectionsCriteria.PROFILE_DRIVING_TRAFFIC;
                break;
            case "Laufen":
            case "Gehen":
            case "walking":
                finalprofile = DirectionsCriteria.PROFILE_WALKING;
                break;
            case "cycling":
            case "Fahrrad fahren":
            case "Fahrrad":
                finalprofile = DirectionsCriteria.PROFILE_CYCLING;
                break;
            default:  throw new IllegalArgumentException("no legal profile");
        }
        return finalprofile;

        }

    /**
     *Calculates the route from the origin to the destination
     * @param origin where to start
     * @param destination where to end
     * @param profile which transport is used
     * @return the final route
     */
    public DirectionsRoute getRoute(String origin, String destination, String profile) throws IOException{


        Point originLatLng = geocoder(origin); //Adresses were geocoded to GeoJson point
        if(originLatLng== null)
        {
            throw new IllegalArgumentException("Origin not Found");
        }
        Point destinationLatLng =geocoder(destination);
        if(originLatLng== null)
        {
            throw new IllegalArgumentException("Destination not not Found");
        }

        // client with thr routing criteria is build

        MapboxDirections.Builder client = MapboxDirections.builder()
            .origin(originLatLng)
            .destination(destinationLatLng)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(switchProfile(profile)) // call of the method switchprofile with the inputted profile to set the profile
            .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg");

        Response<DirectionsResponse> response = client.build().executeCall();



            //if there is no reslut an Expection is thrown
        if (response.body() == null) {
           throw new IllegalArgumentException("No routes found, make sure you set the right user and access token.");
        } else if (response.body().routes().size() < 1) {
            throw new IllegalArgumentException("No routes found");
        }

        // Retrieve the directions route from the API response
        currentRoute = response.body().routes().get(0);
        System.out.println(currentRoute.legs());

    //route is returned
    return currentRoute;
    }
}

