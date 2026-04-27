package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizapp.R;
import com.example.quizapp.data.DBHelper;
import com.example.quizapp.data.UserSession;

public class ProfileFragment extends Fragment {

    private TextView tvUsername, tvGuestMessage;
    private LinearLayout layoutEditProfile;
    private EditText etNewUsername, etOldPassword, etNewPassword;
    private Button btnUpdateUsername, btnUpdatePassword, btnAuthAction;
    private UserSession session;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvGuestMessage = view.findViewById(R.id.tvGuestMessage);
        layoutEditProfile = view.findViewById(R.id.layoutEditProfile);
        
        etNewUsername = view.findViewById(R.id.etNewUsername);
        btnUpdateUsername = view.findViewById(R.id.btnUpdateUsername);
        
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        btnUpdatePassword = view.findViewById(R.id.btnUpdatePassword);
        
        btnAuthAction = view.findViewById(R.id.btnAuthAction);

        session = new UserSession(getContext());
        dbHelper = new DBHelper(getContext());

        if (session.isGuest()) {
            setupGuestView();
        } else {
            setupUserView();
        }

        return view;
    }

    private void setupGuestView() {
        tvUsername.setText("Guest");
        tvGuestMessage.setVisibility(View.VISIBLE);
        layoutEditProfile.setVisibility(View.GONE);
        
        btnAuthAction.setText("Create Account / Login");
        btnAuthAction.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }

    private void setupUserView() {
        tvUsername.setText(session.getUsername());
        tvGuestMessage.setVisibility(View.GONE);
        layoutEditProfile.setVisibility(View.VISIBLE);

        btnAuthAction.setText("Logout");
        btnAuthAction.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        btnUpdateUsername.setOnClickListener(v -> {
            String newUsername = etNewUsername.getText().toString().trim();
            if (newUsername.isEmpty()) {
                Toast.makeText(getContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newUsername.contains(" ")) {
                Toast.makeText(getContext(), "Username cannot contain spaces", Toast.LENGTH_SHORT).show();
                return;
            }
            
            if (dbHelper.isUsernameTaken(newUsername)) {
                Toast.makeText(getContext(), "Username is already taken", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.updateUsername(session.getUserId(), newUsername);
            if (success) {
                session.updateUsername(newUsername);
                tvUsername.setText(newUsername);
                etNewUsername.setText("");
                Toast.makeText(getContext(), "Username updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to update username", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdatePassword.setOnClickListener(v -> {
            String oldPass = etOldPassword.getText().toString().trim();
            String newPass = etNewPassword.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all password fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPass.length() < 6) {
                Toast.makeText(getContext(), "New password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.updatePassword(session.getUserId(), oldPass, newPass);
            if (success) {
                etOldPassword.setText("");
                etNewPassword.setText("");
                Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Incorrect current password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
