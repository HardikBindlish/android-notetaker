package com.example.notetaker;

import android.content.Context;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Notes implements Serializable {
    private Long mDateTime;
    private String mTitle;
    private String mContext;

    public Notes(Long dateTime, String title, String context){
        mContext = context;
        mTitle = title;
        mDateTime = dateTime;
    }

    public Long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Long DateTime) {
        this.mDateTime = DateTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getContext() {
        return mContext;
    }

    public void setContext(String Context) {
        this.mContext = Context;
    }

    public String getDateTimeFormatted(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(mDateTime));
    }
}


