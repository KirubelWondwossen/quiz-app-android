package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Legacy MainActivity - redirects to SplashActivity.
 * This class is no longer used as the launcher but kept for compatibility.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Redirect to the new splash screen
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}