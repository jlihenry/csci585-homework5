import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;


public class ConnectDB {
	private String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user;
	private String password;
	
	public ConnectDB(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public Connection createConnection() {
		Connection conn = null;
		Properties connectionPros = new Properties();
		connectionPros.put("user", user);
		connectionPros.put("password", password);
		try {
			conn = DriverManager.getConnection(dburl, connectionPros);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Database connected!");
		return conn;
	}
	
	public ArrayList<Lion> getLions() throws SQLException {
		Connection conn = createConnection();
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery("select shape, id from lion");
		ArrayList<Lion> list = new ArrayList<Lion>();
		
		while (res.next()) {
			STRUCT object = (STRUCT) res.getObject(1);
			JGeometry shape = JGeometry.load(object);
			String id = res.getString("id");
			double[] coordinates = shape.getFirstPoint();
			list.add(new Lion((int)coordinates[0], (int)coordinates[1], id));
			System.out.println("Lion "+id);
		}
		
		close(res, stmt, conn);
		return list;
	}
	
	public ArrayList<Pond> getPonds() throws SQLException {
		Connection conn = createConnection();
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery("select shape, id from pond");
		ArrayList<Pond> list = new ArrayList<Pond>();
		
		while (res.next()) {
			STRUCT object = (STRUCT) res.getObject(1);
			JGeometry shape = JGeometry.load(object);
			String id = res.getString("id");
			Object[] ordinates = shape.getOrdinatesOfElements();
			list.add(new Pond(
						(int) ((double[])ordinates[0])[0], 
						(int) ((double[])ordinates[0])[2],
						(int) ((double[])ordinates[0])[1], 
						(int) ((double[])ordinates[0])[3],
						id
					));
			System.out.println("Pond "+id);
		}
		
		close(res, stmt, conn);
		return list;
	}
	
	public ArrayList<Lion> getRedLions(String id, DrawingBoard board) throws SQLException {
		Connection conn = createConnection();
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery("select L.id from lion L, Region R where R.id='"+id+"' and sdo_relate"
				+ "(L.shape, R.shape, 'mask=INSIDE')='TRUE'");
		ArrayList<Lion> list = board.getLionList();
		HashSet<String> idSet = new HashSet<String>();
		
		while (res.next()) {
			String lionId = res.getString("id");
			idSet.add(lionId);
		}
		
		Iterator<Lion> it = list.iterator();
		while (it.hasNext()) {
			Lion lion = it.next();
			lion.setColor(Color.green);
			if (idSet.contains(lion.getId())) {
				lion.setColor(Color.red);
			}
		}
		close(res, stmt, conn);
		return list;
	}
	
	public ArrayList<Pond> getRedPonds(String id, DrawingBoard board) throws SQLException {
		Connection conn = createConnection();
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery("select P.id from pond P, region R where R.id='"+id+"' and sdo_relate"
				+ "(P.shape, R.shape, 'mask=INSIDE')='TRUE'");
		ArrayList<Pond> list = board.getPondList();
		HashSet<String> idSet = new HashSet<String>();
		
		while (res.next()) {
			String pondId = res.getString("id");
			System.out.println(pondId);
			idSet.add(pondId);
		}
		
		Iterator<Pond> it = list.iterator();
		while (it.hasNext()) {
			Pond pond = it.next();
			pond.setColor(Color.blue);
			if (idSet.contains(pond.getId())) {
				pond.setColor(Color.red);
			}
		}
		
		close(res, stmt, conn);
		return list;
	}
	
	public ArrayList<Region> getRegions() throws SQLException {
		Connection conn = createConnection();
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery("select shape, id from region");
		ArrayList<Region> list = new ArrayList<Region>();
		
		while (res.next()) {
			STRUCT object = (STRUCT) res.getObject(1);
			JGeometry shape = JGeometry.load(object);
			String id = res.getString("id");
			Object[] ordinates = shape.getOrdinatesOfElements();
			list.add(new Region(
						(int) ((double[])ordinates[0])[0], 
						(int) ((double[])ordinates[0])[2],
						(int) ((double[])ordinates[0])[4], 
						(int) ((double[])ordinates[0])[6],
						(int) ((double[])ordinates[0])[1], 
						(int) ((double[])ordinates[0])[3],
						(int) ((double[])ordinates[0])[5], 
						(int) ((double[])ordinates[0])[7],
						id
					));
			System.out.println("Region "+id);
		}
		
		close(res, stmt, conn);
		return list;
	}
	
	public void close(ResultSet res, Statement stmt, Connection conn) throws SQLException {
		res.close();
		stmt.close();
		conn.close();
	}

}
