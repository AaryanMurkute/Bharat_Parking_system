package com.example.carparking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carparking.R;
import com.example.carparking.models.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private TextView slotIdTextView, timingTextView;
    private EditText vehicleIdEditText, phoneEditText;
    private Button bookingButton;
    private DatabaseReference bookingsRef, slotsRef;
    private String selectedSlotId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        selectedSlotId = getIntent().getStringExtra("slot_id");
        mAuth = FirebaseAuth.getInstance();
        bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
        slotsRef = FirebaseDatabase.getInstance().getReference("parking_slots");

        slotIdTextView = findViewById(R.id.slotIdTextView);
        timingTextView = findViewById(R.id.timingTextView);
        vehicleIdEditText = findViewById(R.id.vehicleIdEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        bookingButton = findViewById(R.id.bookingButton);

        slotIdTextView.setText("Slot: " + selectedSlotId);
        timingTextView.setText("Time: " + new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date()));

        bookingButton.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        String vehicleId = vehicleIdEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (vehicleId.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String bookingId = bookingsRef.push().getKey();
        String userId = mAuth.getCurrentUser().getUid();
        String bookingTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        Booking booking = new Booking(bookingId, userId, selectedSlotId, vehicleId, phone, bookingTime, "active");

        bookingsRef.child(bookingId).setValue(booking)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update slot status to booked
                        slotsRef.child(selectedSlotId).child("booked").setValue(true);

                        Toast.makeText(BookingActivity.this, "Booking confirmed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BookingActivity.this, MyBookingsActivity.class));
                        finish();
                    } else {
                        Toast.makeText(BookingActivity.this, "Booking failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}