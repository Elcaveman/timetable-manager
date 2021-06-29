package test2;


public class Professor {
    private final Long professorId;
    private final String professorName;


    public Professor(Long long1, String professorName){
        this.professorId = long1;
        this.professorName = professorName;
    }
    
   
    public Long getProfessorId(){
        return this.professorId;
    }
    
   
    public String getProfessorName(){
        return this.professorName;
    }
}
