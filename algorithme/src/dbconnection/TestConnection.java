package dbconnection;

import java.sql.Connection;

public class TestConnection {
	public static void main(String[] args) {
		 ConnectionManager cm = ConnectionManager.getInstance();
		 Connection c = cm.openConnection();
		 System.out.println(c);
	}
}
