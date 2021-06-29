package test2;


public class Class {
    private final int classId;
    private final int groupId;
    private final int moduleId;
    private int professorId;
    private int timeslotId;
    private int roomId;
    
    
    public Class(int classId, int groupId, int moduleId){
        this.classId = classId;
        this.moduleId = moduleId;
        this.groupId = groupId;
    }
    
    /**
     * Add professor to class
     * 
     * @param professorId
     */
    public void addProfessor(int professorId){
        this.professorId = professorId;
    }
    
    /**
     * Add timeslot to class
     * 
     * @param timeslotId
     */
    public void addTimeslot(int timeslotId){
        this.timeslotId = timeslotId;
    }    
    
    /**
     * Add room to class
     * 
     * @param roomId
     */
    public void setRoomId(int roomId){
        this.roomId = roomId;
    }
    
    
    public int getClassId(){
        return this.classId;
    }
    
   
    public int getGroupId(){
        return this.groupId;
    }
   
    public int getModuleId(){
        return this.moduleId;
    }
    
    
    public int getProfessorId(){
        return this.professorId;
    }
    
   
    public int getTimeslotId(){
        return this.timeslotId;
    }
    
    
    public int getRoomId(){
        return this.roomId;
    }
}
