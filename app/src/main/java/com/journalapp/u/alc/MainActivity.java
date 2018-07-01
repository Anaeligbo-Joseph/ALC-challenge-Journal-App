package com.journalapp.u.alc;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.journalapp.u.alc.database.JournalAppDatabase;
import com.journalapp.u.alc.database.JournalEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity implements JournalAdapter.ItemClickListener{

    //Create global variables for RecyclerView, adapter
    private RecyclerView mRecyclerView;
    private JournalAdapter mJournalAdapter;
    private JournalAppDatabase mDb;

    //Use to trigger share in futher development
    /*public static final String EXTRA_JOURNAL_ID="extraJournal";
    public static final String INSTANCE_JOURNAL_ID="instanceJournal";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewForJournal);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mJournalAdapter = new JournalAdapter(this, this);
        mRecyclerView.setAdapter(mJournalAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppSwipeExecutors.getInstance().getDiskInputOutput().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<JournalEntry> journals = mJournalAdapter.getJournals();
                        mDb.journalDAO().deleteJournal(journals.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPhase = new Intent(getApplicationContext(), AddJournalSection.class);
                startActivity(addPhase);
                //shorter way to initialize activity
                // startActivity(Intent(new Intent(this, AddJournalSection.class)));
            }
        });
        mDb=JournalAppDatabase.getInstance(getApplicationContext());
        buildViewModel();
    }

    private void buildViewModel(){
        BaseViewModel baseModel= ViewModelProviders.of(this).get(BaseViewModel.class);
        baseModel.getJournals().observe(this, new Observer<List<JournalEntry>>(){
            @Override
            public void onChanged(@Nullable List<JournalEntry> journalEntries) {
                mJournalAdapter.setJournals(journalEntries);
            }
        });
    }

    @Override
    public void onItemClickListener (int itemTag){
        Intent extraIntentJournal=new Intent(MainActivity.this, AddJournalSection.class);
        extraIntentJournal.putExtra(AddJournalSection.EXTRA_JOURNAL_ID, itemTag);
        startActivity(extraIntentJournal);
    }
}

