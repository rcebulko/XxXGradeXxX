package gradebook;

/**
 * Assignment is a class containing all data related to an assignment in
 * a course
 * 
 * @author Kosi Gizdarski
 * @author Cameron Sun
 * @author Tom Hay
 * @author Ryan Cebulko
 * @version 2014-04-11
 */
public class Assignment {
	/** assignment name */
    private String name;
    
    /** total possible points that can be earned */
    private float totalPoints;
    
    /** percent of grade this assignment counts for */
    private float percentOfGrade;
    
    /**
     * constructor to initialize Assignment fields
     * 
     * @param name assignment name
     * @param totalPoints total possible points that can be earned
     * @param percentOfGrade percent of grade this assignment counts for
     */
    public Assignment(String name, float totalPoints, float percentOfGrade) {  
        this.name = name;
        this.totalPoints = totalPoints;
        this.percentOfGrade = percentOfGrade;
    } // end constructor Assignment

    /**
     * accessor for name field
     *
     * @return value of name
     */
    public String getName() {
        return name;
    } // end method getName
    
    /**
     * mutator for name field
     *
     * @param value to set for name field
     */
    public void setName(String name) {
        this.name = name;
    } // end method setName
    
    /**
     * accessor for totalPoints field
     *
     * @return value of totalPoints
     */
    public float getTotalPoints() {
        return totalPoints;
    } // end method getTotalPoints
    
    /**
     * mutator for totalPoints field
     *
     * @param value to set for totalPoints field
     */
    public void setTotalPoints(float totalPoints) {
        this.totalPoints = totalPoints;
    } // end method setTotalPoints
    
    /**
     * accessor for percentOfGrade field
     *
     * @return value of percentOfGrade
     */
    public float getPercentOfGrade() {
        return percentOfGrade;
    } // end method getPercentOfGrade
    
    /**
     * mutator for percentOfGrade field
     *
     * @param value to set for percentOfGrade field
     */
    public void setPercentOfGrade(float percentOfGrade) {
        this.percentOfGrade = percentOfGrade;
    } // end method setPercentOfGrade
    
    /**
     * determines whether a given object is equal to the assignment
     *
     * @param obj object to compare for equality
     * @return whether or not the given object is equal to the assignment
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Assignment)) {
            return false;
        }
        
        Assignment a = (Assignment) obj;
        return a.name.equals(name) &&
                a.totalPoints == totalPoints &&
                a.percentOfGrade == percentOfGrade;
    } // end method equals
    
    /**
     * generates a deterministic hash for the assignment
     *
     * @return a hash of the assignment
     */
    @Override
    public int hashCode() {
        return name.hashCode() * 11 +
                ((Float) totalPoints).hashCode() * 13 +
                ((Float) percentOfGrade).hashCode() * 17;
    } // end method hashCode
} // end class Assignment