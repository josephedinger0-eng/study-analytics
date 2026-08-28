import java.util.ArrayList;
import java.util.HashMap;

public class StudyAnalytics {

    private ArrayList<StudySession> studySessions; // The list of study sessions to analyze

    // Constructor to initialize the StudyAnalytics object with a list of study sessions
    public StudyAnalytics(ArrayList<StudySession> studySessions) {
        this.studySessions = studySessions;
    }

    // Method to calculate the total study time in minutes across all study sessions
    public int getTotalMinutes() {
        int totalMinutes = 0;
        for (StudySession session : studySessions){
            totalMinutes += session.getMinutes();
        }
        return totalMinutes;
    }

    // Method to calculate the average score across all study sessions
    public double getAverageScore() {
        if (studySessions.isEmpty()) {
            return 0.0; // Return 0 if there are no study sessions to avoid division by zero
        }
        double totalScore = 0.0;
        for (StudySession session : studySessions) {
            totalScore += session.getScore();
        }
        return totalScore / studySessions.size(); // Calculate and return the average score
    }

    // Method to get a breakdown of total study time by subject
    public HashMap<String, Integer> getMinutesBySubject() {
        HashMap<String, Integer> subjectMinutes = new HashMap<>();
        for (StudySession session : studySessions) {
            String subject = session.getSubject();
            int minutes = session.getMinutes();
            if (subjectMinutes.containsKey(subject)) {
                subjectMinutes.put(subject, subjectMinutes.get(subject) + minutes);
            } else {
                subjectMinutes.put(subject, minutes);
            }
        }
        return subjectMinutes;
    }
}