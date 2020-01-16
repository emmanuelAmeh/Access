package com.example.android.access;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.Calendar;


/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {Visitor.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})

public abstract class VisitorRoomDatabase extends RoomDatabase {

    private static VisitorRoomDatabase INSTANCE;
    /**
     * Override the onOpen method to populate the database.
     * we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    static VisitorRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VisitorRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VisitorRoomDatabase.class, "visitor_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract VisitorDao mVisitorDao();

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final VisitorDao mDao;

        PopulateDbAsync(VisitorRoomDatabase db) {
            mDao = db.mVisitorDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();
            Date longTime = Calendar.getInstance().getTime();


            mDao.insert(new Visitor("Emmanuel", "Ameh", "09Gogo02", "4321", longTime));
            mDao.insert(new Visitor("Joy", "Amechi", "02Gogo01", "0321", longTime));
            mDao.insert(new Visitor("Ogechi", "Ameh", "09Gogo02", "0021", longTime));
            mDao.insert(new Visitor("Emmanuel", "Ameh", "09Gogo02", "4321", longTime));
            mDao.insert(new Visitor("Joy", "Amechi", "02Gogo01", "0321", longTime));
            mDao.insert(new Visitor("Ogechi", "Ameh", "09Gogo02", "0021", longTime));
            mDao.insert(new Visitor("Emmanuel", "Ameh", "09Gogo02", "4321", longTime));
            mDao.insert(new Visitor("Joy", "Amechi", "02Gogo01", "0321", longTime));
            mDao.insert(new Visitor("Ogechi", "Ameh", "09Gogo02", "0021", longTime));

            return null;
        }
    }
}
