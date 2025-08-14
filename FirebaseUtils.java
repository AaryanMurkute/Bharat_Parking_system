package com.example.carparking.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    public static DatabaseReference getUsersRef() {
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public static DatabaseReference getSlotsRef() {
        return FirebaseDatabase.getInstance().getReference("parking_slots");
    }

    public static DatabaseReference getBookingsRef() {
        return FirebaseDatabase.getInstance().getReference("bookings");
    }

    public static DatabaseReference getDriverRequestsRef() {
        return FirebaseDatabase.getInstance().getReference("driver_requests");
    }

    public static DatabaseReference getFeedbackRef() {
        return FirebaseDatabase.getInstance().getReference("feedback");
    }

    public static String getCurrentUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}