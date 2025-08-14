package com.example.carparking.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carparking.R;
import com.example.carparking.models.Feedback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedbackEditText;
    private Button submitFeedbackButton;
    private DatabaseReference feedbackRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mAuth = FirebaseAuth.getInstance();
        feedbackRef = FirebaseDatabase.getInstance().getReference("feedback");

        feedbackEditText = findViewById(R.id.feedbackEditText);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        submitFeedbackButton.setOnClickListener(v -> submitFeedback());
    }

    private void submitFeedback() {
        String feedbackText = feedbackEditText.getText().toString().trim();
        if (feedbackText.isEmpty()) {
            Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        String feedbackId = feedbackRef.push().getKey();
        String userId = mAuth.getCurrentUser().getUid();
        String feedbackTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        Feedback feedback = new Feedback(feedbackId, userId, feedbackText, feedbackTime);

        feedbackRef.child(feedbackId).setValue(feedback)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                        feedbackEditText.setText("");
                    } else {
                        Toast.makeText(FeedbackActivity.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
