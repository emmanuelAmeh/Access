package com.example.android.access;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitorViewModel extends AndroidViewModel {

    private VisitorRepository mVisitorRepository;

    private LiveData<List<Visitor>> mAllVisitors;

    public VisitorViewModel(@NonNull Application application) {
        super(application);
        mVisitorRepository = new VisitorRepository(application);
        mAllVisitors = mVisitorRepository.getAllVisitors();
    }

    public void insert(Visitor visitor) {
        mVisitorRepository.insert(visitor);
    }

    public void delete(Visitor visitor) {
        mVisitorRepository.delete(visitor);
    }

    public void deleteAll() {
        mVisitorRepository.deleteAll();
    }

    LiveData<List<Visitor>> getAllVisitors() {
        return mAllVisitors;
    }
}
