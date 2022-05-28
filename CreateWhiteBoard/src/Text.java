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
	}
	
	public Text(String[] s) {
		this.x = Integer.parseInt(s[1]);
		this.y = Integer.parseInt(s[2]);
		this.c = new Color(Integer.parseInt(s[3]));
		this.text = s[4];
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawString(text, x, y);;
	}
	
	public String toString() {
		// split(",",5)
		return String.format("Text,%d,%d,%d,%s", x,y,c.getRGB(),text);
	}

}
