package com.example.android.access;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitorRepository {

    private VisitorDao mVisitorDao;
    private LiveData<List<Visitor>> mAllVisitors;

    public VisitorRepository(Application application) {
        VisitorRoomDatabase db = VisitorRoomDatabase.getDatabase(application);
        mVisitorDao = db.mVisitorDao();
        mAllVisitors = mVisitorDao.getAllVisitors();
    }

    LiveData<List<Visitor>> getAllVisitors() {
        return mAllVisitors;
    }

    public void insert(Visitor visitor) {
        new InsertAsyncTask(mVisitorDao).execute(visitor);
    }

    public void delete(Visitor visitor) {
        new DeleteAsyncTask(mVisitorDao).execute(visitor);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mVisitorDao).execute();
    }

    private class InsertAsyncTask extends AsyncTask<Visitor, Void, Void> {

        private VisitorDao mASyncVisitorDao;

        public InsertAsyncTask(VisitorDao ASyncVisitorDao) {
            mASyncVisitorDao = ASyncVisitorDao;
        }

        @Override
        protected Void doInBackground(Visitor... visitors) {
            mASyncVisitorDao.insert(visitors[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Visitor, Void, Void> {

        private VisitorDao mASyncVisitorDao;

        public DeleteAsyncTask(VisitorDao ASyncVisitorDao) {
            mASyncVisitorDao = ASyncVisitorDao;
        }

        @Override
        protected Void doInBackground(Visitor... visitors) {
            mASyncVisitorDao.delete(visitors[0]);
            return null;
        }
    }

    private class DeleteAllAsyncTask extends AsyncTask<Visitor, Void, Void> {

        private VisitorDao mAsyncVisitorDao;

        public DeleteAllAsyncTask(VisitorDao asyncVisitorDao) {
            mAsyncVisitorDao = asyncVisitorDao;
        }

        @Override
        protected Void doInBackground(Visitor... visitors) {
            mAsyncVisitorDao.deleteAll();
            return null;
        }
    }

}
