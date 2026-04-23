package com.example.quizapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quizzy.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_QUESTIONS = "questions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";
    private static final String COLUMN_OPTION4 = "option4";
    private static final String COLUMN_CORRECT_ANSWER = "correctAnswer";
    private static final String COLUMN_CORRECT_ANSWER_TEXT = "correctAnswerText";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_QUESTION_TYPE = "questionType";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_OPTION4 + " TEXT, " +
                COLUMN_CORRECT_ANSWER + " INTEGER, " +
                COLUMN_CORRECT_ANSWER_TEXT + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_QUESTION_TYPE + " TEXT)";
        db.execSQL(createTable);
        insertDefaultQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    private void insertDefaultQuestions(SQLiteDatabase db) {
        // ========== PROGRAMMING ==========
        addQuestion(db, new Question("What does HTML stand for?",
                "Hyper Text Markup Language", "Hyperlinks Text Markup Language",
                "Home Tool Markup Language", "None of these", 1, "Programming"));
        addQuestion(db, new Question("Which language is used for styling web pages?",
                "HTML", "JQuery", "CSS", "XML", 3, "Programming"));
        addQuestion(db, new Question("Java is a compiled language.", true, "Programming"));
        addQuestion(db, new Question("What is the extension of Java code files?",
                ".java", "Programming"));
        addQuestion(db, new Question("What does OOP stand for?",
                "Object Oriented Programming", "Object Oriented Protocol",
                "Open Object Protocol", "None of these", 1, "Programming"));

        // ========== ADVANCED PROGRAMMING ==========
        addQuestion(db, new Question("What is polymorphism in OOP?",
                "Multiple inheritance", "Method overloading and overriding",
                "Data hiding", "Code reuse", 2, "Advanced Programming"));
        addQuestion(db, new Question("An abstract class can be instantiated directly.",
                false, "Advanced Programming"));
        addQuestion(db, new Question("What design pattern ensures only one instance of a class?",
                "Singleton", "Advanced Programming"));
        addQuestion(db, new Question("Which keyword is used for inheritance in Java?",
                "implements", "extends", "inherits", "super", 2, "Advanced Programming"));
        addQuestion(db, new Question("What is the time complexity of binary search?",
                "O(n)", "O(n²)", "O(log n)", "O(1)", 3, "Advanced Programming"));

        // ========== DATA STRUCTURE ==========
        addQuestion(db, new Question("Which data structure uses FIFO?",
                "Stack", "Queue", "Tree", "Graph", 2, "Data Structure"));
        addQuestion(db, new Question("A stack follows the LIFO principle.",
                true, "Data Structure"));
        addQuestion(db, new Question("What is the worst-case complexity of Quick Sort?",
                "O(n log n)", "O(n)", "O(n²)", "O(log n)", 3, "Data Structure"));
        addQuestion(db, new Question("The data structure used for BFS traversal is called a ____.",
                "Queue", "Data Structure"));
        addQuestion(db, new Question("Which data structure is used for recursion?",
                "Queue", "Array", "Stack", "Linked List", 3, "Data Structure"));

        // ========== MACHINE LEARNING ==========
        addQuestion(db, new Question("What does CNN stand for in deep learning?",
                "Central Neural Network", "Convolutional Neural Network",
                "Connected Neural Network", "Computer Neural Network", 2, "Machine Learning"));
        addQuestion(db, new Question("Supervised learning requires labeled data.",
                true, "Machine Learning"));
        addQuestion(db, new Question("Which algorithm is used for classification?",
                "Linear Regression", "K-Means", "Decision Tree", "PCA", 3, "Machine Learning"));
        addQuestion(db, new Question("The technique of reducing overfitting by adding a penalty term is called ____.",
                "Regularization", "Machine Learning"));
        addQuestion(db, new Question("What is the purpose of an activation function?",
                "To store data", "To introduce non-linearity",
                "To reduce dimensions", "To normalize input", 2, "Machine Learning"));

        // ========== DATABASE ==========
        addQuestion(db, new Question("What does SQL stand for?",
                "Structured Query Language", "Simple Query Language",
                "Standard Query Logic", "Sequential Query Language", 1, "Database"));
        addQuestion(db, new Question("A primary key can contain NULL values.",
                false, "Database"));
        addQuestion(db, new Question("Which SQL command is used to retrieve data?",
                "GET", "FETCH", "SELECT", "RETRIEVE", 3, "Database"));
        addQuestion(db, new Question("The process of organizing data to reduce redundancy is called ____.",
                "Normalization", "Database"));
        addQuestion(db, new Question("Which type of join returns all rows from both tables?",
                "INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL OUTER JOIN", 4, "Database"));

        // ========== SOFTWARE DEVELOPMENT ==========
        addQuestion(db, new Question("What does SDLC stand for?",
                "Software Development Life Cycle", "System Design Life Cycle",
                "Software Debugging Life Cycle", "System Development Logic Cycle", 1, "Software Development"));
        addQuestion(db, new Question("Agile methodology uses iterative development.",
                true, "Software Development"));
        addQuestion(db, new Question("Which model is also known as the linear sequential model?",
                "Agile", "Waterfall", "Spiral", "V-Model", 2, "Software Development"));
        addQuestion(db, new Question("A visual representation of a system's workflow is called a ____.",
                "Flowchart", "Software Development"));
        addQuestion(db, new Question("What is the purpose of version control?",
                "Track code changes", "Debug code", "Compile code", "Deploy code", 1, "Software Development"));

        // ========== SOFTWARE TESTING ==========
        addQuestion(db, new Question("What is black-box testing?",
                "Testing without seeing code", "Testing with source code",
                "Performance testing", "Security testing", 1, "Software Testing"));
        addQuestion(db, new Question("Unit testing tests individual components of software.",
                true, "Software Testing"));
        addQuestion(db, new Question("Which testing level tests the complete system?",
                "Unit Testing", "Integration Testing",
                "System Testing", "Acceptance Testing", 3, "Software Testing"));
        addQuestion(db, new Question("Testing done without executing the code is called ____ testing.",
                "Static", "Software Testing"));
        addQuestion(db, new Question("What type of testing checks non-functional requirements?",
                "Functional Testing", "Performance Testing",
                "Unit Testing", "Regression Testing", 2, "Software Testing"));

        // ========== STATISTICS ==========
        addQuestion(db, new Question("What is the mean of 2, 4, 6, 8, 10?",
                "5", "6", "7", "8", 2, "Statistics"));
        addQuestion(db, new Question("The median is always equal to the mean.",
                false, "Statistics"));
        addQuestion(db, new Question("Which measure of central tendency is most affected by outliers?",
                "Mean", "Median", "Mode", "Range", 1, "Statistics"));
        addQuestion(db, new Question("The measure of spread that uses squared deviations from the mean is called ____.",
                "Variance", "Statistics"));
        addQuestion(db, new Question("What does a p-value less than 0.05 typically indicate?",
                "No significance", "Statistical significance",
                "Random error", "Large sample size", 2, "Statistics"));
    }

    private void addQuestion(SQLiteDatabase db, Question q) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, q.getQuestion());
        cv.put(COLUMN_OPTION1, q.getOption1());
        cv.put(COLUMN_OPTION2, q.getOption2());
        cv.put(COLUMN_OPTION3, q.getOption3());
        cv.put(COLUMN_OPTION4, q.getOption4());
        cv.put(COLUMN_CORRECT_ANSWER, q.getCorrectAnswer());
        cv.put(COLUMN_CORRECT_ANSWER_TEXT, q.getCorrectAnswerText());
        cv.put(COLUMN_CATEGORY, q.getCategory());
        cv.put(COLUMN_QUESTION_TYPE, q.getQuestionType());
        db.insert(TABLE_QUESTIONS, null, cv);
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_CATEGORY + " = ?",
                new String[]{category}
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Question question = new Question(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION3)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION4)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CORRECT_ANSWER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORRECT_ANSWER_TEXT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION_TYPE))
                );
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        db.close();

        return questionList;
    }
}
