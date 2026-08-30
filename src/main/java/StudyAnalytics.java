import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.time.LocalDate;

/* 
 * Class to analyze study sessions and provide various summaries.
 */
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
        return totalMinutes; // Return the total study time in minutes
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
        return subjectMinutes; // Return the HashMap containing total minutes by subject
    }

    // Method to get a breakdown of average scores by topic
    public HashMap<String, Double> getAverageScoreByTopic(){
        HashMap<String, Double> topicTotalScores = new HashMap<>();
        HashMap<String, Integer> topicCounts = new HashMap<>();

        for (StudySession session : studySessions) {
            String topic = session.getTopic();
            double score = session.getScore();

            if (topicTotalScores.containsKey(topic)) {
                topicTotalScores.put(topic, topicTotalScores.get(topic) + score);
                topicCounts.put(topic, topicCounts.get(topic) + 1);
            } else {
                topicTotalScores.put(topic, score);
                topicCounts.put(topic, 1);
            }
        }

        // Calculate average scores for each topic
        HashMap<String, Double> averageScores = new HashMap<>();
        for (String topic : topicTotalScores.keySet()) {
            double totalScore = topicTotalScores.get(topic);
            int count = topicCounts.get(topic);
            averageScores.put(topic, totalScore / count);
        }
        return averageScores; // Return the HashMap containing average scores by topic
    }

    // Method to get a breakdown of total study time by date
    public TreeMap<LocalDate, Integer> getMinutesByDate() {
        TreeMap<LocalDate, Integer> dateMinutes = new TreeMap<>();
        for (StudySession session : studySessions) {
            LocalDate date = session.getDate();
            int minutes = session.getMinutes();
            if (dateMinutes.containsKey(date)) {
                dateMinutes.put(date, dateMinutes.get(date) + minutes);
            } else {
                dateMinutes.put(date, minutes);
            }
        }
        return dateMinutes; // Return the TreeMap containing total minutes by date
    }

    // Method to calculate the longest streak of consecutive days with study sessions
    public int getLongestStreak(){
        if (studySessions.isEmpty()) {
            return 0; // Return 0 if there are no study sessions
        }

        TreeMap<LocalDate, Integer> dateMinutes = getMinutesByDate();
        int longestStreak = 1;
        int currentStreak = 1;
        LocalDate previousDate = null;

        for(LocalDate currentDate : dateMinutes.keySet()){
            if(previousDate != null){
                if(previousDate.plusDays(1).equals(currentDate)){
                    currentStreak++;
                } else {
                    longestStreak = Math.max(longestStreak, currentStreak); // Check at the end of the loop
                    currentStreak = 1;
                }
            }
            previousDate = currentDate;
        }
        
        longestStreak = Math.max(longestStreak, currentStreak);

        return longestStreak;
    }
}