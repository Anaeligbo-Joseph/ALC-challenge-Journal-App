package com.journalapp.u.alc;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.journalapp.u.alc.database.JournalAppDatabase;

@SuppressWarnings("unchecked")
class AddJournalViewModelBase extends ViewModelProvider.NewInstanceFactory{
    private final int mJournalId;
    private final JournalAppDatabase mDb;

    public AddJournalViewModelBase(JournalAppDatabase database, int journalId) {
        mDb = database;
        mJournalId = journalId;
    }

    @Override
    public <J extends ViewModel> J create(Class<J> modelClass) {
        return (J) new AddJournalViewModel(mDb, mJournalId);
    }
}
