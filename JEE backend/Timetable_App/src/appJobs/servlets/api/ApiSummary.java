package appJobs.servlets.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import databaseService.beans.Timetable;

import databaseService.dao.timetableDao.IntTimetableDao;
import databaseService.dao.timetableDao.TimetableDao;

/**
 * Servlet implementation class ApiSummary
 */

public class ApiSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiSummary() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private IntTimetableDao timetableDao;
  
   
    public void init() throws ServletException {
    	
    	timetableDao = new TimetableDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private Gson gson = new Gson();
    
    // 'api/summary/?tt_id=1
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String messageJsonString; 
		if(request.getParameter("tt_id") != null)
		{
			Long tt_id = Long.parseLong(request.getParameter("tt_id"));
			String summary = (String) request.getHeader("summary");
			
			
			Timetable timetable = new Timetable();
			
			timetable.setTimetableId(tt_id);
			
			List<Timetable> list = timetableDao.selectTimetable(timetable);
			Timetable timetable1 = list.get(0);
			
			timetable.setTimetableDescription(timetable1.getTimetableDescription());
			timetable.setTimetableFreeTime(timetable1.getTimetableFreeTime());
			timetable.setTimetableHoursPerPeriod(timetable1.getTimetableNbPeriods());
			timetable.setTimetableNbDays(timetable1.getTimetableNbDays());
			timetable.setTimetableNbPeriods(timetable1.getTimetableNbPeriods());
			timetable.setTimetableUserFk(timetable1.getTimetableUserFk());
			timetable.setTimetableSummary(summary);  //summary from request
			
			timetableDao.updateTimetable(timetable);
			
			
			messageJsonString = this.gson.toJson("timetable updated");
	    	
		}
		else
		{
		   messageJsonString = this.gson.toJson("tt_id not found");
		}
		
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(messageJsonString);
        out.flush();

		
		
	}

}
