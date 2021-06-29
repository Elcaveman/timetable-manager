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
 * Servlet implementation class ApiLesson
 */

public class ApiLesson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiLesson() {
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
     
 // "/api/lesson/?tt_id=2&c_id=1" or      (admin et prof) "/api/lesson/?tt_id=2" get user  from session
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * this function returns a list of lessons 
		 */
       //List<Long> lessonsID = new ArrayList<Long>();
		   List<Lesson> listOflessons = new ArrayList<Lesson>() ;
		   
       if(request.getParameter("tt_id") != null)
       {
    	   
    	   Long tt = Long.parseLong(request.getParameter("tt_id"));
    	   Lesson L = new Lesson();
    	   
		   L.setLessonTimetableFk(tt);
    	   if(request.getParameter("c_id") != null)  //    ""/api/lesson/?tt_id=2&c_id=1"
           {
           	
	           	Long cl = Long.parseLong(request.getParameter("c_id"));
	           	
	           	
	           	L.setLessonClassFk(cl);
	           	//System.out.println(lessonDao.selectLesson(L));
	           	listOflessons = lessonDao.selectDetailsLesson(L);
	           	
	           	/*for(Lesson lesson : listOflessons)
	           	{
	           		lessonsID.add(lesson.getLessonId());
	           	}*/
           	
           }
    	   else                               //      "/api/lesson/?tt_id=2"
           {
    		   
    		   HttpSession session = request.getSession();
    		   if(session.getAttribute("id") != null )
    		   {
    			   if(session.getAttribute("type").equals("admin") )
    			   {
    				   Timetable timetable = new Timetable();
    	   				timetable.setTimetableId(tt);
    	   				List<Timetable> listOftimetables;
    	   				listOftimetables = timetableDao.selectTimetable(timetable);
    	   				
    	   				Timetable t = listOftimetables.get(0);
    	   				
    	   				
    	   				if(t.getTimetableUserFk() ==(long)session.getAttribute("id") )   //checks if timetable with id = tt_id had user_id = session user
    	   				{
    	   		           	listOflessons = lessonDao.selectDetailsLesson(L);
    	   				}
    	   				
    			   }
    			   else if(session.getAttribute("type").equals("prof"))
    			   {
    				   
    				   Long user_fk = (Long) session.getAttribute("id");
   					Teacher teacher = new Teacher();
   					teacher.setTeacherUserFk(user_fk);
   					IntTeacherDao teacherDao = new TeacherDao();
   					List<Teacher> list = teacherDao.selectTeacher(teacher);
   					
   					Teacher t1 = list.get(0);
   					System.out.println(t1);
   					long prof = (long) t1.getTeacherId();
    		           	L.setLessonTeacherFk(prof);
    		           	
    		           	listOflessons = lessonDao.selectDetailsLesson(L);
    		           	
    		           	/*for(Lesson lesson : listOflessons)
    		           	{
    		           		lessonsID.add(lesson.getLessonId());
    		           	}*/
    			   }
    		   }
    		   
        	   
           }

           //System.out.println(listOflessons);
           String listOflessonsJsonString = this.gson.toJson(listOflessons);
           PrintWriter out = response.getWriter();
           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
           out.print(listOflessonsJsonString);
           out.flush();
   	
       }
     
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}