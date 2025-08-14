package com.example.carparking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carparking.R;
import com.example.carparking.adapters.SlotAdapter;
import com.example.carparking.models.ParkingSlot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SlotSelectionActivity extends AppCompatActivity implements SlotAdapter.OnSlotClickListener {

    private RecyclerView slotsRecyclerView;
    private SlotAdapter slotAdapter;
    private List<ParkingSlot> slotList = new ArrayList<>();
    private DatabaseReference slotsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_selection);

        slotsRef = FirebaseDatabase.getInstance().getReference("parking_slots");

        slotsRecyclerView = findViewById(R.id.slotsRecyclerView);
        slotsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        slotAdapter = new SlotAdapter(this, slotList, this);
        slotsRecyclerView.setAdapter(slotAdapter);

        loadAvailableSlots();
    }

    private void loadAvailableSlots() {
        slotsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                slotList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ParkingSlot slot = snapshot.getValue(ParkingSlot.class);
                    if (slot != null && !slot.isBooked()) {
                        slotList.add(slot);
                    }
                }
                slotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SlotSelectionActivity.this, "Failed to load slots", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSlotClick(ParkingSlot slot) {
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("slot_id", slot.getId());
        startActivity(intent);
    }
}