package fillDao.beans;

public class Details implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long teacher_fk;
	private String teacher_name;
	
	private Long class_fk;
	private int class_grp;
	
	private String faculty_year;
	private String faculty_abrev;
	
	private Long room_fk ;
	private String room_abrev ;
	
	private Long  subject_fk;
	private String  subject_abrev;
	
	private Long timetable_fk;
	
	private int total_lessons;
	private String lesson_occ;
	private String lesson_link;
	private String color;
	
	
	public Details() {
		super();
	}

	public Details(Long teacher_fk, String teacher_name, Long class_fk, int class_grp, String faculty_year,
			String faculty_abrev, Long room_fk, String room_abrev, Long subject_fk, String subject_abrev, Long timetable_fk,
			int total_lessons, String lesson_occ, String lesson_link, String color) {
		super();
		this.teacher_fk = teacher_fk;
		this.teacher_name = teacher_name;
		this.class_fk = class_fk;
		this.class_grp = class_grp;
		this.faculty_year = faculty_year;
		this.faculty_abrev = faculty_abrev;
		this.room_fk = room_fk;
		this.room_abrev = room_abrev;
		this.subject_fk = subject_fk;
		this.subject_abrev = subject_abrev;
		this.timetable_fk = timetable_fk;
		this.total_lessons = total_lessons;
		this.lesson_occ = lesson_occ;
		this.lesson_link = lesson_link;
		this.color = color;
	}
	
	
	public Long getDetailsId() {
		return id;
	}
	public void setDetailsId(Long id) {
		this.id = id;
	}
	public Long getDetailsTeacherFk() {
		return teacher_fk;
	}
	public void setDetailsTeacherFk(Long teacher_fk) {
		this.teacher_fk = teacher_fk;
	}
	public String getDetailsTeacherName() {
		return teacher_name;
	}
	public void setDetailsTeacherName(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public Long getDetailsClassFk() {
		return class_fk;
	}
	public void setDetailsClassFk(Long class_fk) {
		this.class_fk = class_fk;
	}
	public int getDetailsClassGrp() {
		return class_grp;
	}
	public void setDetailsClassGrp(int class_grp) {
		this.class_grp = class_grp;
	}
	public String getDetailsFacultyYear() {
		return faculty_year;
	}
	public void setDetailsFacultyYear(String faculty_year) {
		this.faculty_year = faculty_year;
	}
	public String getDetailsFacultyAbrev() {
		return faculty_abrev;
	}
	public void setDetailsFacultyAbrev(String faculty_abrev) {
		this.faculty_abrev = faculty_abrev;
	}
	public Long getDetailsRoomFk() {
		return room_fk;
	}
	public void setDetailsRoomFk(Long room_fk) {
		this.room_fk = room_fk;
	}
	public String getDetailsRoomAbrev() {
		return room_abrev;
	}
	public void setDetailsRoomAbrev(String room_abrev) {
		this.room_abrev = room_abrev;
	}
	public Long getDetailsSubjectFk() {
		return subject_fk;
	}
	public void setDetailsSubjectFk(Long subject_fk) {
		this.subject_fk = subject_fk;
	}
	public String getDetailsSubjectAbrev() {
		return subject_abrev;
	}
	public void setDetailsSubjectAbrev(String subject_abrev) {
		this.subject_abrev = subject_abrev;
	}
	public Long getDetailsTimetableFk() {
		return timetable_fk;
	}
	public void setDetailsTimetableFk(Long timetable_fk) {
		this.timetable_fk = timetable_fk;
	}
	public int getDetailsTotalLessons() {
		return total_lessons;
	}
	public void setDetailsTotalLessons(int total_lessons) {
		this.total_lessons = total_lessons;
	}
	public String getDetailsLessonOcc() {
		return lesson_occ;
	}
	public void setDetailsLessonOcc(String lesson_occ) {
		this.lesson_occ = lesson_occ;
	}
	public String getDetailsLessonLink() {
		return lesson_link;
	}
	public void setDetailsLessonLink(String lesson_link) {
		this.lesson_link = lesson_link;
	}
	public String getDetailsColor() {
		return color;
	}
	public void setDetailsColor(String color) {
		this.color = color;
	}
	
	
}
