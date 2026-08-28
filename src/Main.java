import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.HashMap;
public class Main {
	public static void main(String[] args) {

        // Creating objects and variables
        Scanner scanner = new Scanner(System.in);
        ArrayList<StudySession> studySessions = new ArrayList<>();
        StudyAnalytics analytics = new StudyAnalytics(studySessions);
        boolean running = true;

        while(running) {
            System.out.println("Welcome to the Study Session Tracker!");
            System.out.println("1. Add a new study session");
            System.out.println("2. View all study sessions");
            System.out.println("3. View study session summary");
            System.out.println("4. Exit");
            System.out.print("Please select an option (1-4): ");

            int choice = 0;
            boolean validChoice = false;

            // Try-Catch to handle InputMismatchExceptions
            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (choice < 1 || choice > 4) {
                        System.out.println("Invalid input: Please select a number between 1 and 4.");
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
                    addStudySession(scanner, studySessions);
                    break;
                case 2:
                    for(StudySession sessionA : studySessions) {
                        System.out.println(sessionA.toString());
                    }
                    break;
                case 3:
                    if(studySessions.isEmpty()) {
                        System.out.println("No study sessions recorded yet.");
                    } else {
                        int totalMinutes = analytics.getTotalMinutes();
                        double averageScore = analytics.getAverageScore();
                        HashMap<String, Integer> subjectMinutes = analytics.getMinutesBySubject();
                        
                        System.out.println("Total Study Time: " + totalMinutes + " minutes");
                        System.out.println("Average Score: " + averageScore);
                        System.out.println("Study Time by Subject:");
                        for (String subjectA : subjectMinutes.keySet()) {
                            System.out.println("  " + subjectA + ": " + subjectMinutes.get(subjectA) + " minutes");
                        }
                    }
                    break;
                case 4: 
                    running = false;
                    System.out.println("Exiting the Study Session Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
	}

    // Method to add a new study session
    public static void addStudySession(Scanner scanner, ArrayList<StudySession> studySessions) {
        // User input for subject
        String subject = getNonEmptyString(scanner, "Enter your subject: ");

        // User input for topic1 
        String topic = getNonEmptyString(scanner, "Enter your topic: ");
        
        // User input for minutes
        System.out.print("Enter the number of minutes: ");
        int minutes = getPositiveInteger(scanner);

        // User input for score
        System.out.print("Enter your score: ");
        double score = getValidScore(scanner);

        // Creating a StudySession with the user inputs and printing
        StudySession session = new StudySession(subject, topic, minutes, score);
        studySessions.add(session);
        System.out.println("Study session added successfully! You currently have " + studySessions.size() + " study session(s).");
    }

    // Method to get a positive integer from the user
    public static int getPositiveInteger(Scanner scanner){
        int minutes = 0; 
        boolean validMinutes = false;

        // Try-Catch to handle InputMismatchExceptions
         while(!validMinutes){
            try {
                minutes = scanner.nextInt();
                if(minutes <= 0){
                    System.out.println("Invalid Input: Please enter a positive integer.");
                } else {
                    scanner.nextLine(); // Consume the newline character
                    validMinutes = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid Input: Please enter an integer.");
                scanner.next();
            }
        }
        return minutes;
    }

    // Method to get a valid score between 0 and 100
    public static double getValidScore(Scanner scanner){
        double score = 0;
        boolean validScore = false;

        // Try-Catch to handle InputMismatchExceptions
        while (!validScore) {
            try {
                score = scanner.nextDouble();
                if (score < 0 || score > 100) {
                    System.out.println("Invalid input: Please enter a score between 0 and 100.");
                } else {
                    scanner.nextLine(); // Consume the newline character
                    validScore = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input: Please enter a valid double for the score.");
                scanner.next(); // Clear the invalid input
            }
        }
        return score;
    }

    public static String getNonEmptyString(Scanner scanner, String prompt) {
        String input = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input: Please enter a non-empty string.");
            } else {
                validInput = true;
            }
        }
        return input;
    }
}
