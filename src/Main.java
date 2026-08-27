import java.util.Scanner;
import java.util.InputMismatchException;
public class Main {
	public static void main(String[] args) {

        // Creating objects and variables
        Scanner scanner = new Scanner(System.in);

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
        System.out.println("Study Session Details:");
        System.out.println("----------------------");
        System.out.println("Subject: " + session.getSubject());
        System.out.println("Topic: " + session.getTopic());
        System.out.println("Minutes: " + session.getMinutes());
        System.out.println("Score: " + session.getScore());

        scanner.close();

	}
}
