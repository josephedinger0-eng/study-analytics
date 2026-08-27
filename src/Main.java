import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
public class Main {
	public static void main(String[] args) {

        // Creating objects and variables
        Scanner scanner = new Scanner(System.in);
        ArrayList<StudySession> studySessions = new ArrayList<>();
        boolean running = true;

        while(running) {
            System.out.println("Welcome to the Study Session Tracker!");
            System.out.println("1. Add a new study session");
            System.out.println("2. View all study sessions");
            System.out.println("3. Exit");
            System.out.print("Please select an option (1-3): ");

            int choice = 0;
            boolean validChoice = false;

            // Try-Catch to handle InputMismatchExceptions
            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid input: Please select a number between 1 and 3.");
                    } else {
                        validChoice = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input: Please enter an integer.");
                    scanner.next(); // Clear the invalid input
                }
            }

            switch (choice) {
                case 1:
                    // User input for subject
                    System.out.print("Enter your subject: ");
                    String subject;
                    subject = scanner.nextLine();

                    // User input for topic
                    System.out.print("Enter your topic: ");
                    String topic;
                    topic = scanner.nextLine();

                    // User input for minutes
                    System.out.print("Enter the number of minutes: ");

                    int minutes = 0; 
                    boolean validMinutes = false;


                    // Try-Catch to handle InputMismatchExceptions
                    while(!validMinutes){
                        try {
                            minutes = scanner.nextInt();
                            if(minutes <= 0){
                                System.out.println("Invalid Input: Please enter a positive integer.");
                            } else {
                                validMinutes = true;
                            }
                        } catch (InputMismatchException e){
                            System.out.println("Invalid Input: Please enter an integer.");
                            scanner.next();
                        }
                    }

                    // User input for score
                    System.out.print("Enter your score: ");
                    double score = 0;
                    boolean validScore = false;

                    // Try-Catch to handle InputMismatchExceptions
                    while (!validScore) {
                        try {
                            score = scanner.nextDouble();
                            if (score < 0 || score > 100) {
                                System.out.println("Invalid input: Please enter a score between 0 and 100.");
                            } else {
                                validScore = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input: Please enter a valid double for the score.");
                            scanner.next(); // Clear the invalid input
                        }
                    }

                    // Creating a StudySession with the user inputs and printing
                    StudySession session = new StudySession(subject, topic, minutes, score);
                    studySessions.add(session);
                    System.out.println("Study session added successfully! You currently have " + studySessions.size() + " study session(s).");
                    break;
                case 2:
                    for(StudySession sessionA : studySessions) {
                        System.out.println(sessionA.toString());
                    }
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting the Study Session Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();

	}
}
