package test2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dbconnection.ConnectionManager;

import fillDao.lessonDao.IntLessonDao;
import fillDao.lessonDao.LessonDao;
import fillDao.timetableDao.IntTimetableDao;
import fillDao.timetableDao.TimetableDao;


public class TimetableGA {
	
	private final int timetable_id;

    public TimetableGA(int timetable_id) {
		super();
		this.timetable_id = timetable_id;
	}

	public void Fill() {

    	// Get a Timetable object with all the available information.
        Timetable timetable = initializeTimetable();
       
        // Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(1000, 0.01, 0.9, 2, 5);
      
        // Initialize population
        Population population = ga.initPopulation(timetable);
     
        // Evaluate population
        ga.evalPopulation(population, timetable);
        
        // Keep track of current generation
        int generation = 1;
        
        // Start evolution loop
     
        while (ga.isTerminationConditionMet(generation, 10) == false
            && ga.isTerminationConditionMet(population) == false) {
            // Print fitness

            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);
            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

        // Print fitness       
        timetable.createClasses(population.getFittest(0));
        
        System.out.println(population.getFittest(0));
      
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print classes

        Class [] classes = timetable.getClasses();
        
        System.out.println(classes.length);
        
        List<Long> ts_list = new ArrayList<Long>();

        int classIndex = 1;
        String s = "{";
        for (Class bestClass : classes) {
        	
        	if(ts_list.contains(bestClass.getTimeslotId())== false) { 
        		
        		ts_list.add(bestClass.getTimeslotId());
     		
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + 
                    timetable.getModule(bestClass.getModuleId()).getModuleName());
            
            s += timetable.getModule((long)classIndex).getModuleCode() + ":";
            
            System.out.println("Group: " + 
                    timetable.getGroup(bestClass.getGroupId()).getGroupId());
            System.out.println("Room: " + 
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
            System.out.println("Professor: " + 
                    timetable.getProfessor(bestClass.getProfessorId()).getProfessorName());
            System.out.println("Time: " + 
                    timetable.getTimeslot(bestClass.getTimeslotId()).getTimeslot());
            System.out.println("-----");
            
            s += "[";
            for (int m = 0 ; m <5 ; m++) {
            	s += "[";
                for (int c = 0 ; c <4 ; c++) {
                	s += bestClass.getModuleId() ;
                }
                s += "],";
            }
            s += "]";
            
        	}
 
            classIndex++;
       }
        s += "}";
        
       // Print L
        System.out.println("-----------------");
		System.out.println(s);
    }

    @Override
	public String toString() {
		return "TimetableGA [timetable_id=" + timetable_id + "]";
	}

	/**
     * Creates a Timetable 
     * hadchi khas yji mn database
     */
    
    //Initialize timetable
    
    @SuppressWarnings("null")
	private Timetable initializeTimetable() {
		// Create timetable
		Timetable timetable = new Timetable(this.timetable_id);

		/*
		timetable.addRoom(1, "A1", 150);
		timetable.addRoom(2, "B1", 30);
		timetable.addRoom(4, "D1", 200);
		timetable.addRoom(5, "F1", 60);

		timetable.addTimeslot(1, "MON 9:00 - 11:00");
		timetable.addTimeslot(2, "MON 11:00 - 13:00");
		timetable.addTimeslot(3, "MON 13:00 - 15:00");
		timetable.addTimeslot(4, "TUE 9:00 - 11:00");
		timetable.addTimeslot(5, "TUE 11:00 - 13:00");
		timetable.addTimeslot(6, "TUE 13:00 - 15:00");
		timetable.addTimeslot(7, "WED 9:00 - 11:00");
		timetable.addTimeslot(8, "WED 11:00 - 13:00");
		timetable.addTimeslot(9, "WED 13:00 - 15:00");
		timetable.addTimeslot(10, "THU 9:00 - 11:00");
		timetable.addTimeslot(11, "THU 11:00 - 13:00");
		timetable.addTimeslot(12, "THU 13:00 - 15:00");
		timetable.addTimeslot(13, "FRI 9:00 - 11:00");
		timetable.addTimeslot(14, "FRI 11:00 - 13:00");
		timetable.addTimeslot(15, "FRI 13:00 - 15:00");

		timetable.addProfessor(2, "prof5");
		timetable.addProfessor(3, "prof3");
		timetable.addProfessor(4, "prof4");

		timetable.addModule(1, 11, "aaaaaaaaaaaaa", new int[] { 1, 2 });
		timetable.addModule(2, 22, "dddddddddd", new int[] { 1, 3 });
		timetable.addModule(3, 33, "eeeeeeeee", new int[] { 1, 2 });
		timetable.addModule(4, 44, "cccccccccccc", new int[] { 3, 4 });
		timetable.addModule(5, 55, "vvvvvvvvvvvvvv", new int[] { 4 });
		timetable.addModule(6, 66, "xxxxxxxxx", new int[] { 1, 4 });

		timetable.addGroup(1, 10, new int[] { 1, 3, 4 });
		timetable.addGroup(2, 30, new int[] { 2, 3, 5, 6 });
		timetable.addGroup(3, 18, new int[] { 3, 4, 5 });
		timetable.addGroup(4, 25, new int[] { 1, 4 });
		timetable.addGroup(5, 20, new int[] { 2, 3, 5 });
		timetable.addGroup(6, 22, new int[] { 1, 4, 5 });
		timetable.addGroup(7, 16, new int[] { 1, 3 });
		timetable.addGroup(8, 18, new int[] { 2, 6 });
		timetable.addGroup(9, 24, new int[] { 1, 6 });
		timetable.addGroup(10, 25, new int[] { 3, 4 });
		*/
		
		Long module_id = 1L;
		
		IntLessonDao daoL = new LessonDao();
		fillDao.beans.Lesson L = new fillDao.beans.Lesson();
		List<fillDao.beans.Lesson> lL = daoL.selectAlgoLesson(L);
		
		L.setLessonId((long) this.timetable_id);
		
		System.out.println(lL);
		//System.out.println("------- 1");

		for (fillDao.beans.Lesson lesson : lL) {
			//System.out.println("------- 2");
			
			if (lesson.getLessonId() == this.timetable_id) {

				for (int i = 0 ; i < lesson.getTotalLessons(); i++) {
					//System.out.println("-------- 3");

					timetable.addModule(module_id,lesson.getLessonId(),lesson.getLessonSubjectFk().toString(),new Long[] {lesson.getLessonTeacherFk()});
				
					timetable.addProfessor(lesson.getLessonTeacherFk(), lesson.getLessonTeacherName());
				
					timetable.addRoom(lesson.getLessonRoomFk(), lesson.getLessonRoomAbrev(), 10);
				
					Long class_id = lesson.getLessonClassFk(); 
					if (timetable.getGroup(class_id) != null) {
						timetable.appendModule(class_id, module_id);
					}
					else {
						timetable.addGroup(class_id, module_id, new Long[] {module_id});
					}
					module_id++;
				}
			}
		}	

		IntTimetableDao daoTb = new TimetableDao();
		fillDao.beans.Timetable tb = new fillDao.beans.Timetable();
		List<fillDao.beans.Timetable> ltb = daoTb.selectTimetable(tb);
		
		tb.setTimetableId((long) this.timetable_id);
		
		int [] timeslot_list = new int[20];
		for (fillDao.beans.Timetable tt_db : ltb) {
			
			// tt_db.getTimetableId() : there is no timetable in the DB that id == 1 
			
			if (tt_db.getTimetableId() == this.timetable_id) {	
				for(int i = 0 ; i < 5; i++) {
					for(int j = 0 ; j < 4; j++) {
						//timetable.addTimeslot((long)(i*4+j),i*4+j);
						timeslot_list [j] = i*4+j;
					}
				}
			}
		}
		int [] tsL = printDistinct(timeslot_list,timeslot_list.length);
		for (int ts : tsL) {
			timetable.addTimeslot((long)ts,ts);
		}
		return timetable;
	}
    
    // Populate timetable
    /*
	public Timetable populateTimetable() {
		
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection c = null;
		Timetable timetable = new Timetable(this.timetable_id);
		
		try {
			c = cm.openConnection();
			String sql = "SELECT * FROM ROOM ;";
			//int found = 0;
			Statement st = c.createStatement();
			ResultSet resultats = st.executeQuery(sql);
			
			while(resultats.next()) {
				timetable.addRoom((long) resultats.getInt("id"),resultats.getString("abrev"), 0);
			}
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("statement failed to parse");
			e.printStackTrace();
		}finally {
			cm.closeConnection(c);
		}
		
		try {
			c = cm.openConnection();
			String sql2 = "SELECT teacher_id, class_id, l.id, total_lessons FROM lesson l WHERE l.timetable_id = ' "+ this.timetable_id + "';";
			
			Statement st2 = c.createStatement();
			ResultSet resultats2 = st2.executeQuery(sql2);
			
			Long module_id = (long) 1;
			while(resultats2.next()) {
				//List<Module> module = 
			
				timetable.addProfessor(resultats2.getLong("teacher_id"), "");
				timetable.addModule(module_id, resultats2.getLong("l.id"),"" , new Long[] {resultats2.getLong("teacher_id")});
				//timetable.addGroup(resultats2.getInt("class_id"), 10, new int[] { 1, 3, 4 });
				HashMap<Long, Group> grp = timetable.getGroups();
				if (grp.containsKey(resultats2.getLong("class_id"))) {
					timetable.appendModule(resultats2.getLong("class_id"), module_id);
				}
				else {
					timetable.addGroup(resultats2.getLong("class_id"), 0L, new Long[] {module_id});
				}
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("statement failed to parse");
			e.printStackTrace();
		}finally {
			cm.closeConnection(c);
		}	
		return timetable;
	}
	*/
    
	private int [] printDistinct(int arr[], int n) 
    { 
        int nb = arr.length;
        int [] list = new int[nb];
        // First sort the array so that  
        // all occurrences become consecutive 
        Arrays.sort(arr); 
      
        // Traverse the sorted array 
        for (int i = 0; i < n; i++) 
        { 
            // Move the index ahead while  
            // there are duplicates 
            while (i < n - 1 && arr[i] == arr[i + 1]) 
                i++; 
      
            // print last occurrence of  
            // the current element 
            list[i] = arr[i] ; 
        }
		return list; 
    }

	
	public static void main(String[] args) {

		TimetableGA t = new TimetableGA(2);
		t.Fill();
	}
}
