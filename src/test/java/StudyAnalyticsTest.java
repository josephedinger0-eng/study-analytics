import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.time.LocalDate;


public class StudyAnalyticsTest {
    
    @Test
    public void testGetTotalMinutes() {
        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.now(), "Math", "Algebra", 30, 85);
        StudySession session2 = new StudySession(LocalDate.now(), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.now(), "History", "World War II", 60, 75);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);

        // Create a StudyAnalytics object with the sample sessions
        StudyAnalytics analytics = new StudyAnalytics(sessions);

        // Calculate the total minutes and assert the expected value
        int totalMinutes = analytics.getTotalMinutes();
        assertEquals(135, totalMinutes); // 30 + 45 + 60 = 135
    }
}
