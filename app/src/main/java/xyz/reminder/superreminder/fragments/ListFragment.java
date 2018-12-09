package xyz.reminder.superreminder.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.adapters.IRecyclerViewClickListener;
import xyz.reminder.superreminder.adapters.RemindersAdapter;
import xyz.reminder.superreminder.controllers.StyleController;
import xyz.reminder.superreminder.database.ReminderDbManager;
import xyz.reminder.superreminder.database.reminder.Reminder;

import java.util.HashMap;
import java.util.List;

public class ListFragment extends StyleFragment {

    private RemindersAdapter adapter;
    private List<Reminder> remindersList;
    private RecyclerView recyclerView;
    private ReminderDbManager reminderDb;
    private IRecyclerViewClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.reminderRecyclerView);
        reminderDb = new ReminderDbManager(getContext());
        remindersList = reminderDb.getReminderDAO().selectAll();
        listener = this::showActionsDialog;

        adapter = new RemindersAdapter(remindersList, listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void showActionsDialog(final int position) {

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    deleteReminder(position);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getContext().getString(R.string.delete)).setPositiveButton(getContext().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getContext().getString(R.string.cancel), dialogClickListener).show();

    }

    private void deleteReminder(int position) {
        reminderDb.getReminderDAO().delete(remindersList.get(position));
        remindersList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    protected void fillColorMaps() {
        backgroundColorMap = new HashMap<>(1);
        backgroundColorMap.put(R.id.fragment_layout, StyleController.BACKGROUND);

        textColorMap = new HashMap<>(1);
        textColorMap.put(R.id.list_text, StyleController.TEXT_PRIMARY);
    }
}

