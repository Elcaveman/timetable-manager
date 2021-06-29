package test2;


public class Class {
    private final Long classId;
    private final Long groupId;
    private final Long moduleId;
    private Long professorId;
    private Long timeslotId;
    private Long roomId;
    
    /**Initialize new Class */
    public Class(Long classId, Long long1, Long moduleId2){
        this.classId = classId;
        this.moduleId = moduleId2;
        this.groupId = long1;
    }
    
    // Just for class index list
    public Class(int classIndex, Long groupId2, Long moduleId2) {
		this.classId = (long) classIndex;
		this.groupId = groupId2;
		this.moduleId = moduleId2;
		// TODO Auto-generated constructor stub
	}

	public void addProfessor(Long chromosome){
        this.professorId = chromosome;
    }
    
    public void addTimeslot(Long chromosome){
        this.timeslotId = chromosome;
    }    
    
    public void setRoomId(Long chromosome){
        this.roomId = chromosome;
    }
    
    public Long getClassId(){
        return this.classId;
    }
    
    public Long getGroupId(){
        return this.groupId;
    }
    
    public Long getModuleId(){
        return this.moduleId;
    }
    
    public Long getProfessorId(){
        return this.professorId;
    }
    
    public Long getTimeslotId(){
        return this.timeslotId;
    }
    
    public Long getRoomId(){
        return this.roomId;
    }

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", groupId=" + groupId + ", moduleId=" + moduleId + ", professorId="
				+ professorId + ", timeslotId=" + timeslotId + ", roomId=" + roomId + "]";
	}
}
