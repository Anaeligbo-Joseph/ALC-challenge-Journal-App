package com.journalapp.u.alc;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.journalapp.u.alc.database.JournalEntry;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private static final String DATE_SHOW_FORMAT="dd/MM/yyy";

    final private ItemClickListener myItemClickListener;

    private List<JournalEntry> mJournalEntries;
    private Context mContext;
    private SimpleDateFormat mDateFormat=new SimpleDateFormat(DATE_SHOW_FORMAT, Locale.getDefault());

    public JournalAdapter(Context context, ItemClickListener listener){
        mContext=context;
        myItemClickListener=listener;
    }
    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the journal_layout to a view and associate with it.
        View view= LayoutInflater.from(mContext).inflate(R.layout.journal_layout,parent,false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        //
        JournalEntry journalEntry=mJournalEntries.get(position);
        String journalDes=journalEntry.getDescription();
        int priority=journalEntry.getPriority();
        String updatedAt=mDateFormat.format(journalEntry.getUpdatedAt());

        //attach values
        holder.updatedAtView.setText(updatedAt);
        holder.journalDescView.setText(journalDes);

        String categoryRatingString=""+priority;
        holder.categoryRatingView.setText(categoryRatingString);

        GradientDrawable categoryRatingTag= (GradientDrawable)holder.categoryRatingView.getBackground();

        int categoryColor=getPriorityColor(priority);
        categoryRatingTag.setColor(categoryColor);
    }

    private int getPriorityColor(int priority){
        int categoryColor=0;

        switch(priority){
            case 1:
                categoryColor= ContextCompat.getColor(mContext, R.color.colorGreen);
                break;
            case 2:
                categoryColor= ContextCompat.getColor(mContext, R.color.colorBlue);
                break;
            case 3:
                categoryColor=ContextCompat.getColor(mContext,R.color.colorRed);
                break;
            case 4:
                categoryColor=ContextCompat.getColor(mContext,R.color.colorYellow);
                break;
            default:
                break;
        }
        return categoryColor;
    }

    @Override
    public int getItemCount() {
        if(mJournalEntries==null){
            return 0;
        }
        return mJournalEntries.size();
    }

    public List<JournalEntry> getJournals(){
        return mJournalEntries;
    }

    public void setJournals(List<JournalEntry> journalEntries){
        mJournalEntries=journalEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    @SuppressWarnings("WeakerAccess")
    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView journalDescView, updatedAtView,categoryRatingView;

        public JournalViewHolder (View itemView){
            super(itemView);
            journalDescView=itemView.findViewById(R.id.journalDescription);
            updatedAtView=itemView.findViewById(R.id.journalDate);
            categoryRatingView=itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int objectId=mJournalEntries.get(getAdapterPosition()).getId();
            myItemClickListener.onItemClickListener(objectId);
        }
    }
}
