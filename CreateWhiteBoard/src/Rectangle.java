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
	
	
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawRect(this.x, this.y, this.w, this.h);
	}

}
