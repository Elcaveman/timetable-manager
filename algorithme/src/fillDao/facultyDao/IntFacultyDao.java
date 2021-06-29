package fillDao.facultyDao;

import java.util.List;

import fillDao.beans.Faculty;

public interface IntFacultyDao {
	public boolean insertFaculty(Faculty f);
	public boolean deleteFaculty(Faculty f);
	public boolean updateFaculty(Faculty f);
	public List<Faculty> selectFaculty(Faculty f);
}
