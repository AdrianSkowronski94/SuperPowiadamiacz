package xyz.reminder.superreminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import xyz.reminder.superreminder.database.reminder.Reminder;
import xyz.reminder.superreminder.database.reminder.ReminderDAO;

public class ReminderDbManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "reminder_db";
    ReminderDAO reminderDAO;

    public ReminderDbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.reminderDAO = new ReminderDAO(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReminderDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropDatabase(db);
        db.execSQL(ReminderDAO.CREATE_TABLE);
    }

    public void dropDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Reminder.TABLE_NAME);
    }

    public ReminderDAO getReminderDAO() {
        return this.reminderDAO;
    }
}
