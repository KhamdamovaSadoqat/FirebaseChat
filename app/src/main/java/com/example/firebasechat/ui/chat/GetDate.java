package com.example.firebasechat.ui.chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetDate {


    public static String date() {
        long milliSeconds = currentMills();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.forLanguageTag("EN"));
        Date date = new Date(milliSeconds);
        return dateFormat.format(date);
    }

    public static long referenceMaking(){
        return  currentMills();
    }

    public static long currentMills(){
        return System.currentTimeMillis();
    }


}
