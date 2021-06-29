package test2;

public class Timeslot {
    private final Long timeslotId;
    private final int timeslot;

    /** Initalize new Timeslot*/
    public Timeslot(Long long1, int i){
        this.timeslotId = long1;
        this.timeslot = i;
    }
    

    public Long getTimeslotId(){
        return this.timeslotId;
    }
    
  
    public int getTimeslot(){
        return this.timeslot;
    }
}