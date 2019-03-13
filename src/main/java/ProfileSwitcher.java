import com.mapbox.api.directions.v5.DirectionsCriteria;

public class ProfileSwitcher {
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
}
