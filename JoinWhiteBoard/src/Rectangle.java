// written by Jiachen Li, 1068299

import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle implements Drawable{
	private final int x, y, w, h;
	private final Color c;
	public Rectangle(int x, int y, int x2, int y2, Color c){
		this.x = Math.min(x, x2);
		this.y = Math.min(y, y2);
		this.w = Math.abs(x2-x);
		this.h = Math.abs(y2-y);
		this.c = c;
	}
	
	public Rectangle(String[] s) {
		this.x = Integer.parseInt(s[1]);
		this.y = Integer.parseInt(s[2]);
		this.w = Integer.parseInt(s[3]);
		this.h = Integer.parseInt(s[4]);
		this.c = new Color(Integer.parseInt(s[5]));
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawRect(this.x, this.y, this.w, this.h);
	}
	
	public String toString() {
		return String.format("Rectangle,%d,%d,%d,%d,%d", x,y,w,h,c.getRGB());
	}
}
