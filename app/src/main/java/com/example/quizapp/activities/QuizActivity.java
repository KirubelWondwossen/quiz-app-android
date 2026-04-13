package com.example.quizapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.google.android.material.button.MaterialButton;

public class QuizActivity extends AppCompatActivity {

    private String category;

    private TextView tvTimer, tvQuestion;

    private MaterialButton btnOption1, btnOption2, btnOption3, btnOption4;
    private MaterialButton btnExplain, btnNext;

    // Future logic variables
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        category = getIntent().getStringExtra("category");
        if (category == null) category = "Programming";

        initViews();
    }

    private void initViews() {
        tvTimer = findViewById(R.id.tvTimer);
        tvQuestion = findViewById(R.id.tvQuestion);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        btnExplain = findViewById(R.id.btnExplain);
        btnNext = findViewById(R.id.btnNext);

        setupListeners();
    }

    private void setupListeners() {
        btnOption1.setOnClickListener(v -> handleAnswer(1));
        btnOption2.setOnClickListener(v -> handleAnswer(2));
        btnOption3.setOnClickListener(v -> handleAnswer(3));
        btnOption4.setOnClickListener(v -> handleAnswer(4));

        btnNext.setOnClickListener(v -> goToNextQuestion());

        btnExplain.setOnClickListener(v -> {
            // Placeholder for AI explanation
        });
    }

    private void handleAnswer(int selectedOption) {
        // Will implement later
    }

    private void goToNextQuestion() {
        // Will implement later
    }
}