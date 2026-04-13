package com.example.quizapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTIONS = "questions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";
    private static final String COLUMN_OPTION4 = "option4";
    private static final String COLUMN_CORRECT_ANSWER = "correctAnswer";
    private static final String COLUMN_CATEGORY = "category";

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
                COLUMN_CATEGORY + " TEXT)";
        db.execSQL(createTable);

        insertDefaultQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    private void insertDefaultQuestions(SQLiteDatabase db) {
        // Programming questions
        addQuestion(db, new Question("What does HTML stand for?", "Hyper Text Markup Language", "Hyperlinks and Text Markup Language", "Home Tool Markup Language", "None of these", 1, "Programming"));
        addQuestion(db, new Question("Which language is used for styling web pages?", "HTML", "JQuery", "CSS", "XML", 3, "Programming"));
        addQuestion(db, new Question("What is the extension of Java code files?", ".txt", ".pdf", ".sql", ".java", 4, "Programming"));
        addQuestion(db, new Question("Which keyword is used to define a class in Java?", "class", "Class", "define", "struct", 1, "Programming"));
        addQuestion(db, new Question("What does OOP stand for?", "Object Oriented Programming", "Object Oriented Protocol", "Open Object Protocol", "None of these", 1, "Programming"));

        // Math questions
        addQuestion(db, new Question("What is 5 + 7?", "10", "11", "12", "13", 3, "Math"));
        addQuestion(db, new Question("What is 15 - 8?", "6", "7", "8", "9", 2, "Math"));
        addQuestion(db, new Question("What is 6 * 4?", "20", "22", "24", "26", 3, "Math"));
        addQuestion(db, new Question("What is 100 / 4?", "20", "25", "30", "35", 2, "Math"));
        addQuestion(db, new Question("What is the square root of 64?", "6", "7", "8", "9", 3, "Math"));

        // Science questions
        addQuestion(db, new Question("What is the chemical symbol for water?", "O2", "H2O", "CO2", "HO", 2, "Science"));
        addQuestion(db, new Question("What planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn", 2, "Science"));
        addQuestion(db, new Question("What gas do plants absorb?", "Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen", 2, "Science"));
        addQuestion(db, new Question("How many bones are in the adult human body?", "206", "208", "210", "212", 1, "Science"));
        addQuestion(db, new Question("What is the center of an atom called?", "Proton", "Electron", "Nucleus", "Neutron", 3, "Science"));
    }

    private void addQuestion(SQLiteDatabase db, Question q) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, q.getQuestion());
        cv.put(COLUMN_OPTION1, q.getOption1());
        cv.put(COLUMN_OPTION2, q.getOption2());
        cv.put(COLUMN_OPTION3, q.getOption3());
        cv.put(COLUMN_OPTION4, q.getOption4());
        cv.put(COLUMN_CORRECT_ANSWER, q.getCorrectAnswer());
        cv.put(COLUMN_CATEGORY, q.getCategory());
        db.insert(TABLE_QUESTIONS, null, cv);
    }

 public List<Question> getQuestionsByCategory(String category) {
    List<Question> questionList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    String[] selectionArgs = new String[]{category};
    Cursor cursor = db.rawQuery(
            "SELECT * FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_CATEGORY + " = ?",
            selectionArgs
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
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
            );
            questionList.add(question);
        } while (cursor.moveToNext());
    }

    if (cursor != null) cursor.close();
    db.close();

    return questionList;
}
}
