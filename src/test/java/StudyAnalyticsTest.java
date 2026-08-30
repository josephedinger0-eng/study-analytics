import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class StudyAnalyticsTest {
    
    /*
     * Test the getTotalMinutes method
     */
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

        // Create empty ArrayList to test the edge case of no study sessions
        ArrayList<StudySession> emptySessions = new ArrayList<>();

        // Create a StudyAnalytics object with the sample sessions
        StudyAnalytics analytics = new StudyAnalytics(sessions);

        // Create a StudyAnalytics object with the empty list of sessions
        StudyAnalytics emptyAnalytics = new StudyAnalytics(emptySessions);

        // Calculate the total minutes and assert the expected value
        int totalMinutes = analytics.getTotalMinutes();
        assertEquals(135, totalMinutes); // 30 + 45 + 60 = 135
        
        // Calculate the total minutes for the empty list and assert the expected value
        assertEquals(0, emptyAnalytics.getTotalMinutes()); // No sessions, so total minutes should be 0
    }

    /*
     * Test the getAverageScore method
     */
    @Test
    public void testGetAverageScore() {
        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.now(), "Math", "Algebra", 30, 80);
        StudySession session2 = new StudySession(LocalDate.now(), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.now(), "History", "World War II", 60, 100);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);

        // Create empty ArrayList to test the edge case of no study sessions
        ArrayList<StudySession> emptySessions = new ArrayList<>();

        // Create a StudyAnalytics object with the sample sessions
        StudyAnalytics analytics = new StudyAnalytics(sessions);

        // Create a StudyAnalytics object with the empty list of sessions
        StudyAnalytics emptyAnalytics = new StudyAnalytics(emptySessions);

        // Calculate the average score and assert the expected value
        double averageScore = analytics.getAverageScore();
        assertEquals(90.00, averageScore, 0.001); // (80 + 90 + 100) / 3 = 90.00

        // Calculate the average score for the empty list and assert the expected value
        assertEquals(0.0, emptyAnalytics.getAverageScore(), 0.001); // No sessions, so average score should be 0.0
    }

    /*
     * Test the getMinutesBySubject method
     */
    @Test
    public void testGetMinutesBySubject() {
        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.now(), "Math", "Algebra", 30, 85);
        StudySession session2 = new StudySession(LocalDate.now(), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.now(), "Math", "Calculus", 60, 75);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);

        // Create empty ArrayList to test the edge case of no study sessions
        ArrayList<StudySession> emptySessions = new ArrayList<>();
        StudyAnalytics emptyAnalytics = new StudyAnalytics(emptySessions);

        /* 
         * Create a StudyAnalytics object with the sample sessions
         * and get the breakdown of total study time by subject
         */
        StudyAnalytics analytics = new StudyAnalytics(sessions);
        HashMap<String, Integer> minutesBySubject = analytics.getMinutesBySubject();

        // Calculate the total minutes for Math and assert the expected value
        int mathMinutes = minutesBySubject.get("Math");
        assertEquals(90, mathMinutes); // 30 + 60 = 90

        // Calculate the total minutes for Science and assert the expected value
        int scienceMinutes = minutesBySubject.get("Science");
        assertEquals(45, scienceMinutes); // Only one session of Science

        assertEquals(2, minutesBySubject.size()); // There should be two subjects: Math and Science
    
        // Calculate the total minutes for the empty list and assert the expected value
        assertTrue(emptyAnalytics.getMinutesBySubject().isEmpty()); // No sessions, so the HashMap should be empty
    }

    /*
     * Test the getAverageScoreByTopic method
     */
    @Test
    public void testGetAverageScoreByTopic() {
        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.now(), "Math", "Algebra", 30, 80);
        StudySession session2 = new StudySession(LocalDate.now(), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.now(), "Math", "Algebra", 60, 100);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);

        // Create empty ArrayList to test the edge case of no study sessions
        ArrayList<StudySession> emptySessions = new ArrayList<>();

        // Create a StudyAnalytics object with the sample sessions
        StudyAnalytics analytics = new StudyAnalytics(sessions);

        // Create a StudyAnalytics object with the empty list of sessions
        StudyAnalytics emptyAnalytics = new StudyAnalytics(emptySessions);

        // Get the breakdown of average scores by topic
        HashMap<String, Double> averageScoreByTopic = analytics.getAverageScoreByTopic();

        // Calculate the average score for Algebra and assert the expected value
        double algebraAverageScore = averageScoreByTopic.get("Algebra");
        assertEquals(90.0, algebraAverageScore, 0.001); // (80 + 100) / 2 = 90.0

        // Calculate the average score for Biology and assert the expected value
        double biologyAverageScore = averageScoreByTopic.get("Biology");
        assertEquals(90.0, biologyAverageScore, 0.001); // Only one session of Biology

        assertEquals(2, averageScoreByTopic.size()); // There should be two topics: Algebra and Biology
    
        // Calculate the average score for the empty list and assert the expected value
        assertTrue(emptyAnalytics.getAverageScoreByTopic().isEmpty()); // No sessions, so the HashMap should be empty
    }

    @Test
    public void testGetMinutesByDate() { 
        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.now(), "Math", "Algebra", 30, 80);
        StudySession session2 = new StudySession(LocalDate.now(), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.of(2026, 8, 1), "Math", "Algebra", 60, 100);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);

        // Create empty ArrayList to test the edge case of no study sessions
        ArrayList<StudySession> emptySessions = new ArrayList<>();

        // Create a StudyAnalytics object with the sample sessions
        StudyAnalytics analytics = new StudyAnalytics(sessions);

        // Create a StudyAnalytics object with the empty list of sessions
        StudyAnalytics emptyAnalytics = new StudyAnalytics(emptySessions);

        // Get the breakdown of total study time by date
        TreeMap<LocalDate, Integer> minutesByDate = analytics.getMinutesByDate();

        // Calculate the total minutes for today and assert the expected value
        assertEquals(75, minutesByDate.get(LocalDate.now())); // 30 + 45 = 75
        assertEquals(60, minutesByDate.get(LocalDate.of(2026, 8, 1))); // Only one session on this date
        assertEquals(2, minutesByDate.size()); // There should be two dates in the TreeMap
        assertTrue(emptyAnalytics.getMinutesByDate().isEmpty()); // No sessions, so the TreeMap should be empty
    }

    @Test
    public void testGetLongestStreak(){
        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.of(2026, 8, 1), "Math", "Algebra", 30, 80);
        StudySession session2 = new StudySession(LocalDate.of(2026, 8, 2), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.of(2026, 8, 3), "Math", "Algebra", 60, 100);
        StudySession session4 = new StudySession(LocalDate.of(2026, 8, 5), "History", "World War II", 30, 85);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);
        sessions.add(session4);

        // Create empty ArrayList to test the edge case of no study sessions
        ArrayList<StudySession> emptySessions = new ArrayList<>();

        // Create a StudyAnalytics object with the sample sessions
        StudyAnalytics analytics = new StudyAnalytics(sessions);

        // Create a StudyAnalytics object with the empty list of sessions
        StudyAnalytics emptyAnalytics = new StudyAnalytics(emptySessions);

        // Calculate the longest study streak and assert the expected value
        int longestStreak = analytics.getLongestStreak();
        assertEquals(3, longestStreak); // The longest streak is from Aug 1 to Aug 3 (3 days)
        assertEquals(0, emptyAnalytics.getLongestStreak()); // No sessions, so longest streak should be 0
    }

    /*
     * Test the saveSessions and loadSessions methods
     */
    @Test 
    public void testSaveAndLoadSessions() {

        StudyDataManager.setFilePath("data/test-sessions.csv"); // Set a test file path for saving and loading sessions

        // Create some sample study sessions
        StudySession session1 = new StudySession(LocalDate.of(2026, 8, 1), "Math", "Algebra", 30, 80);
        StudySession session2 = new StudySession(LocalDate.of(2026, 8, 2), "Science", "Biology", 45, 90);
        StudySession session3 = new StudySession(LocalDate.of(2026, 8, 3), "Math", "Algebra", 60, 100);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);

        // Save the sessions to a CSV file
        StudyDataManager.saveSessions(sessions);

        // Load the sessions from the CSV file
        ArrayList<StudySession> loadedSessions = StudyDataManager.loadSessions();

        // Assert that the loaded sessions match the original sessions
        assertEquals(sessions.size(), loadedSessions.size());
        for (int i = 0; i < sessions.size(); i++) {
            assertEquals(sessions.get(i).getDate(), loadedSessions.get(i).getDate());
            assertEquals(sessions.get(i).getSubject(), loadedSessions.get(i).getSubject());
            assertEquals(sessions.get(i).getTopic(), loadedSessions.get(i).getTopic());
            assertEquals(sessions.get(i).getMinutes(), loadedSessions.get(i).getMinutes());
            assertEquals(sessions.get(i).getScore(), loadedSessions.get(i).getScore(), 0.001);
        }
    }

    /*
     * Test the loadSessions method when the file does not exist
     */
    @Test
    public void testLoadSessionsWhenFileDoesNotExist() {
        StudyDataManager.setFilePath("data/nonexistent-sessions.csv"); // Set a file path that does not exist

        // Load the sessions from the non-existent CSV file
        ArrayList<StudySession> loadedSessions = StudyDataManager.loadSessions();

        // Assert that the loaded sessions list is empty
        assertTrue(loadedSessions.isEmpty());
    }

    /*
     * Test the saveSessions and loadSessions methods with subjects and topics containing commas
     */
    @Test
    public void testSaveAndLoadSessionsWithCommas(){
        
        StudyDataManager.setFilePath("data/test-sessions.csv"); // Set a test file path for saving and loading sessions

        // Create some sample study sessions with commas in the subject and topic
        StudySession session1 = new StudySession(LocalDate.of(2026, 8, 1), "Math, Advanced", "Algebra, Linear Equations", 30, 80);
        StudySession session2 = new StudySession(LocalDate.of(2026, 8, 2), "Science", "Biology, Cell Structure", 45, 90);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);

        // Save the sessions to a CSV file
        StudyDataManager.saveSessions(sessions);

        // Load the sessions from the CSV file
        ArrayList<StudySession> loadedSessions = StudyDataManager.loadSessions();

        // Assert that the loaded sessions match the original sessions
        assertEquals(sessions.size(), loadedSessions.size());
        for (int i = 0; i < sessions.size(); i++) {
            assertEquals(sessions.get(i).getDate(), loadedSessions.get(i).getDate());
            assertEquals(sessions.get(i).getSubject(), loadedSessions.get(i).getSubject());
            assertEquals(sessions.get(i).getTopic(), loadedSessions.get(i).getTopic());
            assertEquals(sessions.get(i).getMinutes(), loadedSessions.get(i).getMinutes());
            assertEquals(sessions.get(i).getScore(), loadedSessions.get(i).getScore(), 0.001);
        }
    }

    /*
     * Test the parseCSVLine method to ensure it correctly handles fields with commas
     */
    @Test
    public void testParseCSVLine() {
        String line = "2026-08-01,\"Math, Advanced\",\"Algebra, Linear Equations\",30,80.0";
        String[] expectedParts = {"2026-08-01", "Math, Advanced", "Algebra, Linear Equations", "30", "80.0"};
        String[] actualParts = StudyDataManager.parseCSVLine(line);
        assertArrayEquals(expectedParts, actualParts);
    }

    /*
     * Test the loadSessions method to ensure it skips malformed rows
     */
    @Test
    public void testLoadSessionsSkipsMalformedRows(){
        
        StudyDataManager.setFilePath("data/test-sessions.csv"); // Set a test file path for saving and loading sessions

        // Create some sample study sessions, including a malformed row
        StudySession session1 = new StudySession(LocalDate.of(2026, 8, 1), "Math", "Algebra", 30, 80);
        StudySession session2 = new StudySession(LocalDate.of(2026, 8, 2), "Science", "Biology", 45, 90);

        // Add the sessions to a list
        ArrayList<StudySession> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);

        // Save the sessions to a CSV file
        StudyDataManager.saveSessions(sessions);

        // Manually add a malformed row to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/test-sessions.csv", true))) {
            writer.write("malformed,row,with,missing,fields");
            writer.newLine();
        } catch (IOException e) {
            fail("Failed to write malformed row to test file: " + e.getMessage());
        }

        // Load the sessions from the CSV file
        ArrayList<StudySession> loadedSessions = StudyDataManager.loadSessions();

        // Assert that the loaded sessions match the original valid sessions and that the malformed row was skipped
        assertEquals(sessions.size(), loadedSessions.size());
        for (int i = 0; i < sessions.size(); i++) {
            assertEquals(sessions.get(i).getDate(), loadedSessions.get(i).getDate());
            assertEquals(sessions.get(i).getSubject(), loadedSessions.get(i).getSubject());
            assertEquals(sessions.get(i).getTopic(), loadedSessions.get(i).getTopic());
            assertEquals(sessions.get(i).getMinutes(), loadedSessions.get(i).getMinutes());
            assertEquals(sessions.get(i).getScore(), loadedSessions.get(i).getScore(), 0.001);
        }
    }

    /*
     * Clean up the test file after each test
     */
    @AfterEach
    public void cleanUp() {
        try {
            Files.deleteIfExists(Paths.get("data/test-sessions.csv")); // Clean up the test file after each test
            Files.deleteIfExists(Paths.get("data/nonexistent-sessions.csv")); // Clean up the non-existent test file after each test
        } catch (IOException e) {
            System.out.println("Error cleaning up test file: " + e.getMessage());
        }
        StudyDataManager.setFilePath("data/sessions.csv"); // Reset the file path to the default after each test
    }
}
