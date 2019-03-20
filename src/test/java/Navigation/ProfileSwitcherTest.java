package Navigation;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileSwitcherTest {

    @Test
    void switchProfile() {
        ProfileSwitcher testPS = new ProfileSwitcher();
    /*
    class Navigation.ProfileSwitcher
        method 1: switchProfile (String profile): void
            possibilities:*/
        //switchProfile(" ") -> IllegalArgumentException("no legal profile")
        //assertEquals(testPS.switchProfile(" "),IllegalArgumentException)
        Throwable ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    testPS.switchProfile("car");
                },"no IllegalArgumentException thrown");
        Assert.assertEquals(ex.getMessage(),"no legal profile");
        // switchProfile("driving") -> "DirectionsCriteria.PROFILE_DRIVING"
        Assert.assertEquals(testPS.switchProfile("driving"), DirectionsCriteria.PROFILE_DRIVING);
        //switchProfile("driving-traffic") -> "DirectionsCriteria.PROFILE_DRIVING_TRAFFIC"
        Assert.assertEquals(testPS.switchProfile("driving-traffic"), DirectionsCriteria.PROFILE_DRIVING_TRAFFIC);
        //switchProfile("walking") -> "DirectionsCriteria.PROFILE_WALKING"
        Assert.assertEquals(testPS.switchProfile("walking"),DirectionsCriteria.PROFILE_WALKING);
        //switchProfile("cycling") -> "DirectionsCriteria.PROFILE_CYCLING"
        Assert.assertEquals(testPS.switchProfile("cycling"),DirectionsCriteria.PROFILE_CYCLING);

    }
    }
