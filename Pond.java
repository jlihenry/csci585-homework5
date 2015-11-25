import java.awt.Color;
import java.awt.Graphics;


public class Pond {
	private int x, y, r;
	private String id;
	private Color color;
	
	public Pond(int x1, int x2, int y1, int y2, String id) {
		x = (x1 + x2) / 2;
		y = 500 - y1;
		r = Math.abs(x1 - x2) / 2;
		this.id = id;
		color = Color.blue;
	}
	
	public void setColor(Color color) {this.color = color;}
	
	public void draw(Graphics g) {
		Color originalColor = g.getColor();
		g.setColor(Color.black);
		g.drawArc(x, y, r, r, 0, 360);
		g.setColor(color);
		g.fillOval(x, y, r, r);
		g.setColor(Color.black);
		g.drawString(id, x, y);
		g.setColor(originalColor);
	}
	
	public String getId() {return id;}
}
