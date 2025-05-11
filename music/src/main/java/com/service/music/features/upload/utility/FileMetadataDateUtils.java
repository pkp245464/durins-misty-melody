package com.service.music.features.upload.utility;

import java.util.Calendar;
import java.util.Date;

public class FileMetadataDateUtils {
    public static Date addTime(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Set the current date
        calendar.add(Calendar.DATE, days);  // Add the specified number of days
        return calendar.getTime(); // Return the new date
    }
}
