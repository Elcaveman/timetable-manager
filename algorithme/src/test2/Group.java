package test2;

public class Group {
    private final Long groupId;
    private final Long groupSize;
    private Long moduleIds[];

    
    
    public void setModuleIds(Long[] moduleIds) {
		this.moduleIds = moduleIds;
	}


	/**Initialize Group*/
    public Group(Long class_id, Long module_id, Long[] is){
        this.groupId = (long) class_id;
        this.groupSize = module_id;
        this.moduleIds = is;
    }
    
 
    public Group(int groupId2, Long groupSize2, long[] ls) {
		this.groupId = (long) groupId2;
		this.groupSize = groupSize2;
		// TODO Auto-generated constructor stub
	}


	public Long getGroupId(){
        return this.groupId;
    }
    
  
    public Long getGroupSize(){
        return this.groupSize;
    }
        
    /** Get array of group's moduleIds*/
    public Long[] getModuleIds(){
        return this.moduleIds;
    }
}
