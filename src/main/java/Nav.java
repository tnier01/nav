import com.mapbox.api.directions.v5.*;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.models.Positions;



import retrofit2.Call;
import retrofit2.Callback;



public class Nav {


    private DirectionsRoute currentRoute;
    private MapboxDirections client;

    private void getRoute(Position origin, Position destination) {


    MapboxDirections client = MapboxDirections.builder()
            .origin(origin)
            .destination(destination)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
            .build();


    client.enqueueCall(new Callback<DirectionsResponse>()

    {
        @Override public void onResponse (retrofit2.Call< DirectionsResponse > call, retrofit2.Response< DirectionsResponse > response){

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

            System.out.println("Distance =" + currentRoute.distance());

    }

        @Override public void onFailure (Call < DirectionsResponse > call, Throwable throwable){

        System.out.println("Error: " + throwable.getMessage());

    }
    });
    }
}

