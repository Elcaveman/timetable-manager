package databaseService.dao.lessonDao;


import java.util.List;

import databaseService.beans.Lesson;
import databaseService.beans.Timetable;

public interface IntLessonDao {
	public boolean insertLesson(Lesson ls);
	public boolean deleteLesson(Lesson ls);
	public boolean updateLesson(Lesson ls);
	public List<Lesson> selectLesson(Lesson ls);
	public List<Timetable> selectUniqueLessonTimetable(Lesson ls);
	public List<Lesson> selectDetailsLesson(Lesson ls);
}
