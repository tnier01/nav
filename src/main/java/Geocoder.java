import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Geocoder {


    public Point geocoding(String insert) throws IOException
    {
        Point result = null;
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
}
