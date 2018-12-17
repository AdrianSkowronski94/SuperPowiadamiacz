package xyz.reminder.superreminder.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.controllers.StyleController;
import xyz.reminder.superreminder.database.ReminderDbManager;
import xyz.reminder.superreminder.database.reminder.Reminder;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddReminderFragment extends StyleFragment implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private Button saveButton;
    private EditText addName;
    private TextView timeText, dateText;
    private ImageButton timeChoose, dateChoose;

    private int year, month, day, hour, minute;
    private ReminderDbManager reminderDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_addreminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveButton = view.findViewById(R.id.saveButton);
        addName = view.findViewById(R.id.addName);
        timeText = view.findViewById(R.id.timeText);
        dateText = view.findViewById(R.id.dateText);
        timeChoose = view.findViewById(R.id.timeChoose);
        dateChoose = view.findViewById(R.id.dateChoose);

        reminderDb = new ReminderDbManager(getContext());

        timeChoose.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, true);
            timePickerDialog.show();

        });

        dateChoose.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.show();
        });



        saveButton.setOnClickListener(v -> {

            if(addName.getText().toString().matches("") ||
                    dateText.getText().equals(getContext().getString(R.string.no_date)) ||
                    timeText.getText().equals(getContext().getString(R.string.no_time)))
            {
                Toast.makeText(getContext(), getContext().getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
            }

            else
            {
                reminderDb.getReminderDAO().insert(new Reminder(addName.getText().toString(),
                        new Timestamp(year, month, day, hour, minute, 0,0)));

                addName.setText("");
                dateText.setText(getContext().getString(R.string.no_date));
                timeText.setText(getContext().getString(R.string.no_time));
            }
        });
    }

    @Override
    protected void fillColorMaps() {
        backgroundColorMap = new HashMap<>(2);
        backgroundColorMap.put(R.id.fragment_layout, StyleController.BACKGROUND);
        backgroundColorMap.put(R.id.timeChoose, StyleController.BACKGROUND);
        backgroundColorMap.put(R.id.saveButton, StyleController.PRIMARY);
        backgroundColorMap.put(R.id.dateChoose, StyleController.BACKGROUND);

        textColorMap = new HashMap<>(1);
        textColorMap.put(R.id.addreminder_text, StyleController.TEXT_PRIMARY);
        textColorMap.put(R.id.addName, StyleController.TEXT_PRIMARY);
        textColorMap.put(R.id.timeText, StyleController.TEXT_PRIMARY);
        textColorMap.put(R.id.dateText, StyleController.TEXT_PRIMARY);
        textColorMap.put(R.id.saveButton, StyleController.TEXT_SECONDARY);

        imageColorMap = new HashMap<>(1);
        imageColorMap.put(R.id.timeChoose, StyleController.SECONDARY);
        imageColorMap.put(R.id.dateChoose, StyleController.SECONDARY);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        dateText.setText(String.format("%02d", dayOfMonth) + "/"
                         + String.format("%02d", month) + "/"
                         + String.valueOf(year));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

        timeText.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
    }
}
