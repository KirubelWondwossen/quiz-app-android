package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnProgramming = findViewById(R.id.btnProgramming);
        MaterialButton btnMath = findViewById(R.id.btnMath);
        MaterialButton btnScience = findViewById(R.id.btnScience);
        MaterialButton btnStartQuiz = findViewById(R.id.btnStartQuiz);

        btnProgramming.setOnClickListener(v -> {
            selectedCategory = "Programming";
            resetButtons(btnProgramming, btnMath, btnScience);
            btnProgramming.setBackgroundTintList(getColorStateList(R.color.purple_500));
        });

        btnMath.setOnClickListener(v -> {
            selectedCategory = "Math";
            resetButtons(btnProgramming, btnMath, btnScience);
            btnMath.setBackgroundTintList(getColorStateList(R.color.purple_500));
        });

        btnScience.setOnClickListener(v -> {
            selectedCategory = "Science";
            resetButtons(btnProgramming, btnMath, btnScience);
            btnScience.setBackgroundTintList(getColorStateList(R.color.purple_500));
        });

        btnStartQuiz.setOnClickListener(v -> {
            if (selectedCategory.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please select a category first", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("category", selectedCategory);
                startActivity(intent);
            }
        });
    }

    // Reset button colors
    private void resetButtons(MaterialButton... buttons) {
        for (MaterialButton btn : buttons) {
            btn.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));
        }
    }
}