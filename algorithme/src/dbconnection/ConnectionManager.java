package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// This class serve for opening and closing a connection
// getInstance() used to not create every time an ConnectionManager object
public class ConnectionManager {
	private static ConnectionManager instance;
	private ConnectionManager(){
	}
	public static ConnectionManager getInstance(){
		if (instance == null) instance = new ConnectionManager();
		return instance;
	}
	public Connection openConnection() {
		Connection c = null ;
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeedb","root","");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class Driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	public Connection closeConnection(Connection c) {
		if(c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Connection can't be closed");
				e.printStackTrace();
			}
		}
		return null;
	}
}
