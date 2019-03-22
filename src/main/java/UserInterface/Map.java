package UserInterface;

import javax.swing.*;

class Map {

    /**
     * Shows a the map of the route in a JFrame
     */

void showMap(){
    JFrame map = new JFrame("Route");


    // create a GUI component that loads the image: map.png

    ImageIcon imageIcon = new ImageIcon((new ImageIcon("map.jpg"))
            .getImage().getScaledInstance(600, 600,
                    java.awt.Image.SCALE_SMOOTH));
        map.add(new JLabel(imageIcon));

    // show the GUI window
        map.setVisible(true);
        map.setAlwaysOnTop(true);
        map.toFront();
        map.pack();
}

}
