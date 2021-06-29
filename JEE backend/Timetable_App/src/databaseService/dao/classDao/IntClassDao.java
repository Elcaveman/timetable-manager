package databaseService.dao.classDao;

import java.util.List;
import databaseService.beans.Class;
import databaseService.beans.Faculty;

public interface IntClassDao {
	public boolean insertClass(Class cl);
	public boolean deleteClass(Class cl);
	public boolean updateClass(Class cl);
	public List<Class> selectClass(Class cl);
	public Faculty selectClassFacultyName(Class cl);
}
