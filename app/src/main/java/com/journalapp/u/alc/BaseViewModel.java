package com.journalapp.u.alc;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.journalapp.u.alc.database.JournalAppDatabase;
import com.journalapp.u.alc.database.JournalEntry;

import java.util.List;

public class BaseViewModel extends AndroidViewModel{


    private LiveData<List<JournalEntry>> journals;
    private JournalAppDatabase dB;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        dB=JournalAppDatabase.getInstance(this.getApplication());
        journals=dB.journalDAO().loadAllJournals();
    }

    public LiveData<List<JournalEntry>> getJournals() {
        return journals;
    }
}
