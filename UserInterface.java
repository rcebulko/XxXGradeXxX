import gradebook.MyGradeBook;

import java.io.IOException;
import java.util.Scanner;

/**
 * UserInterface serves as both the command line user interface as well as the
 * main controller for the GradeBook program.
 * Built as the final project for CS 3500 OOD Spring 2014.
 * @author Kosi Gizdarski
 * @author Cameron Sun
 * @author Tom Hay
 * @author Ryan Cebulko
 * @version 2014-04-11
 */
public class UserInterface {
    /* scanner used for reading user input */
    private Scanner inputScanner;
    /* MyGradeBook object associated with this session */
    private MyGradeBook gradebook;
    
    /**
     * Default constructor that initializes the fields of the UserInterface
     */
    public UserInterface() {
        inputScanner = new Scanner(System.in);
        gradebook = null;
    }
    
    /**
     * 
     */
    public void initializeGradeBook() {
        printHeader();
        printInitialMenu();
        String[] command = getLine().split(" ");
        if (command[0].equals("new")) {
            gradebook = MyGradeBook.initialize();
        }
        else if (command[0].equals("load")) {
            try {
                gradebook = MyGradeBook.initializeWithFile(command[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (command[0].equals("exit")) {
            return;
        }
        else {
            System.out.println("Unrecognized input. Please try again.");
            initializeGradeBook();
        }
    }
    
    /**
     * 
     */
    public void mainMenu() {
        printHeader();
        printMainMenu();
        int option = getNumber();
        switch (option) {
            case 0: gradebook.outputGradebook();
                    break;
            case 1: 
                    break;
            case 2:  
                    break;
            case 3: informationMenu();
                    break;
            case 4: 
                    break;
            case 5:  
                    break;
      
            default:
                System.out.println("Unrecognized input. Please try again.");
                mainMenu();
                break;
        }
    }
    
    public void informationMenu() {
        printHeader();
        printMainMenu();
        int option = getNumber();
        switch (option) {
            case 1: 
                    break;
            case 2:  
                    break;
            case 3:  
                    break;
            case 4:  
                    break;
            case 5: 
                    break;
            case 6: 
                    break;
            case 7: 
                    break;
            case 8: 
                    break;
            default:
                System.out.println("Unrecognized input. Please try again.");
                informationMenu();
                break;
        }
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
    private void printInitialMenu() {
        System.out.println("Here are some commands to get your started:");
        System.out.println("'new' - makes an empty new gradebook");
        System.out.println("'load[filename]' - loads an existing gradebook");
        System.out.println("'exit' - closes the program");
    }
    
    /**
     * 
     */
    private void printMainMenu() {
        System.out.println("This is the main menu.");
        System.out.println("Select what you would like to do.");
        System.out.println("[0] Output GradeBook");
        System.out.println("[1] Add Assignment");
        System.out.println("[2] Add Student");
        System.out.println("[3] Retrieve information...");
        System.out.println("[4] Edit Grade");
        System.out.println("[5] Exit");
        System.out.print("Enter your choice here --> ");
    }
    
    /**
     * 
     */
    private void printInformationMenu() {
        System.out.println("This is the information menu.");
        System.out.println("It provides all sorts of useful statistics.");
        System.out.println("==== ON STUDENT ====");
        System.out.println("[1] Current Grade");
        System.out.println("[2] Detailed Grade Report");
        System.out.println("[2] Class Summary");
        System.out.println("==== ON ASSIGNMENT ====");
        System.out.println("[4] Average Grade");
        System.out.println("[5] Maximum");
        System.out.println("[6] Median");
        System.out.println("[7] Minimum");
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
