import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DrawingBoard extends JPanel {

	private ArrayList<Lion> lionList;
	private ArrayList<Pond> pondList;
	private ArrayList<Region> regionList;
	
	public DrawingBoard() {
		lionList = new ArrayList<Lion>();
		pondList = new ArrayList<Pond>();
		regionList = new ArrayList<Region>();
		this.setPreferredSize(new Dimension(500, 500));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Start drawing");
		
		// Draw the canvas
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Draw the regions
		Iterator<Region> regionIt = regionList.iterator();
		while (regionIt.hasNext()) {
			regionIt.next().draw(g);
		}
		
		// Draw the ponds
		Iterator<Pond> pondIt = pondList.iterator();
		while (pondIt.hasNext()) {
			pondIt.next().draw(g);
		}
		
		// Draw the lions
		Iterator<Lion> lionIt = lionList.iterator();
		while (lionIt.hasNext()) {
			lionIt.next().draw(g);
		}
	}
	
	public void setData(ArrayList<Lion> lionList, 
			ArrayList<Pond> pondList,
			ArrayList<Region> regionList) {
		this.lionList = lionList;
		this.pondList = pondList;
		this.regionList = regionList;
		repaint();
	}
	
	public ArrayList<Lion> getLionList() {return lionList;}
	public ArrayList<Pond> getPondList() {return pondList;}
	
	public void reset() {
		Iterator<Lion> lionIt = lionList.iterator();
		Iterator<Pond> pondIt = pondList.iterator();
		while (lionIt.hasNext()) {
			lionIt.next().setColor(Color.green);
		}
		while (pondIt.hasNext()) {
			pondIt.next().setColor(Color.blue);
		}
		this.setData(lionList, pondList, regionList);
	}
}
