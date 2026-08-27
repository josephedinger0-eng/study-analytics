import java.util.Scanner;
public class Main {
	public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your subject: ");
		String subject = scanner.nextLine();

        System.out.print("Enter your topic: ");
		String topic = scanner.nextLine();

        System.out.print("Enter the number of minutes: ");
		int minutes = scanner.nextInt();
        while (minutes < 0) {
            System.out.println("Invalid number of minutes. Please enter a non-negative value.");
            System.out.print("Enter the number of minutes: ");
            minutes = scanner.nextInt();
        }

        System.out.print("Enter your score: ");
        double score = scanner.nextDouble();
        while (score < 0 || score > 100) {
            System.out.println("Invalid score. Please enter a score between 0 and 100.");
            System.out.print("Enter your score: ");
            score = scanner.nextDouble();
        }

        StudySession session = new StudySession(subject, topic, minutes, score);
        System.out.print("Study Session Details:");
        System.out.println("----------------------");
        System.out.println("Subject: " + session.getSubject());
        System.out.println("Topic: " + session.getTopic());
        System.out.println("Minutes: " + session.getMinutes());
        System.out.println("Score: " + session.getScore());

        scanner.close();

	}
}
