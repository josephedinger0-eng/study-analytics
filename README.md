# Study Session Tracker

A Java console application for recording, managing, and analyzing study sessions. The application allows users to track study time, subjects, topics, and performance scores while providing useful summaries and sorting options.

## Features

* Add new study sessions
* View all recorded study sessions
* Sort sessions by:

  * Newest date
  * Subject alphabetically
* Modify existing study sessions

  * Date
  * Subject
  * Topic
  * Study time
  * Score
* Delete study sessions
* Calculate total study time
* Calculate average study score
* Calculate study time by subject
* Calculate average score by topic
* Calculate study time by date
* Calculate the longest consecutive study streak
* Automatically use today's date when no date is entered
* Validate user input
* Save study sessions to a CSV file
* Load saved sessions when the application starts
* Handle malformed CSV rows without crashing
* Support CSV fields containing commas

## Technologies

* **Java**
* **Maven**
* **JUnit**
* **CSV file storage**
* Java Collections Framework
* `LocalDate` for date handling

## Project Structure

```text
study-analytics/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Main.java
│   │       ├── StudySession.java
│   │       ├── StudyAnalytics.java
│   │       └── StudyDataManager.java
│   │
│   └── test/
│       └── java/
│           └── StudyAnalyticsTest.java
│
├── data/
│   └── sessions.csv
│
├── pom.xml
└── README.md
```

> The `data` directory and `sessions.csv` file are created automatically when study sessions are saved.

## How It Works

When the application starts, previously saved study sessions are loaded from:

```text
data/sessions.csv
```

The user is presented with a menu:

```text
1. Add a new study session
2. View all study sessions
3. View study session summary
4. Edit or delete a study session
5. Save & Exit
```

### Adding a Study Session

Users enter:

* Subject
* Topic
* Number of minutes studied
* Score from 0–100
* Date

Dates are entered using:

```text
YYYY-MM-DD
```

If the user presses Enter without entering a date, the current date is used.

The application validates the entered information and continues prompting until valid input is provided.

### Viewing Sessions

Sessions can be displayed in two different ways:

1. **Newest date first**
2. **Subject alphabetically**

Sorting creates a separate list, so the original list of study sessions is not modified.

### Editing Sessions

Users can select an existing session and choose which property to modify:

```text
1. Modify date
2. Modify subject
3. Modify topic
4. Modify minutes
5. Modify score
6. Exit
```

The same input validation used when creating sessions is also applied when modifying them.

### Deleting Sessions

Users can select a session from the displayed, sorted list and delete it.

The application keeps track of the session's position in the original list so that sorting does not cause the wrong session to be modified or deleted.

## Study Analytics

The application provides several useful statistics.

### Total Study Time

Calculates the total number of minutes across all recorded sessions.

### Average Score

Calculates the average score across all study sessions.

### Study Time by Subject

Groups study time by subject.

Example:

```text
Math: 120 minutes
Science: 75 minutes
English: 45 minutes
```

### Average Score by Topic

Calculates the average score for each topic.

### Study Time by Date

Groups the total amount of study time by date.

### Longest Study Streak

Calculates the longest sequence of consecutive days on which at least one study session was recorded.

For example, studying on:

```text
August 1
August 2
August 3
August 5
August 6
```

would produce a longest streak of:

```text
3 days
```

## Data Storage

Study sessions are stored locally in a CSV file:

```text
data/sessions.csv
```

The CSV uses the following fields:

```text
date,subject,topic,minutes,score
```

Example:

```text
date,subject,topic,minutes,score
2026-08-01,"Math","Algebra","30","80.0"
2026-08-02,"Science","Biology","45","90.0"
```

The application includes custom CSV parsing so that subjects and topics containing commas can still be loaded correctly.

For example:

```text
2026-08-01,"Math, Advanced","Algebra, Linear Equations",30,80.0
```

is correctly interpreted as five fields rather than being incorrectly split at every comma.

Malformed rows are skipped instead of causing the entire application to fail.

## Input Validation

The application validates user input for several situations.

### Menu Choices

The user must select a valid menu option.

### Study Time

Study time must be a positive integer.

### Score

Scores must be between:

```text
0 and 100
```

Both integers and decimal values are supported.

### Text Fields

Subjects and topics cannot be empty.

### Dates

Dates must use the `YYYY-MM-DD` format and must represent a valid `LocalDate`.

### Session Selection

When selecting a session for editing or deletion, the user must select a valid session number.

Invalid input is rejected and the user is prompted again.

## Testing

The project uses **JUnit** for automated testing.

The test suite covers functionality including:

* Saving and loading sessions
* Loading when the CSV file does not exist
* Parsing CSV fields containing commas
* Skipping malformed CSV rows
* Calculating total study minutes
* Calculating average scores
* Calculating minutes by subject
* Calculating average scores by topic
* Calculating minutes by date
* Calculating longest study streaks
* Validating menu choices
* Validating scores
* Validating non-empty strings
* Validating dates
* Sorting sessions by date
* Sorting sessions by subject
* Ensuring sorting does not modify the original list
* Selecting sessions for editing
* Modifying dates
* Modifying subjects
* Modifying topics
* Modifying study minutes
* Modifying scores
* Deleting sessions
* Handling empty session lists
* Testing the complete sorted-session deletion workflow

Run the test suite with:

```bash
mvn test
```

For a clean build and test:

```bash
mvn clean test
```

## Running the Application

### Prerequisites

You will need:

* Java JDK
* Maven

### Clone the Repository

```bash
git clone https://github.com/josephedinger0-eng/study-analytics.git
```

Navigate into the project:

```bash
cd study-analytics
```

### Run the Tests

```bash
mvn clean test
```

### Run the Application

If your Maven configuration includes the appropriate execution setup, run the application through Maven.

Otherwise, compile the project and run the `Main` class through your Java IDE.

The application starts from:

```text
Main.java
```

## Design

The project is divided into several classes, each with a specific responsibility.

### `Main`

Handles:

* User interaction
* Console input
* Menu navigation
* Input validation
* Session display
* Sorting
* Editing
* Deleting

### `StudySession`

Represents an individual study session and stores:

* Date
* Subject
* Topic
* Minutes
* Score

### `StudyAnalytics`

Performs calculations and generates study statistics.

### `StudyDataManager`

Handles persistent storage by:

* Saving sessions to CSV
* Loading sessions from CSV
* Parsing CSV data
* Handling malformed rows
* Managing the data directory

## Error Handling

The application is designed to handle invalid user input without terminating unexpectedly.

It also handles common file-related problems using Java's `IOException` handling.

When loading CSV data, invalid rows are skipped so that one corrupted record does not prevent valid study sessions from being loaded.

## Future Improvements

Possible future improvements include:

* More advanced filtering options
* Additional study statistics
* Graphical user interface
* Database-based storage
* Exporting reports
* More detailed progress tracking
* Improved CSV escaping and formatting
* More advanced analytics and visualizations

These features are intentionally outside the current core scope of the project.

## Project Status

**Core functionality complete.**

The application currently provides a functional system for recording, managing, persisting, and analyzing study sessions, with automated tests covering the major application behaviors.

The project is currently focused on reliability, testing, and code quality rather than adding unnecessary features.

## License

This project is currently intended as a personal/educational project.
