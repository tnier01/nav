import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import retrofit2.Call;
import retrofit2.Callback;


public class Nav {

    private DirectionsRoute currentRoute;
    private MapboxDirections client;

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


    public void getRoute(Point origin, Point destination, String profile) {

    MapboxDirections client = MapboxDirections.builder()
            .origin(origin)
            .destination(destination)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(switchProfile(profile)) // call of the method switchprofile with the inputted profile to set the profile
            .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
            .build();


    client.enqueueCall(new Callback<DirectionsResponse>()

    {
        @Override
        public void onResponse (retrofit2.Call< DirectionsResponse > call, retrofit2.Response< DirectionsResponse > response){

        if (response.body() == null) {
            System.out.println("No routes found, make sure you set the right user and access token.");
            return;
        } else if (response.body().routes().size() < 1) {
            System.out.println("No routes found");
            return;
        }

        // Retrieve the directions route from the API response
        currentRoute = response.body().routes().get(0);

            // Print some info about the route

            System.out.println("Distance =" + currentRoute.legs());

    }

      @Override
      public void onFailure (Call < DirectionsResponse > call, Throwable throwable){

        System.out.println("Error: " + throwable.getMessage());

    }
    });
    }
}

