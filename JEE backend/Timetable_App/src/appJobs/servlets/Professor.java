package appJobs.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseService.beans.Teacher;
import databaseService.beans.Timetable;
import databaseService.beans.User;
import databaseService.dao.professeurDao.IntTeacherDao;
import databaseService.dao.professeurDao.TeacherDao;
import databaseService.dao.timetableDao.IntTimetableDao;
import databaseService.dao.timetableDao.TimetableDao;

/**
 * Servlet implementation class Professor
 */

public class Professor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Professor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	    private IntTimetableDao timetableDao;
	
	    public void init() throws ServletException {
	    	timetableDao = new TimetableDao();
	    }
    //teachers?tt_id=1&t_id=1 and //teachers?tt_id=1
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long t_id;
		Long tt_id;
		HttpSession session = request.getSession();
		if(session.getAttribute("id") == null)
		{
			 session.setAttribute("denied", "Access Denied! Please Login");
			 response.sendRedirect("/Timetable_App/login"); 
		}
		else 
		{
			if(request.getParameter("tt_id") != null)
			{
				
				tt_id = Long.parseLong(request.getParameter("tt_id"));
				
				if(request.getParameter("t_id") != null)
				{
					 t_id = Long.parseLong(request.getParameter("t_id"));
					 request.setAttribute("timetable", tt_id);
					 request.setAttribute("teacher", t_id);
					 this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/teacher_timetable.jsp").forward(request, response);

				}
				else
				{
					
					if(session.getAttribute("type").equals("prof"))
					{
						
						 request.setAttribute("timetable", tt_id);
						 Long user_fk = (Long) session.getAttribute("id");
						 Teacher teacher = new Teacher();
						 teacher.setTeacherUserFk(user_fk);
						 IntTeacherDao teacherDao = new TeacherDao();
						 List<Teacher> list = teacherDao.selectTeacher(teacher);
						 Teacher t1 = list.get(0);
						 t_id = t1.getTeacherId();
						 
						 request.setAttribute("timetable", tt_id);
						 request.setAttribute("teacher", t_id);
						 this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/teacher_timetable.jsp").forward(request, response);


					}
					else {
						 session.setAttribute("failed", "No teacher selected");
						 response.sendRedirect("/Timetable_App/");
					}
					
				}
				
			}
			else {
				 session.setAttribute("failed", "No Timetable selected");
				 response.sendRedirect("/Timetable_App/");
			}
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
