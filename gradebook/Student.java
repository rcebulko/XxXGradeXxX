package gradebook;

/**
 * Student is a class containing all data related to a student in
 * a school-system
 * 
 * @author Kosi Gizdarski
 * @author Cameron Sun
 * @author Tom Hay
 * @author Ryan Cebulko
 * @version 2014-04-11
 */
public class Student {
	/** username for student */
    private String username;
    
    /** student first name */
    private String firstName;
    
    /** student last name */
    private String lastName;
    
    /** name of student's advisor */
    private String advisor;
    
    /** expected year of graduation */
    private int gradYear;
    
    /**
     * constructor to initialize Student fields
     * 
     * @param username username for student
     * @param firstName student first name
     * @param lastName student last name
     * @param advisor name of student's advisor
     * @param gradYear expected year of graduation
     */
    public Student(String username, String firstName, String lastName,
            String advisor, int gradYear) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.advisor = advisor;
        this.gradYear = gradYear;
    } // end constructor Student

    /**
     * accessor for username field
     * 
     * @return value of username
     */
    public String getUsername() {
        return username;
    } // end method getUsername
    
    /**
     * mutator for username field
     *
     * @param username value to set for username field
     */
    public void setUsername(String username) {
        this.username = username;
    } // end method setUsername
    
    /**
     * accessor for firstName field
     * 
     * @return value of firstName
     */
    public String getFirstName() {
        return firstName;
    } // end method getFirstName
    
    /**
     * mutator for firstName field
     *
     * @param firstName value to set for firstName field
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } // end method setFirstName
    
    /**
     * accessor for lastName field
     * 
     * @return value of lastName
     */
    public String getLastName() {
        return lastName;
    } // end method getLastName
    
    /**
     * mutator for lastName field
     *
     * @param lastName value to set for lastName field
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    } // end method setLastName
    
    /**
     * accessor for advisor field
     * 
     * @return value of advisor
     */
    public String getAdvisor() {
        return advisor;
    } // end method getAdvisor
    
    /**
     * mutator for advisor field
     *
     * @param advisor value to set for advisor field
     */
    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    } // end method setAdvisor
    
    /**
     * accessor for gradYear field
     * 
     * @return value of gradYear
     */
    public int getGradYear() {
        return gradYear;
    } // end method getGradYear
    
    /**
     * mutator for gradYear field
     *
     * @param gradYear value to set for gradYear field
     */
    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    } // end method setGradYear
    
    /**
     * determines whether a given object is equal to the student
     *
     * @param obj object to compare for equality
     * @return whether or not the given object is equal to the student
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        
        Student s = (Student) obj;
        return s.username.equals(username) &&
                s.firstName.equals(firstName) &&
                s.lastName.equals(lastName) &&
                s.advisor.equals(advisor) &&
                s.gradYear == gradYear;
    } // end method equals
    
    /**
     * generates a deterministic hash for the student
     *
     * @return a hash of the student
     */
    @Override
    public int hashCode() {
        return username.hashCode() * 11 +
                firstName.hashCode() * 13 +
                lastName.hashCode() * 17 +
                advisor.hashCode() * 19 +
                ((Integer) gradYear) * 23;
    } // end method hashCode
} // end class Student