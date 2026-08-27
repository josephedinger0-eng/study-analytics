/**
 * Represents a single study session and its associated performance data.
 */
public class StudySession {
    private String subject;
    private String topic;
    private int minutes;
    private double score;

    public StudySession(String subject, String topic, int minutes, double score) {
        this.subject = subject;
        this.topic = topic;
        this.minutes = minutes;
        this.score = score;
    }

    // Getters and setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String toString() {
        return "StudySession: " +
                "subject = '" + subject + '\'' +
                ", topic = '" + topic + '\'' +
                ", minutes = " + minutes +
                ", score = " + score +
                '}';
    }
}