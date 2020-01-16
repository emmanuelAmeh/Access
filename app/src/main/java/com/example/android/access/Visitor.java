package com.example.android.access;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;


@Entity(tableName = "visitors_table")
@TypeConverters({Converters.class})

public class Visitor {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mFirstname;
    private String mLastname;
    private String mHostId;
    private String mVisitorCode;
    private Date mDate;

    public Visitor(String firstname, String lastname, String hostId, String visitorCode, Date date) {
        mFirstname = firstname;
        mLastname = lastname;
        mHostId = hostId;
        mVisitorCode = visitorCode;
        mDate = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public String getLastname() {
        return mLastname;
    }

    public String getHostId() {
        return mHostId;
    }

    public String getVisitorCode() {
        return mVisitorCode;
    }

    public Date getDate() {
        return mDate;
    }
}
