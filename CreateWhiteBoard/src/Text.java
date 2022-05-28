import java.awt.Color;
import java.awt.Graphics2D;

public class Text implements Drawable{
	private final String text;
	private final int x,y;
	private final Color c;

	
	public Text(int x, int y, String text, Color c) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.c = c;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawString(text, x, y);;
	}

}
