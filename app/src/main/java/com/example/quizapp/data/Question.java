package com.example.quizapp.data;

public class Question {
    // Question types
    public static final String TYPE_MULTIPLE_CHOICE = "multiple_choice";
    public static final String TYPE_TRUE_FALSE = "true_false";
    public static final String TYPE_FILL_BLANK = "fill_blank";

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer;       // For MCQ (1-4) and TF (1=True, 2=False)
    private String correctAnswerText; // For fill in the blank
    private String category;
    private String questionType;

    // Constructor for Multiple Choice
    public Question(String question, String option1, String option2, String option3, String option4, int correctAnswer, String category) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.questionType = TYPE_MULTIPLE_CHOICE;
        this.correctAnswerText = "";
    }

    // Constructor for True/False
    public Question(String question, boolean correctAnswer, String category) {
        this.question = question;
        this.option1 = "True";
        this.option2 = "False";
        this.option3 = "";
        this.option4 = "";
        this.correctAnswer = correctAnswer ? 1 : 2;
        this.category = category;
        this.questionType = TYPE_TRUE_FALSE;
        this.correctAnswerText = "";
    }

    // Constructor for Fill in the Blank
    public Question(String question, String correctAnswerText, String category) {
        this.question = question;
        this.option1 = "";
        this.option2 = "";
        this.option3 = "";
        this.option4 = "";
        this.correctAnswer = 0;
        this.correctAnswerText = correctAnswerText;
        this.category = category;
        this.questionType = TYPE_FILL_BLANK;
    }

    // Full constructor (used by DB)
    public Question(String question, String option1, String option2, String option3, String option4,
                    int correctAnswer, String correctAnswerText, String category, String questionType) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.correctAnswerText = correctAnswerText;
        this.category = category;
        this.questionType = questionType;
    }

    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getCorrectAnswerText() { return correctAnswerText; }
    public String getCategory() { return category; }
    public String getQuestionType() { return questionType; }
}
