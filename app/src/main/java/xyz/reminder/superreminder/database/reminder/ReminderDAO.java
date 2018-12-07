package xyz.reminder.superreminder.database.reminder;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import xyz.reminder.superreminder.database.ReminderDbManager;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderDAO {

    public static final String TABLE_NAME = Reminder.TABLE_NAME;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    TIME + " INTEGER);";

    private ReminderDbManager dbManager;

    public ReminderDAO(ReminderDbManager dbManager) {
        this.dbManager = dbManager;
    }

    public Reminder select(long id) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] { ID, NAME, TIME },
                ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null
        );

        if(cursor == null)
            return null;

        cursor.moveToFirst();

        Reminder reminder = createReminder(cursor);

        cursor.close();
        db.close();
        return reminder;
    }

    public List<Reminder> selectAll() {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] { ID, NAME, TIME },
                null,
                null,
                null,
                null,
                null
        );

        if(cursor == null)
            return null;

        List<Reminder> reminderList = new ArrayList<>(5);

        cursor.moveToFirst();
        do {
            reminderList.add(createReminder(cursor));
        } while(cursor.moveToNext());

        cursor.close();
        db.close();
        return reminderList;
    }

    public long insert(Reminder record) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, record.getName());
        values.put(TIME, record.getTime().getTime());

        long id = db.insert(TABLE_NAME, null, values);

        db.close();
        return id;
    }

    public long delete(Reminder record) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        long id = db.delete(
                TABLE_NAME,
                ID + "=?",
                new String[] { String.valueOf(record.getId()) });

        db.close();
        return id;
    }

    public long update(Reminder record) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, record.getName());
        values.put(TIME, record.getTime().getTime());

        long id = db.update(
                Reminder.TABLE_NAME,
                values,
                ID + "=?",
                new String[] { String.valueOf(record.getId())} );

        db.close();
        return id;
    }

    private Reminder createReminder(Cursor cursor) {
        Reminder reminder = new Reminder(
                cursor.getLong(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(NAME)),
                new Timestamp(cursor.getLong(cursor.getColumnIndex(TIME)))
        );

        return reminder;
    }
}
