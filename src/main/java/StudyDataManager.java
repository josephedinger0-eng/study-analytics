import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class StudyDataManager {

    private static String FILE_PATH = "data/sessions.csv"; // Path to the CSV file for storing study sessions

    // Set the file path for the CSV file
    public static void setFilePath(String filePath) {
        FILE_PATH = filePath; 
    }

    /* 
    * Save the list of study sessions to a CSV file. Each session is written as a line in the file, with fields separated by commas.
    * The first line of the file contains the header with field names.
    */
    public static void saveSessions(ArrayList<StudySession> studySessions){

        try {
            Files.createDirectories(Paths.get("data")); // Ensure the "data" directory exists before writing the file
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                writer.write("date,subject,topic,minutes,score"); // Write the header line to the CSV file
                writer.newLine(); // Move to the next line after writing the header

                for (StudySession session : studySessions) {
                    String line = session.getDate() + "," + session.getSubject() + "," + session.getTopic() + "," + session.getMinutes() + "," + session.getScore();
                    writer.write(line); // Write each study session as a line in the CSV file
                    writer.newLine(); // Move to the next line after writing each session
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating data directory: " + e.getMessage()); // Handle any IO exceptions that occur during directory creation
        }
    }

    public static ArrayList<StudySession> loadSessions() {
        ArrayList<StudySession> studySessions = new ArrayList<>(); // Initialize an empty list to hold the loaded study sessions

        if(!Files.exists(Paths.get(FILE_PATH))) {
            return studySessions; // Return an empty list if the CSV file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            reader.readLine(); // Skip the header line in the CSV file
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split each line by commas to extract the study session data
                LocalDate date = LocalDate.parse(parts[0]); // Parse the date from the first part
                String subject = parts[1]; // Extract the subject from the second part
                String topic = parts[2]; // Extract the topic from the third part
                int minutes = Integer.parseInt(parts[3]); // Parse the minutes from the fourth part
                double score = Double.parseDouble(parts[4]); // Parse the score from the fifth part

                StudySession session = new StudySession(date, subject, topic, minutes, score); // Create a new StudySession object with the extracted details
                studySessions.add(session); // Add the newly created session to the list of study sessions
            }
        } catch (IOException e) {
            System.out.println("Error loading study sessions: " + e.getMessage());
        }
        return studySessions;
    }
}


