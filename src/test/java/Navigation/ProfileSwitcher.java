package Navigation;

import com.mapbox.api.directions.v5.DirectionsCriteria;

/**
 * Class for realization of a method to switch the profile.
 */
class ProfileSwitcher {
    /**
     * method which switch between the three different profiles "driving", "driving-traffic", "walking", "cycling" depending on input.
     * If one of the profiles/ cases is entered in the input the finalprofile is set accordingly to the input.
     * finalprofile is the variable with the final selected profile which is returned.
     *
     * @param profile
     * @return finalprofile
     */
    String switchProfile(String profile) {
        String finalprofile;

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
            default:
                throw new IllegalArgumentException("no legal profile");
        }

        return finalprofile;

    }
}
