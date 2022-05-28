import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

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
        
        ArrayList<Drawable> drawList = new ArrayList<Drawable>();
        
        Line line = new Line(10,10,100,100, Color.yellow);
        drawList.add(line);
        Rectangle rec = new Rectangle(10,10,100,100, Color.gray);
        drawList.add(rec);
        Circle cir = new Circle(100,100,100,110, Color.cyan);
        drawList.add(cir);
        Triangle tri = new Triangle(10,10,100,10,60,50, Color.red);
        drawList.add(tri);
        Text txt = new Text(100,100,"Hello world", Color.red);
        drawList.add(txt);
        for(Drawable d: drawList) {
        	d.draw(g2d);
        }
//        g2d.drawPolygon(new int[] {10, 100, 60}, new int[] {10, 10, 50}, 3);
//        g2d.drawOval(95, 95, 10, 10);
//        g2d.draw(circleShape);
        
//        Shape circleShape = new Ellipse2D.Double(100, 100, 100, 100);
//        g2d.draw(circleShape);
        
    }

    public static void main(String[] args) {

        new Test();

    }
}