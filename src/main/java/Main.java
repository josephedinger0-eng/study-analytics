import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

/* 
 * Main class for the Study Session Tracker application.
 */
public class Main {
	public static void main(String[] args) {

        // Creating objects and variables
        Scanner scanner = new Scanner(System.in);
        ArrayList<StudySession> studySessions = StudyDataManager.loadSessions(); // Load existing study sessions from CSV
        StudyAnalytics analytics = new StudyAnalytics(studySessions);
        boolean running = true;

        /*
         * Main application loop
         */
        while(running) {
            System.out.println("\nWelcome to the Study Session Tracker!");
            System.out.println("1. Add a new study session");
            System.out.println("2. View all study sessions");
            System.out.println("3. View study session summary");
            System.out.println("4. Save & Exit");
            System.out.print("Please select an option (1-4): ");

            int choice = getMenuChoice(scanner); // Get the user's menu choice

            /*
             * This switch statement handles the user's choice and executes the corresponding action.
             */
            switch (choice) {
                case 1:
                    addStudySession(scanner, studySessions);
                    break;
                case 2:
                    displayStudySessions(studySessions);
                    break;
                case 3:
                    if(studySessions.isEmpty()) {
                        System.out.println("No study sessions recorded yet.");
                    } else {
                        displaySummary(analytics, studySessions);
                    }   
                    break;
                case 4: 
                    StudyDataManager.saveSessions(studySessions); // Save study sessions to CSV
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
        LocalDate date = getValidDate(scanner);

        StudySession session = new StudySession(date, subject, topic, minutes, score);
        studySessions.add(session);
        System.out.println("Study session added successfully! You currently have " + studySessions.size() + " study session(s).");
    }

    // Method to get a positive integer from the user
    public static int getPositiveInteger(Scanner scanner){
        int minutes = 0; 
        boolean validMinutes = false;

        /* 
            * This loop continues until a valid positive integer is entered.
            * It handles InputMismatchExceptions to ensure the user enters a valid integer.
        */
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

        /* 
            * This loop continues until a valid score between 0 and 100 is entered.
            * It handles InputMismatchExceptions to ensure the user enters a valid double.
        */
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
        return score; // Return the valid score
    }

    // Method to get a non-empty string from the user
    public static String getNonEmptyString(Scanner scanner, String prompt) {
        String input = ""; // Initialize input variable
        boolean validInput = false; // Flag to check if the input is valid

        /*
         * This loop continues until a valid non-empty string is entered.
         */
        while (!validInput) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input: Please enter a non-empty string.");
            } else {
                validInput = true;
            }
        }
        return input; // Return the valid non-empty string
    }

    // Method to get a valid date from the user
    public static LocalDate getValidDate(Scanner scanner) {
        LocalDate date = null; // Initialize date variable
        boolean validDate = false; // Flag to check if the date is valid

        while(!validDate){
            System.out.print("Enter the date (YYYY-MM-DD), or press Enter for today's date: ");
            String input = scanner.nextLine().trim();
            if(!input.isEmpty()){
                try {
                    date = LocalDate.parse(input); // Attempt to parse the input string to a LocalDate
                    validDate = true; // If parsing is successful, set validDate to true
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            } else {
                return LocalDate.now(); // If the input is empty, return the current date
            }
        }
        return date;
    }

    // Method to get a valid menu choice from the user
    public static int getMenuChoice(Scanner scanner) {
        int choice = 0; // Initialize choice variable
        boolean validChoice = false; // Flag to check if the choice is valid

        /*
         * This loop continues until a valid choice between 1 and 4 is entered.
         * It handles InputMismatchExceptions to ensure the user enters a valid integer.
         */
        while (!validChoice) {
            try {
                choice = scanner.nextInt(); // Attempt to read an integer from the user
                scanner.nextLine(); // Consume the newline character
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid input: Please select a number between 1 and 4.");
                } else {
                    validChoice = true; // If the choice is valid, set validChoice to true
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input: Please enter an integer.");
                scanner.next(); // Clear the invalid input
            }
        }
        return choice; // Return the valid menu choice
    }

    // Method to display a summary of all study sessions
    public static void displaySummary(StudyAnalytics analytics, ArrayList<StudySession> studySessions) {
        int totalMinutes = analytics.getTotalMinutes();
        double averageScore = analytics.getAverageScore();
        HashMap<String, Integer> subjectMinutes = analytics.getMinutesBySubject();
        HashMap<String, Double> topicAverageScores = analytics.getAverageScoreByTopic();
        TreeMap<LocalDate, Integer> dateMinutes = analytics.getMinutesByDate();
        int longestStreak = analytics.getLongestStreak();
        
        System.out.println("\nStudy Summary");
        System.out.println("-------------------------");
        
        System.out.println("\nTotal Study Time: " + totalMinutes + " minutes");
        System.out.println("Longest Study Streak: " + longestStreak + " days");
        System.out.printf("Average Score: %.1f%n", averageScore);
        
        System.out.println("\nStudy Time by Subject:");
        for (String subjectA : subjectMinutes.keySet()) {
            System.out.println("  " + subjectA + ": " + subjectMinutes.get(subjectA) + " minutes");
        }
        
        System.out.println("\nAverage Scores by Topic:");
        for(String topicA : topicAverageScores.keySet()) {
            System.out.printf("  %s: %.1f%n", topicA, topicAverageScores.get(topicA));
        }

        System.out.println("\nStudy Time by Date:");
        for (LocalDate dateA : dateMinutes.keySet()) {
            System.out.println("  " + dateA + ": " + dateMinutes.get(dateA) + " minutes");
        }
    }

    // Method to sort study session by date
    public static void displayStudySessions(ArrayList<StudySession> studySessions){
        if (studySessions.isEmpty()) {
            System.out.println("No study sessions recorded yet.");
            return;
        }
        ArrayList<StudySession> sortedSessions = getSortedSessions(studySessions);

        for(StudySession session : sortedSessions){
            System.out.println(session);
        }
    }

    // Method to sort by date
    public static ArrayList<StudySession> getSortedSessions(ArrayList<StudySession> studySessions) {
        ArrayList<StudySession> sortedSessions = new ArrayList<>(studySessions);
        sortedSessions.sort(Comparator.comparing(StudySession::getDate).reversed());
        return sortedSessions;
    } 
}
