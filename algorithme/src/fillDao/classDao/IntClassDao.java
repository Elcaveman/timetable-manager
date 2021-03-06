package fillDao.classDao;

import java.util.List;
import fillDao.beans.Class;

public interface IntClassDao {
	public boolean insertClass(Class cl);
	public boolean deleteClass(Class cl);
	public boolean updateClass(Class cl);
	public List<Class> selectClass(Class cl);
}
