import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Test extends JFrame {

    public Test() {
        setTitle("Drawing a Circle");
        setSize(250, 250);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Shape circleShape = new Ellipse2D.Double(100, 100, 100, 100);
        g2d.draw(circleShape);
    }

    public static void main(String[] args) {

        new Test();

    }
}