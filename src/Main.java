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
        System.out.print("Enter your score: ");
		double score = scanner.nextDouble();

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
