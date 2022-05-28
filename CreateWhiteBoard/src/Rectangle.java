import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle implements Drawable{
	private final int x, y, w, h;
	private final Color c;
	public Rectangle(int x, int y, int x2, int y2, Color c){
		this.x = x;
		this.y = y;
		this.w = x2-x;
		this.h = y2-y;
		this.c = c;
		// TODO Auto-generated constructor stub
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
