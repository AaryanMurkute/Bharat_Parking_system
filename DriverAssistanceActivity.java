package com.example.carparking.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carparking.R;
import com.example.carparking.models.DriverRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DriverAssistanceActivity extends AppCompatActivity {

    private Button callDriverButton;
    private DatabaseReference driverRequestsRef;
    private FirebaseAuth mAuth;
    private String bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_assistance);

        mAuth = FirebaseAuth.getInstance();
        driverRequestsRef = FirebaseDatabase.getInstance().getReference("driver_requests");
        bookingId = getIntent().getStringExtra("booking_id");

        callDriverButton = findViewById(R.id.callDriverButton);

        callDriverButton.setOnClickListener(v -> requestDriverAssistance());
    }

    private void requestDriverAssistance() {
        String requestId = driverRequestsRef.push().getKey();
        String userId = mAuth.getCurrentUser().getUid();
        String requestTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        DriverRequest request = new DriverRequest(requestId, userId, bookingId, requestTime, "pending");

        driverRequestsRef.child(requestId).setValue(request)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Driver requested successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Request failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}