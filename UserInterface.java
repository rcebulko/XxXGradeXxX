import java.util.Scanner;

public class UserInterface {
    
    private Scanner inputScanner;
    
    public UserInterface() {
        inputScanner = new Scanner(System.in);
    }
    
    public void printHeader() {
        System.out.println("+-------------------------------------------------+");
        System.out.println("|-----------------| XxXGradeXxX |-----------------|");
        System.out.println("+-------------------------------------------------+");
    }
    
    public void buildNewBook() {
        System.out.println("Here are some commands to get your started:");
        System.out.println("'new' - makes a new gradebook");
        System.out.println("'load [filename]' - loads an existing gradebook");
        System.out.println("'exit' - closes the program");
        String command = getLine();
        if (command.equals("new")) {
            
        }
        else if (command.substring(0,4).equals("load")) {
            
        }
        else if (command.equals("exit")) {
            
        }
        else {
            System.out.println("Unrecognized input. Please try again.");
            buildNewBook();
        }
    }
    
    public int getNumber() {
        return inputScanner.nextInt();
    }
    
    public String getLine() {
        return inputScanner.nextLine();
    }
    
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.printHeader();
    }

}
