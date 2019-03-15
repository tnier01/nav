import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import retrofit2.Response;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.StaticMapCriteria;
import com.mapbox.geojson.*;
import retrofit2.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import static com.mapbox.core.constants.Constants.PRECISION_6;


public class Schnitstelle {


    private DirectionsRoute currentRoute;

ProfileSwitcher profileSwitcher = new ProfileSwitcher();



    /**
     *Calculates the route from the origin to the destination
     * @param origin where to start as Mapbox Pint
     * @param destination where the destination is as Point
     * @param profile which transport should be used
     * @throws IOException
     * @return the final route as DirectionsRoute
     */
    public DirectionsRoute getRoute(Point origin, Point destination, String profile) throws IOException{

        // client with thr routing criteria is build

        MapboxDirections.Builder client = MapboxDirections.builder()
            .origin(origin)
            .destination(destination)
            .geometries("polyline6")
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


    public DirectionsRoute getListRoute(List<Point> waypoints, String profile) throws IOException{

        // client with thr routing criteria is build

        int waypointsSize = waypoints.size();

        MapboxDirections.Builder client = MapboxDirections.builder()
                .geometries("polyline")
                .steps(true)
                .origin(waypoints.get(0))
                .destination(waypoints.get(waypointsSize-1))
                //.bannerInstructions(true)
                .voiceInstructions(true)
                .voiceUnits("metric")
                .overview(DirectionsCriteria.OVERVIEW_FULL)
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
            System.out.println(currentRoute.legs());
        }
        else{
            throw new IllegalArgumentException("Anfrage ist geschietert.");
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
        Point result = null;

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
        System.out.println("onResponse: " + result.toString());

        } else {

        // No result for your request were found
        System.out.println("onResponse: No result found");

        }
        }
        else
        {
        throw new IllegalArgumentException("Request failed.");
        }
// return result
        return result;
        }

        public void getMap(final Point origin, final Point destion, DirectionsRoute route)
        {
            GeoJson directionsRouteFeature = Feature.fromGeometry(LineString.fromPolyline(route.geometry(), PRECISION_6));
            final String JSON=directionsRouteFeature.toJson();
            System.out.println(JSON);

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

            MapboxStaticMap staticImage = MapboxStaticMap.builder()
                    .accessToken("pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZTNoZ3czMnYxcTNrczE2OTZxbHdsMiJ9.aHG4oyq2cE57oNFq0irkbA")
                    .styleId(StaticMapCriteria.NAVIGATION_GUIDANCE_DAY)
                    .cameraAuto(true)
                    .geoJson(geoJson)
                    .width(500) // Image width
                    .height(500) // Image height
                    .retina(true) // Retina 2x image will be returned
                    .build();



            try {

                String imageUrl = staticImage.url().toString();
                //imageUrl="https://api.mapbox.com/v4/mapbox.emerald/geojson(%7B%22type%22%3A%22LineString%22%2C%22coordinates%22%3A%5B%5B7.595806%2C51.969207%5D%2C%5B7.595676%2C51.96887%5D%2C%5B7.595765%2C51.968864%5D%2C%5B7.595805%2C51.96886%5D%2C%5B7.59583%2C51.968858%5D%2C%5B7.59615%2C51.96883%5D%2C%5B7.596299%2C51.968812%5D%2C%5B7.596374%2C51.968802%5D%2C%5B7.596371%2C51.968751%5D%2C%5B7.596368%2C51.968191%5D%2C%5B7.596387%2C51.967617%5D%2C%5B7.596397%2C51.967475%5D%2C%5B7.596406%2C51.967413%5D%2C%5B7.596417%2C51.967358%5D%2C%5B7.596428%2C51.967321%5D%2C%5B7.596441%2C51.967278%5D%2C%5B7.596461%2C51.967231%5D%2C%5B7.596491%2C51.967172%5D%2C%5B7.596534%2C51.967108%5D%2C%5B7.596581%2C51.967044%5D%2C%5B7.596628%2C51.966989%5D%2C%5B7.596676%2C51.966944%5D%2C%5B7.596731%2C51.966898%5D%2C%5B7.596807%2C51.966843%5D%2C%5B7.596907%2C51.966778%5D%2C%5B7.596989%2C51.966735%5D%2C%5B7.597135%2C51.966669%5D%2C%5B7.597324%2C51.966587%5D%2C%5B7.597492%2C51.966514%5D%2C%5B7.597672%2C51.966424%5D%2C%5B7.59779%2C51.966488%5D%2C%5B7.597848%2C51.966522%5D%2C%5B7.597888%2C51.966544%5D%2C%5B7.598048%2C51.966637%5D%2C%5B7.598128%2C51.966678%5D%2C%5B7.598174%2C51.966693%5D%2C%5B7.598229%2C51.966707%5D%2C%5B7.598284%2C51.966712%5D%2C%5B7.598391%2C51.966716%5D%2C%5B7.598547%2C51.966715%5D%2C%5B7.599083%2C51.966707%5D%2C%5B7.599458%2C51.966705%5D%2C%5B7.599645%2C51.966703%5D%2C%5B7.600016%2C51.966701%5D%2C%5B7.600556%2C51.9667%5D%2C%5B7.60061%2C51.966695%5D%2C%5B7.600662%2C51.966683%5D%2C%5B7.600857%2C51.966603%5D%2C%5B7.600909%2C51.96664%5D%2C%5B7.600957%2C51.966661%5D%2C%5B7.601%2C51.966678%5D%2C%5B7.601065%2C51.966691%5D%2C%5B7.601125%2C51.966699%5D%2C%5B7.60135%2C51.966675%5D%2C%5B7.601572%2C51.966671%5D%2C%5B7.601642%2C51.966663%5D%2C%5B7.601675%2C51.966647%5D%2C%5B7.601702%2C51.966626%5D%2C%5B7.601757%2C51.966564%5D%2C%5B7.601723%2C51.9664%5D%2C%5B7.601677%2C51.966184%5D%2C%5B7.601628%2C51.96592%5D%2C%5B7.601583%2C51.965667%5D%2C%5B7.601559%2C51.9655%5D%2C%5B7.601539%2C51.965294%5D%2C%5B7.601533%2C51.965202%5D%2C%5B7.601526%2C51.965064%5D%2C%5B7.601523%2C51.964955%5D%2C%5B7.60152%2C51.964792%5D%2C%5B7.601525%2C51.964682%5D%2C%5B7.601535%2C51.964561%5D%2C%5B7.60154%2C51.964515%5D%2C%5B7.601556%2C51.964394%5D%2C%5B7.601584%2C51.964252%5D%2C%5B7.601623%2C51.964077%5D%2C%5B7.601645%2C51.963996%5D%2C%5B7.601668%2C51.96392%5D%2C%5B7.601713%2C51.963779%5D%2C%5B7.601786%2C51.963583%5D%2C%5B7.601852%2C51.963428%5D%2C%5B7.601974%2C51.963121%5D%2C%5B7.602076%2C51.962859%5D%2C%5B7.602158%2C51.962633%5D%2C%5B7.602235%2C51.962403%5D%2C%5B7.602285%2C51.962243%5D%2C%5B7.602312%2C51.962146%5D%2C%5B7.602348%2C51.962%5D%2C%5B7.602377%2C51.961843%5D%2C%5B7.602391%2C51.961739%5D%2C%5B7.602399%2C51.96165%5D%2C%5B7.602404%2C51.961579%5D%2C%5B7.602403%2C51.961331%5D%2C%5B7.602402%2C51.961261%5D%2C%5B7.602395%2C51.961141%5D%2C%5B7.602389%2C51.96099%5D%2C%5B7.602385%2C51.960873%5D%2C%5B7.602383%2C51.960736%5D%2C%5B7.602384%2C51.960493%5D%2C%5B7.602388%2C51.960337%5D%2C%5B7.602393%2C51.960221%5D%2C%5B7.6024%2C51.960096%5D%2C%5B7.60242%2C51.959816%5D%2C%5B7.60243%2C51.959696%5D%2C%5B7.602437%2C51.959604%5D%2C%5B7.602447%2C51.95953%5D%2C%5B7.602652%2C51.959596%5D%2C%5B7.602805%2C51.959648%5D%2C%5B7.603368%2C51.959851%5D%2C%5B7.603909%2C51.960057%5D%2C%5B7.604239%2C51.960227%5D%2C%5B7.604665%2C51.960407%5D%2C%5B7.605367%2C51.960687%5D%2C%5B7.605752%2C51.96084%5D%2C%5B7.606053%2C51.960963%5D%2C%5B7.60654%2C51.961155%5D%2C%5B7.60673%2C51.961206%5D%2C%5B7.606964%2C51.961232%5D%2C%5B7.607212%2C51.961229%5D%2C%5B7.607828%2C51.961196%5D%2C%5B7.608351%2C51.961163%5D%2C%5B7.608598%2C51.961154%5D%2C%5B7.608772%2C51.961184%5D%2C%5B7.609588%2C51.961486%5D%2C%5B7.609743%2C51.961551%5D%2C%5B7.609858%2C51.961604%5D%2C%5B7.609947%2C51.961648%5D%2C%5B7.610016%2C51.961683%5D%2C%5B7.610078%2C51.961716%5D%2C%5B7.610153%2C51.961753%5D%2C%5B7.610246%2C51.961792%5D%2C%5B7.610328%2C51.961816%5D%2C%5B7.610402%2C51.961834%5D%2C%5B7.610481%2C51.96185%5D%2C%5B7.610559%2C51.961865%5D%2C%5B7.610676%2C51.961885%5D%2C%5B7.610806%2C51.961903%5D%2C%5B7.610921%2C51.961916%5D%2C%5B7.611042%2C51.961925%5D%2C%5B7.611174%2C51.961933%5D%2C%5B7.611268%2C51.961935%5D%2C%5B7.611368%2C51.961931%5D%2C%5B7.611607%2C51.961911%5D%2C%5B7.612054%2C51.96186%5D%2C%5B7.612198%2C51.961847%5D%2C%5B7.612331%2C51.961836%5D%2C%5B7.612435%2C51.961832%5D%2C%5B7.612711%2C51.961824%5D%2C%5B7.612818%2C51.961824%5D%2C%5B7.612927%2C51.961826%5D%2C%5B7.61304%2C51.961832%5D%2C%5B7.61313%2C51.96184%5D%2C%5B7.613215%2C51.96185%5D%2C%5B7.61385%2C51.961932%5D%2C%5B7.614045%2C51.96195%5D%2C%5B7.614211%2C51.961964%5D%2C%5B7.614289%2C51.961968%5D%2C%5B7.614372%2C51.961969%5D%2C%5B7.614493%2C51.961969%5D%2C%5B7.61471%2C51.961969%5D%2C%5B7.61471%2C51.962056%5D%2C%5B7.61471%2C51.962087%5D%2C%5B7.6147%2C51.962209%5D%2C%5B7.614665%2C51.962267%5D%2C%5B7.614599%2C51.962374%5D%2C%5B7.614512%2C51.962524%5D%2C%5B7.614429%2C51.96265%5D%2C%5B7.614375%2C51.962737%5D%2C%5B7.614393%2C51.9629%5D%2C%5B7.61534%2C51.96286%5D%2C%5B7.615483%2C51.962914%5D%2C%5B7.615579%2C51.962999%5D%2C%5B7.615616%2C51.963091%5D%2C%5B7.615637%2C51.963529%5D%2C%5B7.615669%2C51.964064%5D%2C%5B7.615633%2C51.964131%5D%2C%5B7.615571%2C51.964179%5D%2C%5B7.615502%2C51.964224%5D%2C%5B7.615385%2C51.964246%5D%2C%5B7.614468%2C51.964287%5D%2C%5B7.614351%2C51.964369%5D%2C%5B7.613649%2C51.96439%5D%2C%5B7.613675%2C51.964501%5D%2C%5B7.613717%2C51.964796%5D%2C%5B7.613738%2C51.964938%5D%2C%5B7.613775%2C51.965198%5D%2C%5B7.613859%2C51.965344%5D%2C%5B7.613905%2C51.965525%5D%2C%5B7.613935%2C51.965613%5D%2C%5B7.614002%2C51.965638%5D%2C%5B7.614147%2C51.965634%5D%2C%5B7.614342%2C51.965615%5D%5D%7D)/auto/600x600@2x.png?access_token=pk.eyJ1IjoibmljazEyMTIiLCJhIjoiY2pvZWp1ZHQyMDlmZjNxcGlxaGMyd20wdyJ9.8wLTCZ-eXC9AxijlozQfhg";
                System.out.println(imageUrl);
                String destinationFile = "map.jpg";

                URL url = new URL(imageUrl);
                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(destinationFile);

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



