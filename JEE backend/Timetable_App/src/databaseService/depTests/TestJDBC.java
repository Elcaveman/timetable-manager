package databaseService.depTests;

//import java.sql.Connection;
import java.util.List;

import databaseService.beans.User;
//import user.dao.ConnectionManager;
import databaseService.dao.user.dao.IntUserDao;
import databaseService.dao.user.dao.UserDao;

public class TestJDBC {
	public static void main(String[] args) {
		IntUserDao dao = new UserDao();
		//User u = new User();
		//List<User> l = dao.selectUser(u);
		//System.out.print(l);
		
		dao.insertUser(new User("JOJO69","69696969","prof"));
		dao.insertUser(new User("JOJO420","69696969","admin"));
		
		// for select : User u = new User();
		//  			List<User> l = dao.selectUser(u);
		//				System.out.print(l);
		// for update : User u = new User(1L,"test2","test2","Professor");
		//  			dao.updateUser(u);
		// for delete : User u = new User(1L);
		//  			dao.deleteUser(u);
	}
}
