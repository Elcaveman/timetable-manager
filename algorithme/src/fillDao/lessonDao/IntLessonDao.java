package fillDao.lessonDao;

import java.util.HashMap;
import java.util.List;

import fillDao.beans.Lesson;

public interface IntLessonDao {
	public boolean insertLesson(Lesson ls);
	public boolean deleteLesson(Lesson ls);
	public boolean updateLesson(Lesson ls);
	public List<Lesson> selectLesson(Lesson ls);
	public List<Lesson> selectUniqueLessonTimetable(Lesson ls);
	public List<Lesson> selectDetailsLesson(Lesson ls);
	public List<Lesson> selectAlgoLesson(Lesson ls);
}
