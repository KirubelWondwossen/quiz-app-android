package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("totalQuestions", 0);
        String category = getIntent().getStringExtra("category");

        TextView tvScorePercent = findViewById(R.id.tvScorePercent);
        TextView tvScoreFraction = findViewById(R.id.tvScoreFraction);
        TextView tvResultTitle = findViewById(R.id.tvResultTitle);
        TextView tvResultMessage = findViewById(R.id.tvResultMessage);
        TextView btnRetry = findViewById(R.id.btnRetry);
        TextView btnHome = findViewById(R.id.btnHome);

        // Calculate percentage
        int percent = total > 0 ? (score * 100) / total : 0;

        tvScorePercent.setText(percent + "%");
        tvScoreFraction.setText(score + "/" + total);

        // Set motivational message based on score
        if (percent >= 80) {
            tvResultTitle.setText("Excellent! 🎉");
            tvResultMessage.setText("Outstanding performance! You really know your stuff!");
        } else if (percent >= 60) {
            tvResultTitle.setText("Great Job! 👏");
            tvResultMessage.setText("Well done! Keep practicing to improve even more!");
        } else if (percent >= 40) {
            tvResultTitle.setText("Good Try! 💪");
            tvResultMessage.setText("You're getting there! Review the material and try again.");
        } else {
            tvResultTitle.setText("Keep Learning! 📚");
            tvResultMessage.setText("Don't give up! Study more and come back stronger!");
        }

        // Animate score circle
        tvScorePercent.setAlpha(0f);
        tvScorePercent.setScaleX(0.3f);
        tvScorePercent.setScaleY(0.3f);
        tvScorePercent.animate()
                .alpha(1f).scaleX(1f).scaleY(1f)
                .setDuration(600).setStartDelay(300).start();

        tvScoreFraction.setAlpha(0f);
        tvScoreFraction.animate().alpha(1f).setDuration(400).setStartDelay(600).start();

        tvResultTitle.setAlpha(0f);
        tvResultTitle.setTranslationY(30f);
        tvResultTitle.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(800).start();

        tvResultMessage.setAlpha(0f);
        tvResultMessage.animate().alpha(1f).setDuration(400).setStartDelay(1000).start();

        // Retry - go back to same quiz
        btnRetry.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
            finish();
        });

        // Home - go back to MainActivity
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}