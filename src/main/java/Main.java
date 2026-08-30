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
            System.out.println("4. Modify or delete a study session");
            System.out.println("5. Save & Exit");
            System.out.print("Please select an option (1-5): ");

            int choice = getMenuChoice(scanner); // Get the user's menu choice

            /*
             * This switch statement handles the user's choice and executes the corresponding action.
             */
            switch (choice) {
                case 1:
                    addStudySession(scanner, studySessions);
                    break;
                case 2:
                    displayStudySessions(studySessions, scanner);
                    break;
                case 3:
                    if(studySessions.isEmpty()) {
                        System.out.println("No study sessions recorded yet.");
                    } else {
                        displaySummary(analytics);
                    }   
                    break;
                case 4:
                    displayStudySessionsForEditing(studySessions, scanner);
                    break;
                case 5: 
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
         * This loop continues until a valid choice between 1 and 5 is entered.
         * It handles InputMismatchExceptions to ensure the user enters a valid integer.
         */
        while (!validChoice) {
            try {
                choice = scanner.nextInt(); // Attempt to read an integer from the user
                scanner.nextLine(); // Consume the newline character
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid input: Please select a number between 1 and 5.");
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
    public static void displaySummary(StudyAnalytics analytics) {
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

    // Method to display and sort study sessions
    public static void displayStudySessions(ArrayList<StudySession> studySessions, Scanner scanner){        
        if (studySessions.isEmpty()) {
            System.out.println("No study sessions recorded yet.");
            return;
        }

        System.out.println("How would you like to sort the sessions?");
        System.out.println("1. Sort by newest date");
        System.out.println("2. Sort by subject");

        int sortChoice = getSessionSortChoice(scanner);

        ArrayList<StudySession> sortedSessions = getSortedSessions(studySessions, sortChoice);

        for(int i = 0; i < sortedSessions.size(); i++){
            System.out.println(i + 1 + ". " + sortedSessions.get(i));
        }
    }

    // Method to sort by date
    public static ArrayList<StudySession> getSortedSessions(ArrayList<StudySession> studySessions, int choice) {
        
        ArrayList<StudySession> sortedSessions = new ArrayList<>(studySessions);

        switch(choice) {
            case 1: 
                sortedSessions.sort(Comparator.comparing(StudySession::getDate).reversed());
                break;
            case 2:
                sortedSessions.sort(Comparator.comparing(StudySession::getSubject));
                break;
        }
        return sortedSessions;
    } 

    /*
    * Ensure that the choice of sorting is either 1 or 2
    */
    public static int getSessionSortChoice(Scanner scanner){
        int choice = 0;
        boolean validChoice = false;

        while(!validChoice){
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if(!(choice == 1 || choice == 2)){
                    System.out.println("Invalid input: Please select either 1 or 2.");
                } else {
                    validChoice = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter either 1 or 2.");
                scanner.next();
            }
        }
        return choice;
    }

    /*
    * Displays every studySession and prompts user to modify/delete before passing off
    */
    public static void displayStudySessionsForEditing(ArrayList<StudySession> studySessions, Scanner scanner){
        
        // Ends method if there are no sessions
        if (studySessions.isEmpty()) {
            System.out.println("No study sessions recorded yet.");
            return;
        }

        // Allows user to choose sorting method
        System.out.println("How would you like to sort the sessions?");
        System.out.println("1. Sort by newest date");
        System.out.println("2. Sort by subject");

        int sortChoice = getSessionSortChoice(scanner); // Gets valid choice

        ArrayList<StudySession> sortedSessions = getSortedSessions(studySessions, sortChoice); // Sorts sessions into new ArrayList

        // Numbers sessions
        for(int i = 0; i < sortedSessions.size(); i++){
            System.out.println(i + 1 + ". " + sortedSessions.get(i));
        }
        

        // User chooses which session to edit
        System.out.println("Please select a session to modify or delete: ");
        int sessionIndex = getChosenSession(sortedSessions, scanner);
        int originalIndex = -1;

        // Maps the choice from the sorted array to the original array
        for(int i = 0; i < studySessions.size(); i++){
            if(sortedSessions.get(sessionIndex).equals(studySessions.get(i))){
                originalIndex = i;
                break;
            }
        }

        // Ends method if cannot find chosen session
        if(originalIndex == -1){
            System.out.println("Unable to find the selected session.");
            return;
        }

        // Prompts user to modify or delete
        System.out.println("1. Modify session");
        System.out.println("2. Delete session");
        System.out.println("3. Cancel");
        int choice = getEditChoice(scanner); // Ensures valid choice

        // Passess choice off to either modify, delete, or break
        switch (choice) {
            case 1:
                modifySession(originalIndex, studySessions, scanner);
                break;
            case 2:
                deleteSession(originalIndex, studySessions);
                break;
            case 3:
                break;
        }
    }

    /*
    * Method to get a user input that fits the arraylist of sessions 
    */
    public static int getChosenSession(ArrayList<StudySession> studySessions, Scanner scanner){
        int sessionIndex = 0;
        int length = studySessions.size();
        boolean validInput = false;

        while(!validInput){
            try {
                sessionIndex = scanner.nextInt();
                scanner.nextLine();
                if(sessionIndex < 1 || sessionIndex > length){
                    System.out.println("Invalid input. Please select an existing session.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter the number of the session you would like to select.");
            }
        }
        return sessionIndex - 1;
    }

    /*
    * Method to get a user choice between 1 and 3 to determine if they want to edit or end
    */
    public static int getEditChoice(Scanner scanner){
        int choice = 0;
        boolean validChoice = false;

        while(!validChoice){
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if(choice > 3 || choice < 1){
                    System.out.println("Invalid input: Please select either 1, 2, or 3.");
                } else {
                    validChoice = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                scanner.next();
            }
        }
        return choice;
    }

    /* 
    * Method to allow the user to modify a chosen quality of a session
    */
    public static void modifySession(int sessionIndex, ArrayList<StudySession> studySessions, Scanner scanner){
        // Prompts user to pick a quality
        System.out.println("Please select a quality to modify.");
        System.out.println("1. Modify date");
        System.out.println("2. Modify subject");
        System.out.println("3. Modify topic");
        System.out.println("4. Modify minutes");
        System.out.println("5. Modify score");
        System.out.println("6. Exit");

        int choice = getModificationChoice(scanner);

        // Swtich-case to modify based on the user choice
        switch (choice) {
            case 1:
                System.out.print("Select a new date (yyyy-mm-dd): ");
                LocalDate newDate = getValidDate(scanner);
                studySessions.get(sessionIndex).setDate(newDate);
                break;
            case 2:
                String newSubject = getNonEmptyString(scanner, "Select a new subject: ");
                studySessions.get(sessionIndex).setSubject(newSubject);
                break;
            case 3:
                String newTopic = getNonEmptyString(scanner, "Select a new topic: ");
                studySessions.get(sessionIndex).setTopic(newTopic);
                break;
            case 4:
                System.out.print("Select a new minute amount: ");
                int newMinutes = getPositiveInteger(scanner);
                studySessions.get(sessionIndex).setMinutes(newMinutes);
                break;
            case 5:
                System.out.print("Select a new score: ");
                double newScore = getValidScore(scanner);
                studySessions.get(sessionIndex).setScore(newScore);
                break;
            case 6:
                break;

        }
    }

    // Method to remove a session
    public static void deleteSession(int sessionIndex, ArrayList<StudySession> studySessions){
        studySessions.remove(sessionIndex);
    }

    //  Method to oversee the user choosing between 1 and 6
    public static int getModificationChoice(Scanner scanner){
        int choice = 0;
        boolean validChoice = false;

        while(!validChoice){
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if(choice > 6 || choice < 1){
                    System.out.println("Invalid input: Please select 1 - 6");
                } else {
                    validChoice = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter 1 - 6.");
                scanner.next();
            }
        }
        return choice;
    }
}
