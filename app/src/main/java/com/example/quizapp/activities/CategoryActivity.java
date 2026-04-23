package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;

public class CategoryActivity extends AppCompatActivity {

    private final String[] categories = {
            "Programming", "Advanced Programming", "Data Structure", "Machine Learning",
            "Database", "Software Development", "Software Testing", "Statistics"
    };

    private final int[] cardIds = {
            R.id.cardProgramming, R.id.cardAdvProgramming,
            R.id.cardDataStructure, R.id.cardML,
            R.id.cardDatabase, R.id.cardSoftwareDev,
            R.id.cardSoftwareTest, R.id.cardStatistics
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        for (int i = 0; i < cardIds.length; i++) {
            final String category = categories[i];
            View card = findViewById(cardIds[i]);

            // Animate cards appearing
            card.setAlpha(0f);
            card.setTranslationY(50f);
            card.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400)
                    .setStartDelay(100L * i)
                    .start();

            card.setOnClickListener(v -> {
                // Scale animation on click
                v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction(() -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                    intent.putExtra("category", category);
                    startActivity(intent);
                }).start();
            });
        }
    }
}
