import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GeoFrame extends JFrame {
	private ArrayList<Region> regionList = null;
	
	private static final long serialVersionUID = -8795635126730746658L;
	JButton startButton;
	DrawingBoard mainPanel;
	JCheckBox checkBox;
	JScrollPane scrollPane;
	
	public GeoFrame() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Initialize all components */
		mainPanel = new DrawingBoard();
		
		startButton = new JButton("START drawing the map");
		checkBox = new JCheckBox("show ions and ponds in the selected region");
		JTextArea console = new JTextArea();
		scrollPane = new JScrollPane(console);
		scrollPane.setPreferredSize(new Dimension(50, 450));
		//scrollPane.setPreferredSize(new Dimension(80, 400));

		mainPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (regionList != null && checkBox.isSelected()) {
					int x = e.getX();
					int y = e.getY();
					Iterator<Region> it = regionList.iterator();
					while (it.hasNext()) {
						Region region = it.next();
						if (region.contains(x, y)) {
							console.append("Region "+region.getId()+" is selected!\n\n");
							ConnectDB connect = new ConnectDB("jian", "0");
							try {
								mainPanel.setData(connect.getRedLions(region.getId(), mainPanel), 
										connect.getRedPonds(region.getId(), mainPanel), 
										regionList);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});

		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				console.append("Connecting to the database...\n\n");
				ConnectDB connect = new ConnectDB("jian", "0");
				try {
					regionList = connect.getRegions();
					mainPanel.setData(connect.getLions(), connect.getPonds(), regionList);
					console.append("The map is shown on the board!\n\n");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		checkBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkBox.isSelected()) {
					console.append("The checkbox is unchecked!\n\n");
					mainPanel.reset();
				} else console.append("The checkbox is checked!\n\n");
			}
			
		});
		
		// add a panel to put the buttons
		JPanel control = new JPanel();
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		control.add(startButton);
		control.add(checkBox);
		control.add(scrollPane);
		
		/* Setup frame */
		this.setSize(600, 500);
		this.setLayout(new FlowLayout());
		this.getContentPane().add(mainPanel);
		System.out.println("after added height is "+mainPanel.getHeight());
		this.getContentPane().add(control);
		this.pack();
	}
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeoFrame geoFrame = new GeoFrame();
		geoFrame.setVisible(true);
	}
*/
}
