// written by Jiachen Li, 1068299

import java.awt.Color;
import java.awt.Graphics2D;

public class Triangle implements Drawable{

	private final int[] x,y;
	private final Color c;
	public Triangle(int x1, int y1, int x2, int y2,int x3, int y3, Color c){
		this.x = new int[] {x1,x2,x3};
		this.y = new int[] {y1,y2,y3};
		this.c = c;
	}
	
	public Triangle(String[] s){
		this.x = new int[] {
				Integer.parseInt(s[1]),
				Integer.parseInt(s[2]),
				Integer.parseInt(s[3])};
		this.y = new int[] {
				Integer.parseInt(s[4]),
				Integer.parseInt(s[5]),
				Integer.parseInt(s[6])};
		this.c = new Color(Integer.parseInt(s[7]));
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawPolygon(x, y, 3);
	}

	public String toString() {
		return String.format("Triangle,%d,%d,%d,%d,%d,%d,%d", x[0],x[1],x[2],y[0],y[1],y[2],c.getRGB());
	}
}
