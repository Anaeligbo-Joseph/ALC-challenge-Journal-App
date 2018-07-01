package com.journalapp.u.alc;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.journalapp.u.alc.database.JournalAppDatabase;
import com.journalapp.u.alc.database.JournalEntry;

import java.util.Date;

public class AddJournalSection extends AppCompatActivity{
    private int mJournalId=DEFAULT_JORUNAL_ID;
    public static final String EXTRA_JOURNAL_ID = "extraJournalId";
    private static final String INSTANCE_JOURNAL_ID = "instanceJournalId";
    private static final int GENERAL = 1;
    private static final int WORK = 2;
    private static final int FAMILY = 3;
    private static final int PERSONAL = 4;
    private static final int DEFAULT_JORUNAL_ID=-1;
    private EditText mEditText;
    //Private Database for the add section/activity
    private JournalAppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes_layout);

        initializeViewsInLayout();
        mDb=JournalAppDatabase.getInstance((getApplicationContext()));

        if(savedInstanceState!=null&&savedInstanceState.containsKey(INSTANCE_JOURNAL_ID)){
            mJournalId=savedInstanceState.getInt(INSTANCE_JOURNAL_ID,DEFAULT_JORUNAL_ID);
        }
        Intent intent=getIntent();
        if(intent!=null&&intent.hasExtra(EXTRA_JOURNAL_ID)){
            Toast.makeText(this, "You are making an update to the Journal", Toast.LENGTH_LONG).show();
            if(mJournalId==DEFAULT_JORUNAL_ID){
                mJournalId=intent.getIntExtra(EXTRA_JOURNAL_ID,DEFAULT_JORUNAL_ID);
                AddJournalViewModelBase addJournalViewModel=new AddJournalViewModelBase(mDb,mJournalId);
                final AddJournalViewModel  vModel= ViewModelProviders.of(this, addJournalViewModel).get(AddJournalViewModel.class);
                vModel.getJournal().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        vModel.getJournal().removeObserver(this);
                        displayInterface(journalEntry);
                    }
                });
            }
        }
    }

    private void displayInterface(JournalEntry journalEntry) {
        if(journalEntry==null){
            return;
        }
        mEditText.setText(journalEntry.getDescription());
        setPriorityInViews(journalEntry.getPriority());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_JOURNAL_ID,mJournalId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.add_controller, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_accept) {
            triggerSave();
        }
        return super.onOptionsItemSelected(item);
    }

        private void triggerSave(){
            String descriptionNote = mEditText.getText().toString();
            int categorySelection = getPriorityFromViews();
            Date date = new Date();

            final JournalEntry note = new JournalEntry(descriptionNote, categorySelection, date);
            AppSwipeExecutors.getInstance().getDiskInputOutput().execute(new Runnable() {
                @Override
                public void run() {
                    if (mJournalId == DEFAULT_JORUNAL_ID) {
                        // insert new note
                        mDb.journalDAO().insertJournal(note);
                    } else {
                        //update the note
                        note.setId(mJournalId);
                        mDb.journalDAO().updateJournal(note);
                    }
                    finish();
                }
            });
        }

    private void initializeViewsInLayout() {
        mEditText = (EditText)findViewById(R.id.editNote);
        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radioGroupPack);
    }

    public void onSaveButtonClicked() {
        String description = mEditText.getText().toString();
        int categorySelection = getPriorityFromViews();
        Date date = new Date();

        final JournalEntry journal = new JournalEntry(description, categorySelection, date);
        AppSwipeExecutors.getInstance().getDiskInputOutput().execute(new Runnable() {
            @Override
            public void run() {
                if (mJournalId == DEFAULT_JORUNAL_ID) {
                    // insert new note
                    mDb.journalDAO().insertJournal(journal);
                } else {
                    //update note
                    journal.setId(mJournalId);
                    mDb.journalDAO().updateJournal(journal);
                }
                finish();
            }
        });
    }

    private int getPriorityFromViews() {
        int categorySelection = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroupPack)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                categorySelection = GENERAL;
                break;
            case R.id.radioButton2:
                categorySelection = WORK;
                break;
            case R.id.radioButton3:
                categorySelection = FAMILY;
                break;
            case R.id.radioButton4:
                categorySelection = PERSONAL;
        }
        return categorySelection;
    }

    private void setPriorityInViews(int categorySelection) {
        switch (categorySelection) {
            case GENERAL:
                ((RadioGroup) findViewById(R.id.radioGroupPack)).check(R.id.radButton1);
                break;
            case WORK:
                ((RadioGroup) findViewById(R.id.radioGroupPack)).check(R.id.radioButton2);
                break;
            case FAMILY:
                ((RadioGroup) findViewById(R.id.radioGroupPack)).check(R.id.radioButton3);
                break;
            case PERSONAL:
                ((RadioGroup) findViewById(R.id.radioGroupPack)).check(R.id.radioButton4);
        }
    }
}

