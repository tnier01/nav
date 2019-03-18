import javax.swing.*;
import java.io.IOException;

public class Map {



public void showMap() throws IOException {
    JFrame map = new JFrame("Route");


    // create a GUI component that loads the image: map.png

    ImageIcon imageIcon = new ImageIcon((new ImageIcon("map.png"))
            .getImage().getScaledInstance(600, 600,
                    java.awt.Image.SCALE_SMOOTH));
        map.add(new JLabel(imageIcon));

    // show the GUI window
        map.setVisible(true);
        map.pack();
}

}
