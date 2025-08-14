package com.example.carparking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carparking.R;
import com.example.carparking.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        private TextView bookingIdTextView;
        private TextView slotIdTextView;
        private TextView vehicleIdTextView;
        private TextView bookingTimeTextView;
        private TextView statusTextView;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingIdTextView = itemView.findViewById(R.id.bookingIdTextView);
            slotIdTextView = itemView.findViewById(R.id.slotIdTextView);
            vehicleIdTextView = itemView.findViewById(R.id.vehicleIdTextView);
            bookingTimeTextView = itemView.findViewById(R.id.bookingTimeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }

        public void bind(Booking booking) {
            bookingIdTextView.setText("Booking #" + booking.getId().substring(0, 8));
            slotIdTextView.setText("Slot: " + booking.getSlotId());
            vehicleIdTextView.setText("Vehicle: " + booking.getVehicleId());
            bookingTimeTextView.setText("Booked on: " + booking.getBookingTime());
            statusTextView.setText("Status: " + booking.getStatus());
        }
    }
}