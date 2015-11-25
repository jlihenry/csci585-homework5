import java.awt.Color;
import java.awt.Graphics;


public class Lion {
	private int x, y;
	private String id;
	private Color color;
	
	public Lion(int x, int y, String id) {
		this.x = x;
		this.y = 500 - y;
		this.id = id;
		color = Color.green;
	}
	
	public void setColor(Color color) {this.color = color;}
	
	public void draw(Graphics g) {
		Color originalColor = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, 10, 10);
		g.setColor(Color.black);
		g.drawString(id, x, y);
		g.setColor(originalColor);
	}
	
	public String getId() {return id;}
}
