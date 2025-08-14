package com.example.carparking.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getCurrentDateTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
    }

    public static String formatDateTime(long timestamp) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                .format(new Date(timestamp));
    }
}