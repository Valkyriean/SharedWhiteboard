import java.awt.Color;
import java.awt.Graphics2D;

public class Triangle implements Drawable{

	private final int[] x,y;
	private final Color c;
	public Triangle(int x1, int y1, int x2, int y2,int x3, int y3, Color c){
		this.x = new int[] {x1,x2,x3};
		this.y = new int[] {y1,y2,y3};
		this.c = c;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawPolygon(x, y, 3);
	}

}
