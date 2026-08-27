public class Main {
	public static void main(String[] args) {
		StudySession session = new StudySession("CS", "Data Structures", 60, 85.5);
        System.out.println("Study Session Details:" + "\n-------------------------");
        System.out.println("Subject: " + session.getSubject());
        System.out.println("Topic: " + session.getTopic());
        System.out.println("Minutes: " + session.getMinutes());
        System.out.println("Score: " + session.getScore());
	}
}
