package com.example.carparking.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carparking.R;
import com.example.carparking.adapters.BookingAdapter;
import com.example.carparking.models.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView bookingsRecyclerView;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList = new ArrayList<>();
    private DatabaseReference bookingsRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        mAuth = FirebaseAuth.getInstance();
        bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");

        bookingsRecyclerView = findViewById(R.id.bookingsRecyclerView);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingAdapter = new BookingAdapter(this, bookingList);
        bookingsRecyclerView.setAdapter(bookingAdapter);

        loadUserBookings();
    }

    private void loadUserBookings() {
        String userId = mAuth.getCurrentUser().getUid();

        bookingsRef.orderByChild("userId").equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bookingList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Booking booking = snapshot.getValue(Booking.class);
                            if (booking != null) {
                                bookingList.add(booking);
                            }
                        }
                        bookingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MyBookingsActivity.this, "Failed to load bookings", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}