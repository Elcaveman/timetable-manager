package appJobs.servlets.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import databaseService.beans.Lesson;
import databaseService.beans.Teacher;
import databaseService.beans.Timetable;
import databaseService.dao.lessonDao.IntLessonDao;
import databaseService.dao.lessonDao.LessonDao;
import databaseService.dao.professeurDao.IntTeacherDao;
import databaseService.dao.professeurDao.TeacherDao;
import databaseService.dao.timetableDao.IntTimetableDao;
import databaseService.dao.timetableDao.TimetableDao;


/**
 * Servlet implementation class ApiTimetable
 */

public class ApiTimetable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	

    public ApiTimetable() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private IntLessonDao lessonDao;
    private IntTimetableDao timetableDao;
	
    public void init() throws ServletException {
    	lessonDao = new LessonDao();
    	timetableDao = new TimetableDao();
    }

     private Gson gson = new Gson();
     
    //"/api/timetable/?c_id=1" or "/api/timetable/?tt_id=1" directly
     
     //or "/api/timetable/(depends on prof or admin)" 
     
  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
       
		/**
		 * this function returns a list of timetables ID 
		 */
       //List<Long> timetablesID = new ArrayList<Long>();
		
		List<Timetable> listOfTimetables = new ArrayList<Timetable>();
  
       if(request.getParameter("c_id") != null)  //    "/api/timetable/?c_id=1"
        {
    	   /**
   		 * return all unique timetables from lesson where class_fk = class
   		 */
        	Lesson L = new Lesson();
        	Long cl = Long.parseLong(request.getParameter("c_id"));
        	L.setLessonClassFk(cl);
        	listOfTimetables = lessonDao.selectUniqueLessonTimetable(L);
	
        }
       else if(request.getParameter("tt_id") != null)
       {
    	   /**
      		 * return all  timetables from timetable where id = tt_id
      		 */
    	    Timetable t = new Timetable();
			Long id =  Long.parseLong(request.getParameter("tt_id"));
			t.setTimetableId(id);
			listOfTimetables = timetableDao.selectTimetable(t);
    	   
       }

    
        else 
        {
        	HttpSession session = request.getSession();
        	if(session.getAttribute("id")!= null)
        	{
        		if(session.getAttribute("type").equals("prof"))
        		{
        			/**
               		 * return all unique timetables from lesson where prof_fk = prof in session
               		 */
        			Lesson L = new Lesson();
        			
        			Long user_fk = (Long) session.getAttribute("id");
					Teacher teacher = new Teacher();
					teacher.setTeacherUserFk(user_fk);
					IntTeacherDao teacherDao = new TeacherDao();
					List<Teacher> list = teacherDao.selectTeacher(teacher);
					
					Teacher t1 = list.get(0);
					long prof = (long) t1.getTeacherId();
    	        	
    	        	L.setLessonTeacherFk(prof);
    	        	listOfTimetables = lessonDao.selectUniqueLessonTimetable(L);
    	        	System.out.println(listOfTimetables);
    	        	
        		}
        		else if (session.getAttribute("type").equals("admin"))
        		{
        			/**
               		 * return all unique timetables from timetable where user_fk = user in session
               		 */
                	
        			Timetable t = new Timetable();
    				Long user =  (Long) session.getAttribute("id");
    				t.setTimetableUserFk(user);
    				
    				listOfTimetables = timetableDao.selectUniqueTimetable(t);
    				
        		}
        			
        	}
        }
        String listOfTimetablesJsonString = this.gson.toJson(listOfTimetables);
    	PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(listOfTimetablesJsonString);
        out.flush();
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
