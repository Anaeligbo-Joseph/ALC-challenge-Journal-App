package com.journalapp.u.alc;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.journalapp.u.alc.database.JournalAppDatabase;
import com.journalapp.u.alc.database.JournalEntry;

class AddJournalViewModel extends ViewModel{
    private LiveData<JournalEntry> journal;

    public AddJournalViewModel(JournalAppDatabase database, int journalId) {
        journal=database.journalDAO().loadJournalById(journalId);
    }

    public LiveData<JournalEntry> getJournal() {
        return journal;
    }
}
