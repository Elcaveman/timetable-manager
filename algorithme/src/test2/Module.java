package test2;

public class Module {
    private final Long moduleId;
    private final Long moduleCode;
    private final String module;
    private final Long[] professorIds;
    
    /**
     * Initialize new Module
     * 
     * @param moduleId2
     * @param l
     * @param module
     * @param professorIds2
     */
    public Module(Long moduleId2, long l, String module, Long[] professorIds2){
        this.moduleId = moduleId2;
        this.moduleCode = l;
        this.module = module;
        this.professorIds = professorIds2;
    }
    
    /**
     * Get moduleId
     * 
     * @return moduleId
     */
    public Long getModuleId(){
        return this.moduleId;
    }
    
    /**
     * Get module code
     * 
     * @return moduleCode
     */
    public Long getModuleCode(){
        return this.moduleCode;
    }
    
    /**
     * Get module name
     * 
     * @return moduleName
     */
    public String getModuleName(){
        return this.module;
    }
    
    /**
     * Get random professor Id
     * 
     * @return professorId
     */
    public Long getRandomProfessorId(){
        Long professorId = professorIds[(int) (professorIds.length * Math.random())];
        return professorId;
    }
}
