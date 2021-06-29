package test2;

import java.util.*;

public class Timetable {
	private final int id;
	
	private final HashMap<Long, Room> rooms;
	private final HashMap<Long, Professor> professors;
	
	@Override
	public String toString() {
		return "Timetable [id=" + id + ", rooms=" + rooms + ", professors=" + professors + ", modules=" + modules
				+ ", groups=" + groups + ", timeslots=" + timeslots + ", classes=" + Arrays.toString(classes)
				+ ", numClasses=" + numClasses + "]";
	}

	private final HashMap<Long, Module> modules;
	private final HashMap<Long, Group> groups;
	private final HashMap<Long, Timeslot> timeslots;
	private Class classes[];

	private int numClasses = 0;

	/**
	 * Initialize new Timetable
	 */
	
	public Timetable(int id) {
		this.rooms = new HashMap<Long, Room>();
		this.professors = new HashMap<Long, Professor>();
		this.modules = new HashMap<Long, Module>();
		this.groups = new HashMap<Long, Group>();
		this.timeslots = new HashMap<Long, Timeslot>();
		this.id = id;
	}

	
	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
		this.id = cloneable.id;
	}

	public HashMap<Long, Group> getGroups() {
		return this.groups;
	}

	private HashMap<Long, Timeslot> getTimeslots() {
		return this.timeslots;
	}

	private HashMap<Long, Module> getModules() {
		return this.modules;
	}

	private HashMap<Long, Professor> getProfessors() {
		return this.professors;
	}

	
	public void addRoom(Long roomId, String roomName, int capacity) {
		this.rooms.put(roomId, new Room(roomId, roomName, capacity));
	}

	
	public void addProfessor(Long long1, String professorName) {
		this.professors.put(long1, new Professor(long1, professorName));
	}

	
	public void addModule(Long moduleId, long moduleCode, String module, Long professorIds[]) {
		this.modules.put(moduleId, new Module(moduleId, moduleCode, module, professorIds));
	}

	
	public void addGroup(Long class_id, Long module_id, Long[] is) {
		this.groups.put(class_id, new Group(class_id, module_id, is));
		this.numClasses = 0;
	}

	
	public void addTimeslot(Long long1, int i) {
		this.timeslots.put(long1, new Timeslot(long1, i));
	}

	
	public void createClasses(Individual individual) {
		// Init classes
		Class [] classes = new Class[this.getNumClasses()];

		// Get individual's chromosome
		Long[] chromosome = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;

		for (Group group : this.getGroupsAsArray()) {
			Long[] moduleIds = group.getModuleIds();
			for (Long moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);
				
				classes[classIndex].addTimeslot(chromosome[chromosomePos]);
				chromosomePos++;

				
				classes[classIndex].setRoomId(chromosome[chromosomePos]);
				chromosomePos++;

				
				classes[classIndex].addProfessor(chromosome[chromosomePos]);
				chromosomePos++;

				classIndex++;
			}
		}

		this.classes = classes;
	}


	public Room getRoom(Long long1) {
		if (!this.rooms.containsKey(long1)) {
			System.out.println("Rooms doesn't contain key " + long1);
		}
		return (Room) this.rooms.get(long1);
	}

	public HashMap<Long, Room> getRooms() {
		return this.rooms;
	}

	
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	
	public Professor getProfessor(Long long1) {
		return (Professor) this.professors.get(long1);
	}


	public Module getModule(Long moduleId) {
		return (Module) this.modules.get(moduleId);
	}

	
	public Long[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}


	public Group getGroup(Long long1) {
		return (Group) this.groups.get(long1);
	}

	
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	
	public Timeslot getTimeslot(Long long1) {
		return (Timeslot) this.timeslots.get(long1);
	}

	
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	
	public Class[] getClasses() {
		return this.classes;
	}

	
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	
	public int calcClashes() {
		int clashes = 0;

		for (Class classA : this.classes) {
			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
			Long groupSize = this.getGroup(classA.getGroupId()).getGroupSize();
			
			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if room is taken
			for (Class classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}

			// Check if professor is available
			for (Class classB : this.classes) {
				if (classA.getProfessorId() == classB.getProfessorId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
		}

		return clashes;
	}


	public int getTimetableId() {
		return id;
	}
	
	public void appendModule(Long class_id, Long module_id) {
		// create a new ArrayList 
		
		int n = (this.groups.get(class_id).getModuleIds().length) ;
		Long[] list = new Long[n+1];
		
		for (int i = 0 ; i < n ; i++ ) {
			list[i] = this.groups.get(class_id).getModuleIds()[i] ;
		}
  
        // Add the new element 
        list[n] = module_id;
  
        // Convert the Arraylist to array 
        //Integer[] mods = list.toArray(mods);
        this.groups.get(class_id).setModuleIds(list);
	}
}
