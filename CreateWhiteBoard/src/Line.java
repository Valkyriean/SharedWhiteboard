import java.awt.Color;
import java.awt.Graphics2D;

public class Line implements Drawable{
	private final int x1, y1, x2, y2;
	private final Color c;

	public Line(int x1, int y1, int x2, int y2, Color c) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.c = c;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.drawLine(this.x1, this.y1, this.x2, this.y2);
	}
}
