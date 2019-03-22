package Navigation;

import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TransformInsertionTest {

    @Test
    void transformPoint() throws IOException {
        TransformInsertion testEt = new TransformInsertion();
        /*
    class Navigation.TransformInsertion

        method 1: transformPoint(String point): Point
            possibilities:*/
        // input is not a point or a existing location -> IOException
        //assertEquals(testEt.transformPoint("no Point"), IOException)
        Throwable ex= Assertions.assertThrows(ServicesException.class,
                () -> {
                    testEt.transformPoint("");
                },
                "no IOException thrown in transformPoint");
        Assert.assertEquals(ex.getMessage(), "A query with at least one character or digit is required.");
        // example 1: transformPoint("hamburg") -> [10.0, 53.55]
        Point p=Point.fromLngLat(10.0, 53.55);

        Assert.assertEquals(testEt.transformPoint("hamburg").center(), p);
        p=Point.fromLngLat(9.999952, 53.54987);
        // example 2: transformPoint("10.0, 53.55") -> [9.999953, 53.54978]
        Assert.assertEquals(testEt.transformPoint("53.55, 10.0").center(), p);
        // example 3: transformPoint("köln") -> [6.95778, 50.94222]
        p= Point.fromLngLat(6.95778, 50.94222);
        Assert.assertEquals(testEt.transformPoint("köln").center(), p);

        p=Point.fromLngLat(6.958112, 50.94232);

        // example 4: transformPoint("6.95778, 50.94222") -> [6.95778, 50.94222]
        Assert.assertEquals(testEt.transformPoint("50.94222, 6.95778").center(), p);

        p= Point.fromLngLat( 7.640063, 51.95173);
        Assert.assertEquals(testEt.transformPoint("Hafen, Münster").center(), p);
        Assert.assertEquals(testEt.transformPoint("Hafen Münster").center(), p);
    }

    @Test
    void transformProfile() {
    }
}