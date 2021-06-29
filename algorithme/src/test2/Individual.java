package test2;


public class Individual {
	
	private Long[] chromosome;
	private double fitness = -1;
	
	public Individual(Timetable timetable) {
		int numClasses = timetable.getNumClasses();

		// 1 gene for room, 1 for time, 1 for professor
		int chromosomeLength = numClasses * 3;
		// Create random individual
		Long newChromosome[] = new Long[chromosomeLength];
		int chromosomeIndex = 0;
		// Loop through groups
		for (Group group : timetable.getGroupsAsArray()) {
			// Loop through modules
			for (Long moduleId : group.getModuleIds()) {
				// Add random time
				Long timeslotId = timetable.getRandomTimeslot().getTimeslotId();
				newChromosome[chromosomeIndex] = (long) timeslotId;
				//System.out.println(timeslotId +" >>>>>>>>>>>> ");
				chromosomeIndex++;

				// Add random room
				Long roomId = timetable.getRandomRoom().getRoomId();
				newChromosome[chromosomeIndex] = roomId;
				chromosomeIndex++;

				// Add random professor
				Module module = timetable.getModule(moduleId);
				newChromosome[chromosomeIndex] = (long) module.getRandomProfessorId();
				chromosomeIndex++;
			}
		}
		this.chromosome = newChromosome;
	}


	public Individual(int chromosomeLength) {
		// Create random individual
		Long[] individual;
		individual = new Long[chromosomeLength];
		
	
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individual[gene] = (long) gene;
		}
		
		this.chromosome = individual;
	}
    
	
	public Individual(Long[] chromosome) {
		// Create individual chromosome
		this.chromosome = chromosome;
	}

	
	public Long[] getChromosome() {
		return this.chromosome;
	}

	
	public int getChromosomeLength() {
		return this.chromosome.length;
	}


	public void setGene(int offset, Long long1) {
		this.chromosome[offset] = (long) long1;
	}

	
	public Long getGene(int offset) {
		return this.chromosome[offset];
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	
	public double getFitness() {
		return this.fitness;
	}
	
	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene] + ",";
		}
		return output;
	}

	
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}


	
}
