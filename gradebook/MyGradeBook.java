package gradebook;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * MyGradeBook is a class containing all functions related to creating,
 * populating, modifying, accessing and persisting data related to
 * assignments, students, and grades received by students on
 * assignments, including various pieces of statistical data 
 * 
 * @author Kosi Gizdarski
 * @author Cameron Sun
 * @author Tom Hay
 * @author Ryan Cebulko
 * @version 2014-04-11
 */
public class MyGradeBook {
	/**
	 * list of assignments in the order they were added, for index-based retrieval
	 */
	private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
	
	/**
	 * map relating usernames to their associated students
	 */
	private TreeMap<String, Student> students = new TreeMap<String, Student>();
	
	/**
	 * map to look up an assignment's index in assignments for name-based retrieval
	 */
	private TreeMap<String, Integer> assignmentNameToIndex = new TreeMap<String, Integer>();
	
	/**
	 * map relating assignment names and usernames to grades;
	 * the first key is the assignment name and the second is the username;
	 * this association tables allows the Assignment and Student classes to
	 * operate completely independently with know knowledge of or need for
	 * the other
	 */
	private HashMap<String, HashMap<String, Float>> grades = new HashMap<String, HashMap<String, Float>>();
	
	/** total of percentage values of all assignments */
	private float percentOfSemester = 0;
	
	/**
	 * factory method to construct an empty MyGradebook
	 * 
	 * @return an empty MyGradeBook
	 */
	public static MyGradeBook initialize() {
		return new MyGradeBook();
	} // end method initialize

	/**
	 * factory method to construct a MyGradebook that contains the grade book
	 * from filename
	 * 
	 * @param filename
	 *            the filename for the file that contains the initial grade
	 *            book, which is formatted like initial.txt
	 * @return a MyGradebook that contains the grade book from filename
	 * @throws IOException
	 * 			  thrown if error occurs processing or closing file-stream
	 */
	public static MyGradeBook initializeWithFile(String filename)
			throws IOException {
		// initialize an empty gradebook
		MyGradeBook mgb = initialize();
		
		// fill new gradebook with data from file
		mgb.processFile(filename);
		
		// return populated gradebook
		return mgb;
	} // end method initializeWithFile

	/**
	 * factory method to construct a MyGradebook that contains the grade book
	 * from startingString
	 * 
	 * @param startingString
	 *            String that contains the initial grade book, which is
	 *            formatted like initial.txt
	 * @return a MyGradebook that contains the grade book from startingString
	 * @throws IOException
	 * 			  thrown if error occurs processing or closing string byte-stream
	 */
	public static MyGradeBook initializeWithString(
	        String startingString)
	        		throws IOException {
		// initialize an empty gradebook
		MyGradeBook mgb = initialize();
		
		// fill new gradebook with data from a string
		mgb.processString(startingString);
		
		// return populated gradebook
		return mgb;
	} // end method initializeWithString

	/**
	 * add to the state of this grade book---new assignments, new students, new
	 * grades---by processing filename
	 * 
	 * @param filename
	 *            the filename for a file that contains information that will be
	 *            added to the grade book. The file could contain several
	 *            different types of information---new assignments, new
	 *            students, new grades. The file will be formatted like
	 *            addAssignments.txt, addStudents.txt, gradesForAssignment1.txt,
	 *            and gradesForStudent.txt.
	 * @throws IOException
	 * 			  thrown if error occurs processing or closing file-stream
	 */
	public void processFile(String filename) 
			throws IOException {
		// load input stream from file
		InputStream input = new FileInputStream(filename);
		
		// process input stream
		processStream(input);
		
		// release resources associated with input stream
		input.close();
	} // end method processFile

	/**
	 * add to the state of this grade book---new assignments, new students, new
	 * grades---by processing additionalString
	 * 
	 * @param additionalString
	 *            String that contains information that will be added to the
	 *            grade book. The String could contain several different types
	 *            of information---new assignments, new students, new grades.
	 *            The String will be formatted like addAssignments.txt,
	 *            addStudents.txt, gradesForAssignment1.txt, and
	 *            gradesForStudent.txt.
	 * @throws IOException
	 * 			  thrown if error occurs processing or closing string byte-stream
	 */
	public void processString(String additionalString)
			throws IOException {
		// load input stream from string
		InputStream input = new ByteArrayInputStream(
				additionalString.getBytes("UTF-8")) ;
		
		// process input stream
		processStream(input);
		
		// release resources associated with input stream
		input.close();
	} // end method processString
	
	/**
	 * add to the state of this grade book---new assignments, new students, new
	 * grades---by processing input
	 * 
	 * @param input
	 * 			  Input stream that contains information that will be added to
	 * 			  the grade book. The String could contain several different
	 * 			  types of information---new assignments, new students, new
	 * 			  grades. The String will be formatted like addAssignments.txt,
	 *            addStudents.txt, gradesForAssignment1.txt, and
	 *            gradesForStudent.txt.
	 */
	private void processStream(InputStream input) {
		// initialize scanner resource
		Scanner in = new Scanner(input);
		
		// get input type header
		String inputType = in.nextLine().trim();
		
		// handle inputs containing a full gradebook
		if (inputType.equals("GRADEBOOK")) {
			// collect all assignment data, splitting on tabs
			String[] assignmentsName = in.nextLine().trim().split("\\t");
			String[] assignmentsTotalPoints = in.nextLine().trim().split("\\t");
			String[] assignmentsPercentOfGrade = in.nextLine().trim().split("\\t");
			
			// iterate through assignment input data
			for (int i = 0; i < assignmentsName.length; ++i) {
				addAssignment(new Assignment(
						assignmentsName[i],
						new Float(assignmentsTotalPoints[i].trim()),
						new Float(assignmentsPercentOfGrade[i].trim())));
			} // end for loop

			// iterate through lines containing user data
			while (in.hasNextLine()) {
				// split line by tabs
				String[] userData = in.nextLine().trim().split("\\t");
				
				// create and add a student object with input data
				addStudent(new Student(
						userData[0], // username
						userData[1], // first name
						userData[2], // last name
						userData[3], // advisor
						Integer.parseInt(
								userData[4], // expected graduation year
								10)));
                
                // add student entry to grade lookup table with 0s for all grades
                for (int i = 0; i < userData.length - 5; ++i) {
                	changeGrade(
                			assignmentsName[i],
                			userData[0],
                			new Float(userData[i + 5]));
                } // end for loop
			} // end while loop
		} // end if block
		
		// handle inputs listing assignments
		else if (inputType.equals("ASSIGNMENT")) {
			// process at least one record, more if they are present
			do {
				// create and add an assignment object with input data
				addAssignment(new Assignment(
						in.nextLine().trim(), // assignment name
						in.nextFloat(), // total points
						in.nextFloat())); // percent of semester grade
			} while (in.hasNextLine() && in.nextLine().trim().equals("ASSIGNMENT"));
			// end do-while block
		} // end if block
		
		// handle inputs listing students
		else if (inputType.equals("STUDENT")) {
			do {
				// create and add a student object with input data
				addStudent(new Student(
						in.nextLine().trim(), // username
						in.nextLine().trim(), // first name
						in.nextLine().trim(), // last name
						in.nextLine().trim(), // advisor
						in.nextInt(10))); // expected graduation year
			} while (in.hasNextLine() && in.nextLine().trim().equals("STUDENT"));
			// end do-while block
		} // end else-if block
		
		// handle inputs listing grades for an assignment
		else if (inputType.equals("GRADES_FOR_ASSIGNMENT")) {
			String assignmentName = in.nextLine().trim(); // assignment name
			while (in.hasNextLine()) {
				changeGrade(
						assignmentName, // assignment name
						in.nextLine().trim(), // username
						in.nextFloat()); // grade
			} // end while loop
		} // end else-if block
		
		// handle inputs listing grades for a student
		else if (inputType.equals("GRADES_FOR_STUDENT")) {
			String username = in.nextLine().trim(); // username
			while (in.hasNextLine()) {
				changeGrade(
						in.nextLine().trim(), // assignment name
						username, // username
						in.nextFloat()); // grade
			} // end while loop
		} // end else-if block
		
		// handle invalid input formats
		else {
			// release resources associated with input stream
			in.close();
			
			// throw exception identifying input format error
			throw new IllegalArgumentException("Invalid input header");
		} // end else block

		// release resources associated with input stream
		in.close();
	} // end method processStream
	
	private void addAssignment(Assignment newAssignment) {
		// add assignment to list of assignments
		assignments.add(newAssignment);
		
		// add map from assignment name to index
		assignmentNameToIndex.put(
				newAssignment.getName(),
				assignments.size() - 1);
		
		// add assignment to grade lookup table with 0s for all grades
		HashMap<String, Float> assignmentGrades = new HashMap<String, Float>();
		for (String username : students.keySet()) {
			assignmentGrades.put(username, 0f);
		} // end for loop
		grades.put(newAssignment.getName(), assignmentGrades);
		
		// keep track of increasing total semester percentage
		percentOfSemester += newAssignment.getPercentOfGrade();
	}
	
	/**
	 * adds a student record to the gradebook
	 * 
	 * @param newStudent student to add to the gradebook
	 */
	private void addStudent(Student newStudent) {
		// add student to map of students
		students.put(newStudent.getUsername(), newStudent);
		
		// add student entry to grade lookup table with 0s for all grades
		for (Assignment a : assignments) {
			grades.get(a.getName()).put(newStudent.getUsername(), 0f);
		} // end for loop
	} // end method addStudent

	/**
	 * changes the assignment (named assignmentName) grade for student (whose
	 * username is equal to username) to newGrade
	 * 
	 * @param assignmentName
	 *            name of the assignment
	 * @param username
	 *            username for the student
	 * @param newGrade
	 *            the new grade for the given assignment and student
	 * @return whether there was a grade to change. Returns true if the given
	 *         assignment/student combination exists and username’s 
	 *         assignmentName grade is now newGrade, returns false otherwise
	 */
	public boolean changeGrade(String assignmentName,
	        String username, double newGrade) {
		// check that the assignment exists
		if (assignmentNameToIndex.containsKey(assignmentName)) {
			// cache the assignment object for clarity of code
			Assignment a = assignments.get(assignmentNameToIndex.get(assignmentName));
			
			// check if the student exists and make sure the new grade is valid
			if (grades.get(assignmentName).containsKey(username) &&
					newGrade <= a.getTotalPoints() && newGrade >= 0) {
				// assign new grade
				grades.get(assignmentName).put(username, (float) newGrade);
				
				// return true if new grade has been assigned
				return true;
			} // end if block
		} // end if block
		
		// return false if any of the conditions failed
		return false;
	} // end method changeGrade

	/**
	 * calculates the average across all students for a given assignment
	 * 
	 * @param assignmentName
	 *            name of the assignment
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing assignment
	 * @return the average across all students for assignmentName
	 */
	public double average(String assignmentName)
			throws IllegalArgumentException, ArithmeticException {
		// check that the assignment exists
		if (!assignmentNameToIndex.containsKey(assignmentName)) {
			throw new IllegalArgumentException("Assignment not found");
		} // end if block
		// the median is undefined for empty lists
		if (students.size() == 0) {
			throw new ArithmeticException("Empty list has no average");
		} // end if block

		// iterate through the assignment grades, accumulating a total
		float total = 0;
		HashMap<String, Float> gradesForAssignment = grades.get(assignmentName);
		for (Float grade : gradesForAssignment.values()) {
			total += grade;
		} // end for loop
		
		// return the average of all grades
		return total / gradesForAssignment.size();
	} // end method average

	/**
	 * calculates the median across all students for a given assignment
	 * 
	 * @param assignmentName
	 *            name of the assignment
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing assignment
	 * @return the median across all students for assignmentName
	 */
	public double median(String assignmentName)
			throws IllegalArgumentException, ArithmeticException {
		// check that the assignment exists
		if (!assignmentNameToIndex.containsKey(assignmentName)) {
			throw new IllegalArgumentException("Assignment not found");
		} // end if block
		// the median is undefined for empty lists
		if (students.size() == 0) {
			throw new ArithmeticException("Empty list has no median");
		} // end if block

		// iterate through the assignment grades, accumulating building an
		// array of grades to be sorted
		HashMap<String, Float> gradesForAssignment = grades.get(assignmentName);
		float[] pointsOnAssignment = new float[gradesForAssignment.size()];
		int i = 0;
		for (Float grade : gradesForAssignment.values()) {
			pointsOnAssignment[i] = grade;
		} // end for loop
		
		// sort list of grades
		Arrays.sort(pointsOnAssignment);
		// cache list size for reuse
		int size = pointsOnAssignment.length;
		
		// if the list has an odd number of elements, return the middle value
		if (size % 2 == 1) {
			return pointsOnAssignment[size / 2];
		} // end if block
		// otherwise, return the average of the two middle values
		else {
			return (pointsOnAssignment[size / 2 - 1] + pointsOnAssignment[size / 2]) / 2; 
		} // end else block
	} // end method median

	/**
	 * calculates the min across all students for a given assignment
	 * 
	 * @param assignmentName
	 *            name of the assignment
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing assignment
	 * @return the min across all students for assignmentName
	 */
	public double min(String assignmentName)
			throws IllegalArgumentException, ArithmeticException {
		// check that the assignment exists
		if (!assignmentNameToIndex.containsKey(assignmentName)) {
			throw new IllegalArgumentException("Assignment not found");
		} // end if block
		// the minimum is undefined for empty lists
		if (students.size() == 0) {
			throw new ArithmeticException("Empty list has no minimum");
		} // end if block

		// iterate through the assignment grades, keeping track of the smallest
		// value
		HashMap<String, Float> gradesForAssignment = grades.get(assignmentName);
		float min = Float.MAX_VALUE;
		for (Float grade : gradesForAssignment.values()) {			
			// store the current value if it is less than the current minimum
			min = Math.min(min, grade);
		} // end for loop
		
		// return the minimum of all grades
		return min;
	} // end method min

	/**
	 * calculates the max across all students for a given assignment
	 * 
	 * @param assignmentName
	 *            name of the assignment
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing assignment
	 * @return the max across all students for assignmentName
	 */
	public double max(String assignmentName)
			throws IllegalArgumentException, ArithmeticException {
		// check that the assignment exists
		if (!assignmentNameToIndex.containsKey(assignmentName)) {
			throw new IllegalArgumentException("Assignment not found");
		} // end if block
		// the maximum is undefined for empty lists
		if (students.size() == 0) {
			throw new ArithmeticException("Empty list has no maximum");
		} // end if block

		// iterate through the assignment grades, keeping track of the largest
		// value
		HashMap<String, Float> gradesForAssignment = grades.get(assignmentName);
		float max = Float.MIN_NORMAL;
		for (Float grade : gradesForAssignment.values()) {			
			// store the current value if it is more than the current maximum
			max = Math.max(max, grade);
		} // end for loop
		
		// return the maximum of all grades
		return max;
	} // end method max

	/**
	 * calculates the current grade for the given student
	 * 
	 * @param username
	 *            username for the student
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing student
	 * @return the current grade for student with username. The current grade is
	 *         calculated based on the current assignment grades, assignment
	 *         total points, assignment percent of semester. The current grade
	 *         for a student is the sum of the relative assignment grades
	 *         divided by the current percent of semester time 100. Since all
	 *         grades may not currently be entered, we have to divide by the
	 *         current percent. The relative assignment grade is the student's
	 *         assignment grade divide by total point value for the assignment
	 *         times the percent of semester.
	 */
	public double currentGrade(String username)
			throws IllegalArgumentException {
		// check that the student exists
		if (!students.containsKey(username)) {
			throw new IllegalArgumentException("Student not found");
		} // end if block

		// iterate through the assignments, accumulating a total
		float total = 0;
		for (Assignment a : assignments) {
			float relativeGrade =
					grades.get(a.getName()).get(username) / a.getTotalPoints();
			total += a.getPercentOfGrade() * relativeGrade;					
		} // end for loop
		
		// return the average of all grades
		return total * 100 / (grades.size() * percentOfSemester);
	} // end method currentGrade

	/**
	 * calculates the current grade for all students
	 * 
	 * @return HashMap of the current grades for all students. The key of the
	 *         HashMap is the username of the student. The value is the current
	 *         grade for the associated student. The current grade is calculated
	 *         based on the current assignment grades, assignment total points,
	 *         assignment percent of semester. The current grade for a student
	 *         is the sum of the relative assignment grades divided by the
	 *         current percent of semester time 100. Since all grades may not
	 *         currently be entered, we have to divide by the current percent.
	 *         The relative assignment grade is the student's assignment grade
	 *         divide by total point value for the assignment times the percent
	 *         of semester.
	 */
	public HashMap<String, Double> currentGrades() {
		// create a map relating students to their current grades
		HashMap<String, Double> currentGrades = new HashMap<String, Double>();
		
		// iterate through users, storing their current grades
		for (String username : students.keySet()) {
			currentGrades.put(username, currentGrade(username));
		} // end for loop
		
		// return the built-up map of current grades
		return currentGrades;
	} // end method currentGrades()

	/**
	 * provides the grade earned by the given student for the given assignment
	 * 
	 * @param assignmentName
	 *            name of the assignment
	 * @param username
	 *            username for the student
	 * @throws IllegalArgumentException
	 * 			  thrown when the parameters do not match an existing 
	 * 			  assignment or student
	 * @return the grade earned by username for assignmentName
	 */
	public double assignmentGrade(String assignmentName, String username)
			throws IllegalArgumentException {
		// check that the assignment exists
		if (!assignmentNameToIndex.containsKey(assignmentName)) {
			throw new IllegalArgumentException("Assignment not found");
		} // end if block
		// check that the student exists
		if (!students.containsKey(username)) {
			throw new IllegalArgumentException("Student not found");
		} // end if block
		
		return grades.get(assignmentName).get(username);
	} // end method assignmentGrade

	/**
	 * provide a String that contains the current grades of all students in the
	 * course
	 * 
	 * @return a String that contains the current grades of all students in the
	 *         course. The String should be formatted like
	 *         currentGrades.txt---CURRENT_GRADES heading and each row: username
	 *         followed by tab and current grade. The usernames will be listed
	 *         alphabetically.
	 */
	public String outputCurrentGrades() {
		// create a string builder for iterative string concatenation
		StringBuilder sb = new StringBuilder("CURRENT_GRADES");
		
		// build output string
		for (String username : students.keySet()) {
			sb.append("\n" + username + " " + currentGrade(username));
		} // end for loop
		
		// return output string
		return sb.toString();
	} // end method outputCurrentGrades

	/**
	 * provide a String that contains the current grades of the given student
	 * 
	 * @param username
	 *            username for student
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing student
	 * @return a String that contains the current grades of username. The String
	 *         should be formatted like studentGrades.txt---STUDENT_GRADES
	 *         heading, student info, dividers, each assignment (assignment name
	 *         followed by tab and assignment grade), and current grade.
	 *         Assignments are to remain in the same order as given.
	 */
	public String outputStudentGrades(String username)
			throws IllegalArgumentException {
		// check that the student exists
		if (!students.containsKey(username)) {
			throw new IllegalArgumentException("Student not found");
		} // end if block
		
		// create a string builder for iterative string concatenation
		StringBuilder sb = new StringBuilder("STUDENT_GRADES");
		
		// get student to be output
		Student s = students.get(username);
		
		// output student data
		sb.append(
				"\n" + username +
				"\n" + s.getFirstName() +
				"\n" + s.getLastName() +
				"\n" + s.getAdvisor() +
				"\n" + s.getGradYear() +
				"\n----");
		
		// iterate through assignments and append each to the output string
		for (String assignmentName : assignmentNameToIndex.keySet()) {
			sb.append(
					"\n" + assignmentName +
					"\t" + assignmentGrade(assignmentName, username));
		} // end for loop
		
		// output current average grade
		sb.append("\n----\nCURRENT GRADE\t" + currentGrade(username));
		
		// return output string
		return sb.toString();
	} // end method outputStudentGrades

	/**
	 * provide a String that contains the assignment grades of all students in
	 * the course for the given assignment
	 * 
	 * @param assignName
	 *            name of the assignment
	 * @throws IllegalArgumentException
	 * 			  thrown when parameter does not match an existing assignment
	 * @return a String that contains the assignment grades of all students in
	 *         the course for assignName. The String should be formatted like
	 *         assignmentGrade.txt---ASSIGNMENT_GRADES heading, assignment info,
	 *         dividers, each student (username followed by tab and assignment
	 *         grade), and assignment stats. The usernames will be listed
	 *         alphabetically while assignments are to remain in the same 
	 *         order as given.
	 */
	public String outputAssignmentGrades(String assignName)
			throws IllegalArgumentException {
		// check that the student exists
		if (!assignmentNameToIndex.containsKey(assignName)) {
			throw new IllegalArgumentException("Student not found");
		} // end if block
		
		// create a string builder for iterative string concatenation
		StringBuilder sb = new StringBuilder("ASSIGNMENT_GRADES");
		
		// get assignment to be output
		Assignment a = assignments.get(assignmentNameToIndex.get(assignName));
		
		// output assignment data
		sb.append(
				"\n" + a.getName() +
				"\n" + a.getTotalPoints() +
				"\n" + a.getPercentOfGrade() +
				"\n----");
		
		// iterate through students and append each to the output string
		for (Student s : students.values()) {
			sb.append(
					"\n" + s.getUsername() +
					"\n" + s.getFirstName() +
					"\n" + s.getLastName() +
					"\n" + s.getAdvisor() +
					"\n" + s.getGradYear());
		} // end for loop
		
		// output statistical data for the assignment
		sb.append(
				"\n----\nSTATS\nAverage " + average(assignName) +
				"\nMedian " + median(assignName) +
				"\nMax " + max(assignName) +
				"\nMin " + min(assignName));

		// return output string
		return sb.toString();
	} // end method outputAssignmentGrades

	/**
	 * provide a String that contains the current grade book. This String could
	 * be used to initialize a new grade book.
	 * 
	 * @return a String that contains the current grade book. This String could
	 *         be used to initialize a new grade book. The String should be
	 *         formatted like gradebook.txt. The usernames will be listed
	 *         alphabetically.
	 */
	public String outputGradebook() {
		// create a string builder for iterative string concatenation
		StringBuilder sb = new StringBuilder("GRADEBOOK");
		
		// output header
		sb.append("\n\t\t\t\t");
		
		// iterate through assignments and append each to the output string
		for (Assignment a : assignments) {
			sb.append("\t" + a.getName());
		} // end for loop
		sb.append("\n\t\t\t\t");
		for (Assignment a : assignments) {
			sb.append("\t" + a.getTotalPoints());
		} // end for loop
		sb.append("\n\t\t\t\t");
		for (Assignment a : assignments) {
			sb.append("\t" + a.getPercentOfGrade());
		} // end for loop
		
		// iterate through students and append each to the output string
		for (Student s : students.values()) {
			sb.append(
					"\n" + s.getUsername() +
					"\t" + s.getFirstName() +
					"\t" + s.getLastName() +
					"\t" + s.getAdvisor() +
					"\t" + s.getGradYear());
			
			// iterate through assignments and append each to the output string
			for (String assignmentName : assignmentNameToIndex.keySet()) {
				sb.append("\t" + assignmentGrade(
						assignmentName,
						s.getUsername().toString()));
			} // end for loop
		} // end for loop
		
		// return output string
		return sb.toString();
	} // end method outputGradebook
} // end class MyGradeBook