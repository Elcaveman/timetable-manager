package test2;

public class TimetableGA {

    public static void main(String[] args) {
    	
        Timetable timetable = initializeTimetable();
        
        // Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 2, 5);
        
      
        Population population = ga.initPopulation(timetable);
        
        
        ga.evalPopulation(population, timetable);
        
        // Keep track of current generation
        int generation = 1;
        
       
        while (ga.isTerminationConditionMet(generation, 1000) == false
            && ga.isTerminationConditionMet(population) == false) {
            // Print fitness
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            
            population = ga.crossoverPopulation(population);

           
            population = ga.mutatePopulation(population, timetable);

            
            ga.evalPopulation(population, timetable);

            
            generation++;
        }

        // Print fitness
        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print classes
        System.out.println();
        Class classes[] = timetable.getClasses();
        int classIndex = 1;
        for (Class bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + 
                    timetable.getModule(bestClass.getModuleId()).getModuleName());
            System.out.println("Group: " + 
                    timetable.getGroup(bestClass.getGroupId()).getGroupId());
            System.out.println("Room: " + 
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
            System.out.println("Professor: " + 
                    timetable.getProfessor(bestClass.getProfessorId()).getProfessorName());
            System.out.println("Time: " + 
                    timetable.getTimeslot(bestClass.getTimeslotId()).getTimeslot());
            System.out.println("-----");
            classIndex++;
        }
    }

   
	private static Timetable initializeTimetable() {
		// Create timetable
		Timetable timetable = new Timetable();

		// Set up rooms
		
		timetable.addRoom(4, "A", 20);
		

		// Set up timeslots
		timetable.addTimeslot(1, "lundi 9:00 - 11:00");


		// Set up professors
		timetable.addProfessor(1, "prof");
		
		
		

		timetable.addModule(1, "a", "aa", new int[] { 1, 2 });
		timetable.addModule(2, "b", "bb", new int[] { 1, 3 });
		timetable.addModule(3, "c", "ccc", new int[] { 1, 2 });
		timetable.addModule(4, "d", "ddd", new int[] { 3, 4 });
		
		timetable.addModule(6, "prb", "probability", new int[] { 1, 4 });

		
		timetable.addGroup(1, 10, new int[] { 1, 3, 4 });
		
		
				return timetable;
	}
}