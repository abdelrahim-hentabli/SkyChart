import javax.swing.*;
import java.awt.*;

/**
 * Created by Rahim on 6/7/2017.
 */
public class CircleFrame extends JComponent {
    int size;
    public CircleFrame(int circleSize){
        size = circleSize;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = size; i < (size * 3 / 2); i++){
            g.drawOval(0 - (i - size) / 2,0- (i - size) / 2,i,i);
        }
    }
}
