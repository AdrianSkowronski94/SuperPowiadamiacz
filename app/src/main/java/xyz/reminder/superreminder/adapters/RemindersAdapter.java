package xyz.reminder.superreminder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;

import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.database.reminder.Reminder;

/**
 * Created by Joanna on 09.12.2018.
 */

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.RemindersViewHolder> {

    private List<Reminder> remindersList;
    private IRecyclerViewClickListener listener;

    public class RemindersViewHolder extends RecyclerView.ViewHolder{

        public TextView reminderName, reminderTime, reminderDate;

        public RemindersViewHolder(View itemView) {
            super(itemView);

            reminderName = itemView.findViewById(R.id.reminderName);
            reminderTime = itemView.findViewById(R.id.reminderTime);
            reminderDate = itemView.findViewById(R.id.reminderDate);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClicked(getAdapterPosition());
                    return false;
                }
            });

        }
    }

    public RemindersAdapter(List<Reminder> remindersList, IRecyclerViewClickListener listener)
    {
        this.remindersList = remindersList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RemindersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reminder_card, viewGroup, false);
        return new RemindersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindersViewHolder remindersViewHolder, int i) {
        Reminder reminder = remindersList.get(i);
        remindersViewHolder.reminderName.setText(reminder.getName());
        Timestamp ts = reminder.getTime();
        remindersViewHolder.reminderTime.setText(String.format("%02d", ts.getHours()) + ":" + String.format("%02d",ts.getMinutes()));// String.valueOf(ts.getMinutes()));
        remindersViewHolder.reminderDate.setText(String.format("%02d", ts.getDate()) + "/" +
                                                 String.format("%02d", ts.getMonth() + 1)+ "/" +
                                                 String.valueOf(ts.getYear()));
    }
    @Override
    public int getItemCount() {
        return remindersList.size();
    }

}
