Study Analytics

A Java-based study tracking application that records study sessions and analyzes study habits and performance.

Features
Record study sessions with:
Date
Subject
Topic
Study time
Score
Validate user input and handle invalid entries
View all recorded study sessions
Calculate total study time
Calculate overall average score
Analyze study time by subject
Analyze average performance by topic
Analyze study time by date
Calculate the longest consecutive study streak
Technologies
Java 21
ArrayList
HashMap
TreeMap
LocalDate
Exception handling
Git / GitHub
Project Structure
src/
├── Main.java
├── StudySession.java
└── StudyAnalytics.java
Main.java

Handles the user interface, menu system, input validation, and creation of study sessions.

StudySession.java

Represents an individual study session and stores its date, subject, topic, study time, and score.

StudyAnalytics.java

Contains methods for analyzing the stored study sessions, including subject, topic, date, and study streak analytics.

Example
Welcome to the Study Session Tracker!
1. Add a new study session
2. View all study sessions
3. View study summary
4. Quit

Study Summary
-------------------------
Total Study Time: 315 minutes
Average Score: 88.3
Longest Study Streak: 3 day(s)

Study Time by Subject:
  Computer Science: 180 minutes
  Mathematics: 135 minutes

Average Score by Topic:
  Data Structures: 91.5
  Algebra: 85.0
Running the Program

Clone the repository and navigate to the project directory.

Compile the Java source files:

javac src/*.java

Run the program:

java -cp src Main
Future Improvements

Planned improvements include:

Persistent storage of study sessions
Automated testing with JUnit
Additional study and performance analytics
Improved presentation of dates and statistics
Potential graphical user interface
