import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class Nav {

    private DirectionsRoute currentRoute;
    private Point result;

    public Point geocoder(String insert)
    {

        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
                .query(insert)
                .build();

        final boolean[] ergebniss = {false};

        mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                List<CarmenFeature> results = response.body().features();

                if (results.size() > 0) {

                    // Log the first results Points
                    result = results.get(0).center();
                    System.out.println("onResponse: " + result.toString());


                } else {

                    // No result for your request were found.
                   System.out.println( "onResponse: No result found");

                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        try {

            //sleep 5 seconds
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
if (result==null)
{
System.out.println("Fehler");
}

    return result;
    }





    public DirectionsRoute getRoute(String origin, String destination) {

        Point originLatLng = geocoder(origin);
        System.out.println(originLatLng);
        Point destinationLatLng =geocoder(destination);

        MapboxDirections client = MapboxDirections.builder()
            .origin(originLatLng)
            .destination(destinationLatLng)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(DirectionsCriteria.PROFILE_DRIVING)
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
        System.out.println(currentRoute.legs());

    }


      @Override
      public void onFailure (Call < DirectionsResponse > call, Throwable throwable){

        System.out.println("Error: " + throwable.getMessage());

    }
    });
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return currentRoute;
    }
}

