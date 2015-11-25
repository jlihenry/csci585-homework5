import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class Region {
	public int[] xPoints = new int[4];
	public int[] yPoints = new int[4];
	public String id;
	
	public Region(int x1, int x2, int x3, int x4,
			int y1, int y2, int y3, int y4, String id) {
		xPoints[0] = x1;
		xPoints[1] = x2;
		xPoints[2] = x3;
		xPoints[3] = x4;
		yPoints[0] = 500 - y1;
		yPoints[1] = 500 - y2;
		yPoints[2] = 500 - y3;
		yPoints[3] = 500 - y4;
		this.id = id;
	}
	
	public void draw(Graphics g) {
		Color originalColor = g.getColor();
		g.setColor(Color.black);
		g.drawPolyline(xPoints, yPoints, 4);
		
		int x = (xPoints[0]+xPoints[1]+xPoints[2]+xPoints[3])/4;
		int y = (yPoints[0]+yPoints[1]+yPoints[2]+yPoints[3])/4;
		g.setColor(Color.GRAY);
		g.drawString(id, x, y);
		g.setColor(originalColor);
	}
	
	public boolean contains(int x, int y) {
		Polygon p = new Polygon(xPoints, yPoints, 4);
		return p.contains(x, y);
	}
	
	public String getId() {return id;}
}
