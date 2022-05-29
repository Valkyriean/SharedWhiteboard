// written by Jiachen Li, 1068299

import java.awt.Color;
import java.awt.Graphics2D;

public class Circle implements Drawable{

	private final int x, y, r;
	private final Color c;

	public Circle(int x1, int y1, int x2, int y2, Color c) {
		this.r = (int) Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
		this.x = x1-r;
		this.y = y1-r;
		this.c = c;
	}

	public Circle(String[] s) {
		this.x = Integer.parseInt(s[1]);
		this.y = Integer.parseInt(s[2]);
		this.r = Integer.parseInt(s[3]);
		this.c = new Color(Integer.parseInt(s[4]));
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawOval(this.x, this.y, this.r*2, this.r*2);
	}
	
	public String toString() {
		return String.format("Circle,%d,%d,%d,%d", x,y,r,c.getRGB());
	}
}
