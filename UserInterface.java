import java.util.Scanner;

/**
 * UserInterface serves as both the command line user interface as well as the
 * main controller for the GradeBook project.
 * @author Kosi Gizdarski
 * @author Cameron Sun
 * @author Tom Hay
 * @author Ryan Cebulko
 * @version 2014-04-11
 */
public class UserInterface {
    /* Scanner used for reading user input */
    private Scanner inputScanner;
    /* MyGradeBook object associated with this session */
    private MyGradeBook gradebook;
    
    /**
     * 
     */
    public UserInterface() {
        inputScanner = new Scanner(System.in);
        gradebook = null;
    }
    
    /**
     * 
     */
    private void printHeader() {
        System.out.println("+-------------------------------------------------+");
        System.out.println("|-----------------| XxXGradeXxX |-----------------|");
        System.out.println("+-------------------------------------------------+");
    }
    
    /**
     * 
     */
    private void initializeGradeBook() {
        printInitialMenu();
        String[] command = getLine().split(" ");
        if (command[0].equals("new")) {
            gradebook = MyGradeBook.initialize();
        }
        else if (command[0].equals("load")) {
            gradebook = MyGradeBook.initializeWithFile(command[1]);
        }
        else if (command[0].equals("exit")) {
            return;
        }
        else {
            System.out.println("Unrecognized input. Please try again.");
            initializeGradeBook();
        }
    }
    
    
    private void printInitialMenu() {
        System.out.println("Here are some commands to get your started:");
        System.out.println("'new' - makes an empty new gradebook");
        System.out.println("'load[filename]' - loads an existing gradebook");
        System.out.println("'exit' - closes the program");
    }
    
    private void printMainMenu() {
        System.out.println("This is the main menu. Select what you would like to do:");
        System.out.println("[1] Add Assignment");
        System.out.println("[2] Add Student");
        System.out.println("[3] Retrieve information...");
        System.out.println("[4] Edit Gradebook");
        System.out.println("[5] Exit");
        System.out.print("Enter your choice here --> ");
    }
    
    private void printInformationMenu() {
        System.out.println("This is the information menu.");
        System.out.println("It provides all sorts of useful statistics.");
        System.out.println("==== STUDENT ====");
        System.out.println("[1] Current Grade");
        System.out.println("==== ASSIGNMENT ====");
        System.out.println("[2] Average Grade");
        System.out.println("[3] Maxaximum");
        System.out.println("[4] Median");
        System.out.println("[5] Minimum");
        System.out.print("Enter your numerical choice here --> ");
    }
    
    private void printEditingMenu() {
        System.out.println("This is the editing menu.");
        System.out.println("It allows you to edit the content of the gradebook.");
        System.out.println("[1] Change Grade");
        System.out.print("Enter your numerical choice here --> ");
    }
    
    // HELPER FUNCTIONS FOR I/O
    /**
     * 
     * @return
     */
    private int getNumber() {
        return inputScanner.nextInt();
    }
    
    /**
     * 
     * @return
     */
    private String getLine() {
        return inputScanner.nextLine();
    }
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.printHeader();
    }

}
