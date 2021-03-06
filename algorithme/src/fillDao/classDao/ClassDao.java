package fillDao.classDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconnection.ConnectionManager;
import fillDao.beans.Class;

public class ClassDao implements IntClassDao{

	@Override
	public boolean insertClass(Class cl) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection c = null;
		try {
			c = cm.openConnection();
			String sql = "INSERT INTO CLASS (faculty_id,group_num,free_time,color) VALUES ('"+cl.getClassFacultyFk()+"','"+cl.getClassGroup()+"','"+cl.getClassFreeTime()+"','"+cl.getClassColor()+"');";
			Statement st = c.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("statement failed to parse");
			e.printStackTrace();
		}finally {
			cm.closeConnection(c);
		}
		return false;
	}

	@Override
	public boolean deleteClass(Class cl) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection c = null;
		try {
			c = cm.openConnection();
			String sql = "DELETE FROM CLASS WHERE ID = '"+cl.getClassId()+"' ";
			Statement st = c.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("statement failed to parse");
			e.printStackTrace();
		}finally {
			cm.closeConnection(c);
		}
		return false;
	}

	@Override
	public boolean updateClass(Class cl) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection c = null;
		try {
			c = cm.openConnection();
			String sql = "UPDATE CLASS SET FACULTY_FK = '"+cl.getClassFacultyFk()+"' , GROUP = '"+cl.getClassGroup()+"' , FREE_TIME = '"+cl.getClassFreeTime()+"' , COLOR = '"+cl.getClassColor()+"' WHERE ID = '"+cl.getClassId()+"' ";
			Statement st = c.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("statement failed to parse");
			e.printStackTrace();
		}finally {
			cm.closeConnection(c);
		}
		return false;
	}

	@Override
	public List<Class> selectClass(Class cl) {
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection c = null;
		List<Class> list = new ArrayList<Class>();
		try {
			c = cm.openConnection();
			String sql = "SELECT * FROM CLASS WHERE 1=1 ";
			if(cl.getClassId() != null) {
				sql+= " AND ID = " + cl.getClassId();
			}
			if(cl.getClassFacultyFk() != null) {
				sql+= " AND FACULTY_FK = '" + cl.getClassFacultyFk()+ "'";
			}
			if(cl.getClassGroup() != 0) {
				sql+= " AND GROUP = '" + cl.getClassGroup()+ "'";
			}
			if(cl.getClassFreeTime() != null) {
				sql+= " AND FREE_TIME = '" + cl.getClassFreeTime()+ "'";
			}
			if(cl.getClassColor() != null) {
				sql+= " AND COLOR = '" + cl.getClassColor()+ "'";
			}
			
			Statement st = c.createStatement();
			ResultSet resultats = st.executeQuery(sql);
			while(resultats.next()) {
				Class cll = new Class();
				cll.setClassId(resultats.getLong("Id"));
				cll.setClassFacultyFk(resultats.getLong("faculty_fk"));
				cll.setClassGroup(resultats.getInt("group"));
				cll.setClassFreeTime(resultats.getString("free_time"));
				cll.setClassColor(resultats.getString("color"));
				
				list.add(cll);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("statement failed to parse");
			e.printStackTrace();
		}finally {
			cm.closeConnection(c);
		}
		return list;
		
	}

}
