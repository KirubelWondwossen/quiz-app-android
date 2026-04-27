package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.data.DBHelper;
import com.example.quizapp.data.Question;
import com.example.quizapp.data.QuizResult;
import com.example.quizapp.data.UserSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private String category;
    private TextView tvCategoryTitle, tvQuestionCounter, tvQuestionType, tvQuestion;
    private ProgressBar progressBar;
    private LinearLayout layoutOptions, layoutTrueFalse, layoutFillBlank;
    private TextView btnOption1, btnOption2, btnOption3, btnOption4;
    private TextView btnTrue, btnFalse;
    private EditText etAnswer;
    private TextView btnPrevious, btnNext;
    private ImageButton btnBack;

    private int currentQuestionIndex = 0;
    private int selectedOption = -1;
    private String fillAnswer = "";

    private DBHelper dbHelper;
    private List<Question> questionList;

    // Store user answers for navigation
    private int[] userAnswers;
    private String[] userTextAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        category = getIntent().getStringExtra("category");
        if (category == null) category = "Programming";

        initViews();

        dbHelper = new DBHelper(this);
        questionList = dbHelper.getQuestionsByCategory(category);

        if (questionList != null && !questionList.isEmpty()) {
            userAnswers = new int[questionList.size()];
            userTextAnswers = new String[questionList.size()];
            for (int i = 0; i < questionList.size(); i++) {
                userAnswers[i] = -1;
                userTextAnswers[i] = "";
            }
            tvCategoryTitle.setText(category);
            displayQuestion();
        } else {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvQuestionCounter = findViewById(R.id.tvQuestionCounter);
        tvQuestionType = findViewById(R.id.tvQuestionType);
        tvQuestion = findViewById(R.id.tvQuestion);
        progressBar = findViewById(R.id.progressBar);

        layoutOptions = findViewById(R.id.layoutOptions);
        layoutTrueFalse = findViewById(R.id.layoutTrueFalse);
        layoutFillBlank = findViewById(R.id.layoutFillBlank);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        etAnswer = findViewById(R.id.etAnswer);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        setupListeners();
    }

    private void setupListeners() {
        btnOption1.setOnClickListener(v -> selectOption(1));
        btnOption2.setOnClickListener(v -> selectOption(2));
        btnOption3.setOnClickListener(v -> selectOption(3));
        btnOption4.setOnClickListener(v -> selectOption(4));

        btnTrue.setOnClickListener(v -> selectOption(1));
        btnFalse.setOnClickListener(v -> selectOption(2));

        btnNext.setOnClickListener(v -> goToNextQuestion());
        btnPrevious.setOnClickListener(v -> goToPreviousQuestion());
        btnBack.setOnClickListener(v -> finish());
    }

    private void selectOption(int option) {
        selectedOption = option;
        userAnswers[currentQuestionIndex] = option;
        updateOptionStyles();
    }

    private void updateOptionStyles() {
        Question q = questionList.get(currentQuestionIndex);
        String type = q.getQuestionType();

        if (Question.TYPE_MULTIPLE_CHOICE.equals(type)) {
            btnOption1.setBackgroundResource(selectedOption == 1 ?
                    R.drawable.bg_option_selected : R.drawable.bg_option_default);
            btnOption2.setBackgroundResource(selectedOption == 2 ?
                    R.drawable.bg_option_selected : R.drawable.bg_option_default);
            btnOption3.setBackgroundResource(selectedOption == 3 ?
                    R.drawable.bg_option_selected : R.drawable.bg_option_default);
            btnOption4.setBackgroundResource(selectedOption == 4 ?
                    R.drawable.bg_option_selected : R.drawable.bg_option_default);
        } else if (Question.TYPE_TRUE_FALSE.equals(type)) {
            btnTrue.setBackgroundResource(selectedOption == 1 ?
                    R.drawable.bg_option_selected : R.drawable.bg_option_default);
            btnFalse.setBackgroundResource(selectedOption == 2 ?
                    R.drawable.bg_option_selected : R.drawable.bg_option_default);
        }
    }

    private void goToNextQuestion() {
        saveCurrentAnswer();

        Question currentQ = questionList.get(currentQuestionIndex);
        if (Question.TYPE_FILL_BLANK.equals(currentQ.getQuestionType())) {
            String ans = etAnswer.getText().toString().trim();
            if (ans.isEmpty()) {
                Toast.makeText(this, "Please type your answer", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (selectedOption == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < questionList.size()) {
            displayQuestion();
        } else {
            showResult();
        }
    }

    private void goToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            saveCurrentAnswer();
            currentQuestionIndex--;
            displayQuestion();
        }
    }

    private void saveCurrentAnswer() {
        Question q = questionList.get(currentQuestionIndex);
        if (Question.TYPE_FILL_BLANK.equals(q.getQuestionType())) {
            userTextAnswers[currentQuestionIndex] = etAnswer.getText().toString().trim();
        } else {
            userAnswers[currentQuestionIndex] = selectedOption;
        }
    }

    private void displayQuestion() {
        Question q = questionList.get(currentQuestionIndex);

        // Update counter and progress
        tvQuestionCounter.setText((currentQuestionIndex + 1) + "/" + questionList.size());
        int progress = (int) (((float) (currentQuestionIndex + 1) / questionList.size()) * 100);
        progressBar.setProgress(progress);

        // Set question text
        tvQuestion.setText(q.getQuestion());

        // Hide all answer layouts
        layoutOptions.setVisibility(View.GONE);
        layoutTrueFalse.setVisibility(View.GONE);
        layoutFillBlank.setVisibility(View.GONE);

        // Show appropriate layout based on question type
        String type = q.getQuestionType();
        if (Question.TYPE_MULTIPLE_CHOICE.equals(type)) {
            tvQuestionType.setText("MULTIPLE CHOICE");
            layoutOptions.setVisibility(View.VISIBLE);
            btnOption1.setText(q.getOption1());
            btnOption2.setText(q.getOption2());
            btnOption3.setText(q.getOption3());
            btnOption4.setText(q.getOption4());
        } else if (Question.TYPE_TRUE_FALSE.equals(type)) {
            tvQuestionType.setText("TRUE / FALSE");
            layoutTrueFalse.setVisibility(View.VISIBLE);
        } else if (Question.TYPE_FILL_BLANK.equals(type)) {
            tvQuestionType.setText("FILL IN THE BLANK");
            layoutFillBlank.setVisibility(View.VISIBLE);
            etAnswer.setText(userTextAnswers[currentQuestionIndex]);
        }

        // Restore saved answer
        selectedOption = userAnswers[currentQuestionIndex];
        updateOptionStyles();

        // Update previous button visibility
        btnPrevious.setVisibility(currentQuestionIndex > 0 ? View.VISIBLE : View.INVISIBLE);

        // Update next button text
        if (currentQuestionIndex == questionList.size() - 1) {
            btnNext.setText("Finish ✓");
        } else {
            btnNext.setText("Next →");
        }

        // Animate question entry
        tvQuestion.setAlpha(0f);
        tvQuestion.setTranslationX(40f);
        tvQuestion.animate().alpha(1f).translationX(0f).setDuration(300).start();
    }

    private void showResult() {
        int score = 0;
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            if (Question.TYPE_FILL_BLANK.equals(q.getQuestionType())) {
                if (userTextAnswers[i] != null &&
                        userTextAnswers[i].equalsIgnoreCase(q.getCorrectAnswerText())) {
                    score++;
                }
            } else {
                if (userAnswers[i] == q.getCorrectAnswer()) {
                    score++;
                }
            }
        }

        UserSession session = new UserSession(this);
        if (!session.isGuest()) {
            QuizResult result = new QuizResult();
            result.setUserId(session.getUserId());
            result.setCategory(category);
            result.setScore(score);
            result.setTotalQuestions(questionList.size());
            result.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));
            dbHelper.saveResult(result);
        }

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questionList.size());
        intent.putExtra("category", category);
        startActivity(intent);
        finish();
    }
}