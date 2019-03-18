import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.api.staticmap.v1.models.StaticMarkerAnnotation;
import com.mapbox.geojson.*;
import retrofit2.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.mapbox.core.constants.Constants.PRECISION_6;


public class Schnitstelle {


    private DirectionsRoute currentRoute;



    public DirectionsRoute getListRoute(List<Point> waypoints, String profile) throws IOException{

        // client with thr routing criteria is build

        int waypointsSize = waypoints.size();

        MapboxDirections.Builder client = MapboxDirections.builder()
                .geometries("polyline6")
                .steps(true)
                .origin(waypoints.get(0))
                .destination(waypoints.get(waypointsSize-1))
                //.bannerInstructions(true)
                .voiceInstructions(true)
                .voiceUnits("metric")
                .overview(DirectionsCriteria.OVERVIEW_SIMPLIFIED)
                .profile(profile) // call of the method switchprofile with the inputted profile to set the profile
                .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg");

        for (int i = 1; i < waypointsSize-1; i++) {
            client.addWaypoint(waypoints.get(i));
        }

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
           // System.out.println(currentRoute.legs());
        }
        else{
            throw new IllegalArgumentException("Request failed, please try with better insertions");
        }
        //route is returned
        return currentRoute;
    }

/**
 * Transform an Adress into an Mapbox Point
 * @param insert the adress to geocode
 * @return the geocoded result as Point
 * @throws IOException
 */
public Point geocoding(String insert) throws IOException
        {
        Point result= null;

        //build gecoder
        MapboxGeocoding.Builder mapboxGeocoding = MapboxGeocoding.builder()
        .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
        .query(insert);

 //get  the response
        Response<GeocodingResponse> response = mapboxGeocoding.build().executeCall();

        if (response.isSuccessful()) {
//work with the response

        List<CarmenFeature> results = response.body().features();


        if (results.size() > 0) {

        // Log the first results Points
        result = results.get(0).center();
        //System.out.println("onResponse: " + result.toString());

        }
        }
        else
        {
        throw new IllegalArgumentException("Request failed.");
        }
// return result
        return result;
    }


    public List<CarmenFeature> geocodeToObj(String insert) throws IOException {
        List<CarmenFeature> results = null;
        //build geocoder
        MapboxGeocoding.Builder mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg")
                .query(insert);

        //get  the response
        Response<GeocodingResponse> response = mapboxGeocoding.build().executeCall();

        if (response.isSuccessful()) {
            //work with the response

            results = response.body().features();

        }
        return results;
    }

    /**
     * Saves a Map of the route as map
     * @param route the calculated route
     */
        public void getMap(List<Point> waypoints, DirectionsRoute route)
        {
            // transforms the Polyline into a Feature
            Feature directionsRouteFeature = Feature.fromGeometry(LineString.fromPolyline(route.geometry(), PRECISION_6));

            //transform the Feature into an JSON
            final String JSON=directionsRouteFeature.toJson();


//transform the Json into an valid GeoJson
            GeoJson geoJson= new GeoJson() {
                @Override
                public String type() {
                    return "LineString";
                }

                @Override
                public String toJson() {
                    return JSON.substring(29, JSON.length()-1);
                }
                @Override
                public BoundingBox bbox() {
                    return null;
                }
            };

            //create a list of List of StaticMarker annotatuions
            List<StaticMarkerAnnotation> marker = new ArrayList<>();

            //create a StaticMarkeAnnotation from the origin
            StaticMarkerAnnotation originMarke = StaticMarkerAnnotation.builder()
                    .lnglat(waypoints.get(0))
                    .color("ff0000")
                    .build();

            marker.add(originMarke);
            for(int i=1; i<waypoints.size()-1;i++)
            {
                StaticMarkerAnnotation marke = StaticMarkerAnnotation.builder()
                        .lnglat(waypoints.get(i))
                        .color("0000ff")
                        .build();
                marker.add(marke);
            }
            //create a StaticMarkeAnnotation from the destination
            StaticMarkerAnnotation destinationMarke = StaticMarkerAnnotation.builder()
                    .lnglat(waypoints.get(waypoints.size()-1))
                    .color("00ff00")
                    .build();

            marker.add(destinationMarke);

            // create a Valid URL to get a Map with the route and Markes for Start and Origin
            MapboxStaticMap staticImage = MapboxStaticMap.builder()
                    .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZTNoZ3czMnYxcTNrczE2OTZxbHdsMiJ9.aHG4oyq2cE57oNFq0irkbA")
                    .styleId(StaticMapCriteria.NAVIGATION_GUIDANCE_DAY)
                    .cameraAuto(true)
                    .staticMarkerAnnotations(marker)
                    .geoJson(geoJson)
                    .width(500) // Image width
                    .height(500) // Image height
                    .retina(true) // Retina 2x image will be returned
                    .build();

            //open the Stream with then builded URL
            try {

                String imageUrl = staticImage.url().toString();
                String destinationFile = "map.jpg";

                URL url = new URL(imageUrl);
                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(destinationFile);

                //save the image
                byte[] b = new byte[2048];
                int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
}



