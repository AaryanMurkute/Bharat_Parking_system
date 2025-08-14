package com.example.carparking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carparking.R;
import com.example.carparking.models.ParkingSlot;

import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {

    private List<ParkingSlot> slotList;
    private OnSlotClickListener listener;

    public interface OnSlotClickListener {
        void onSlotClick(ParkingSlot slot);
    }

    public SlotAdapter(List<ParkingSlot> slotList, OnSlotClickListener listener) {
        this.slotList = slotList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slot, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        ParkingSlot slot = slotList.get(position);
        holder.bind(slot, listener);
    }

    @Override
    public int getItemCount() {
        return slotList.size();
    }

    static class SlotViewHolder extends RecyclerView.ViewHolder {
        private TextView slotIdTextView;
        private TextView slotStatusTextView;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            slotIdTextView = itemView.findViewById(R.id.slotIdTextView);
            slotStatusTextView = itemView.findViewById(R.id.slotStatusTextView);
        }

        public void bind(final ParkingSlot slot, final OnSlotClickListener listener) {
            slotIdTextView.setText("Slot " + slot.getId());
            slotStatusTextView.setText(slot.isBooked() ? "Booked" : "Available");
            slotStatusTextView.setTextColor(slot.isBooked() ?
                    itemView.getContext().getResources().getColor(R.color.red) :
                    itemView.getContext().getResources().getColor(R.color.green));

            if (!slot.isBooked()) {
                itemView.setOnClickListener(v -> listener.onSlotClick(slot));
            } else {
                itemView.setOnClickListener(null);
            }
        }
    }
}