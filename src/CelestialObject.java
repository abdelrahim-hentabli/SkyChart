import javax.swing.*;
import java.awt.*;

/**
 * Created by Rahim on 5/29/2017.
 */
public abstract class CelestialObject extends JComponent{
    protected double altitude;
    protected double azimuth;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    abstract void clicked();
    abstract void draw(Graphics g);
    abstract void update(Coordinates c, Time t);
    abstract String print();

}
