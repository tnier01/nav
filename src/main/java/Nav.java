import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import retrofit2.Response;

import java.io.IOException;


public class Nav {


    private DirectionsRoute currentRoute;

Geocoder geocoder = new Geocoder();
ProfileSwitcher profileSwitcher = new ProfileSwitcher();

    /**
     *Calculates the route from the origin to the destination
     * @param origin where to start
     * @param destination where to end
     * @param profile which transport is used
     * @return the final route
     */
    public DirectionsRoute getRoute(String origin, String destination, String profile) throws IOException{


        Point originLatLng = geocoder.geocoding(origin); //Adresses were geocoded to GeoJson point
        if(originLatLng== null)
        {
            throw new IllegalArgumentException("Origin not Found");
        }
        Point destinationLatLng = geocoder.geocoding(destination);
        if(originLatLng== null)
        {
            throw new IllegalArgumentException("Destination not not Found");
        }

        // client with thr routing criteria is build

        MapboxDirections.Builder client = MapboxDirections.builder()
            .origin(originLatLng)
            .destination(destinationLatLng)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(profileSwitcher.switchProfile(profile)) // call of the method switchprofile with the inputted profile to set the profile
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

