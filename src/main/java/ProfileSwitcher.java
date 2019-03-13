import com.mapbox.api.directions.v5.DirectionsCriteria;

public class ProfileSwitcher {
    /**
     * method which switch between the three different profiles "driving", "driving-traffic", "walking", "cycling" depending on input.
     * @param profile
     * @return
     */
    public String switchProfile(String profile) {
        String finalprofile=null;
        switch (profile) {
            case "driving":
                finalprofile = DirectionsCriteria.PROFILE_DRIVING;
                break;
            case "driving-traffic":
                finalprofile = DirectionsCriteria.PROFILE_DRIVING_TRAFFIC;
                break;
            case "walking":
                finalprofile = DirectionsCriteria.PROFILE_WALKING;
                break;
            case "cycling":
                finalprofile = DirectionsCriteria.PROFILE_CYCLING;
                break;
            default:  throw new IllegalArgumentException("no legal profile");
        }
        return finalprofile;

    }
}
