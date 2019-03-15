import javax.swing.*;
import java.io.IOException;

public class Map {


    private JPanel panel1;
    private JTable table1;

    /*
            // main-Methode
            public static void main(String[] args)
            {
                // Erzeugung eines neuen Dialoges
                JDialog meinJDialog = new JDialog();
                meinJDialog.setTitle("JSplitPane Beispiel");
                meinJDialog.setSize(600,400);


                // create a GUI component that loads the image: image.jpg
                //
                ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg"))
                        .getImage().getScaledInstance(630, 600,
                                java.awt.Image.SCALE_SMOOTH));
                test.add(new JLabel(imageIcon));


                // Erzeugung zweier JPanel-Objekte
                JPanel panelRot = new JPanel();
                JPanel panelGelb = new JPanel(imageIcon);
                // Hintergrundfarben der JPanels werden gesetzt
                panelRot.setBackground(Color.red);
                panelGelb.setBackground(Color.yellow);
                //Beschriftungen für die beiden Seiten werden erstellt
                JLabel labelRot = new JLabel("Ich bin auf der roten Seite");
                JLabel labelGelb = new JLabel("Ich bin auf der gelben Seite");
                //Labels werden unseren Panels hinzugefügt
                panelRot.add(labelRot);
                panelGelb.add(labelGelb);

                // Erzeugung eines JSplitPane-Objektes mit horizontaler Trennung
                JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                // Hier setzen wir links unser rotes JPanel und rechts das gelbe
                splitpane.setLeftComponent(panelRot);
                splitpane.setRightComponent(panelGelb);

                // Hier fügen wir unserem Dialog unser JSplitPane hinzu
                meinJDialog.add(splitpane);
                // Wir lassen unseren Dialog anzeigen
                meinJDialog.setVisible(true);

            }

    */
public static void main(String[] args) throws IOException {
    JFrame map = new JFrame("Route");

    Prototyp.main(args);

    // create a GUI component that loads the image: image.jpg
    //
    ImageIcon imageIcon = new ImageIcon((new ImageIcon("map.jpg"))
            .getImage().getScaledInstance(600, 600,
                    java.awt.Image.SCALE_SMOOTH));
        map.add(new JLabel(imageIcon));

    // show the GUI window
        map.setVisible(true);
        map.pack();
}

}
