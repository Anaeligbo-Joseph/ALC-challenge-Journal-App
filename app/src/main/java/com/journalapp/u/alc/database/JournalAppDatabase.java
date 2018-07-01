package com.journalapp.u.alc.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@TypeConverters(DateStructurer.class)
@Database(entities={JournalEntry.class}, version=1, exportSchema=false)
public abstract class JournalAppDatabase extends RoomDatabase{

    private static final Object LOCK=new Object();
    private static final String DATABASE_NAME="journalnotes";
    private static JournalAppDatabase mInstance;


    public static JournalAppDatabase getInstance(Context context){
        if(mInstance==null){
            synchronized (LOCK){
                mInstance= Room.databaseBuilder(context.getApplicationContext(),JournalAppDatabase.class,JournalAppDatabase.DATABASE_NAME).build();
            }
        }
        return mInstance;
    }
    public abstract JournalDao journalDAO();
}
