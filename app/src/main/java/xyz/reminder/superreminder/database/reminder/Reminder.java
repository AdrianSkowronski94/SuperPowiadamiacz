package xyz.reminder.superreminder.database.reminder;

import java.sql.Timestamp;

public class Reminder {

    public static final String TABLE_NAME = "reminder";

    private long id;
    private String name;
    private Timestamp time;

    public Reminder() {
    }

    public Reminder(String name, Timestamp time) {
        this.name = name;
        this.time = time;
    }

    Reminder(long id, String name, Timestamp time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Timestamp getTime() { return time; }

    void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTime(Timestamp time) { this.time = time; }
}
