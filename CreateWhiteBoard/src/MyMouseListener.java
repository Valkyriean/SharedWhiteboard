import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter{


        @Override
        public void mouseClicked(MouseEvent e) {
             int  x1 = e.getX();
             int  y1 = e.getY();
             System.out.println(x1);
             System.out.println(y1);
//             repaint();

        }
    }