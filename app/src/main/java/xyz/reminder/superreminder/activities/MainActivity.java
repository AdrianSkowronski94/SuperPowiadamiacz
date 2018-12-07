package xyz.reminder.superreminder.activities;

import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.database.ReminderDbManager;
import xyz.reminder.superreminder.database.reminder.Reminder;
import xyz.reminder.superreminder.fragments.AddReminderFragment;
import xyz.reminder.superreminder.fragments.ListFragment;
import xyz.reminder.superreminder.fragments.SettingsFragment;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int fragmentId;
    ReminderDbManager reminderDb;
//    TimerService timerService;
//    ServiceConnection serviceConn;
    long ix;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            changeFragment(new ListFragment());
        }

        reminderDb = new ReminderDbManager(this);

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
Reminder reminder = new Reminder("Nazwa", timestamp);
Reminder reminder2 = new Reminder("Nazwa2", timestamp);

    ix = reminderDb.getReminderDAO().insert(reminder);
            reminderDb.getReminderDAO().insert(reminder2);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return super.getSharedPreferences(name, mode);
    }

    void onToolbarItemClick(View view) {
    System.out.println("############### select");
    Reminder r = reminderDb.getReminderDAO().select(ix);
    System.out.println(r.getId() + "\n" + r.getName() + "\n" + r.getTime());

    System.out.println("############### update (compare ids)");
    r.setName("Inne imie");
    long ii = reminderDb.getReminderDAO().update(r);
    System.out.println(ii);
    System.out.println(ix);

    System.out.println("############### select after update");
    r = reminderDb.getReminderDAO().select(ix);
    System.out.println(r.getId() + "\n" + r.getName() + "\n" + r.getTime());

    System.out.println("############### selectAll");
    List<Reminder> rr = reminderDb.getReminderDAO().selectAll();
    for(Reminder ri: rr)
        System.out.println(ri.getId() + "\n" + ri.getName() + "\n" + ri.getTime());

    System.out.println("############### delete (compare ids)");
    ii = reminderDb.getReminderDAO().delete(r);
    System.out.println(ii);

    System.out.println("############### selectAll after delete");
    rr = reminderDb.getReminderDAO().selectAll();
    for(Reminder ri: rr)
        System.out.println(ri.getId() + "\n" + ri.getName() + "\n" + ri.getTime());
    System.out.println("###############");
        switch(view.getId()) {
            case R.id.bar_list:
                changeFragment(new ListFragment());
                break;
            case R.id.bar_addreminder:
                changeFragment(new AddReminderFragment());
                break;
            case R.id.bar_settings:
                changeFragment(new SettingsFragment());
                break;
        }
    }

    void changeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();

        fm.popBackStack();
        fm.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
        fragmentId = fragment.getId();
    }
}

/////////////////// Database test ////////////////////

//// Uzupełnienie tabeli
//Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
//Reminder reminder = new Reminder("Nazwa", timestamp);
//Reminder reminder2 = new Reminder("Nazwa2", timestamp);
//
//    reminderDb.getReminderDAO().insert(reminder);
//            reminderDb.getReminderDAO().insert(reminder2);


//// Test działania ReminderDao
//public void test() { // ix to dowolne id w tabeli
//    System.out.println("############### select");
//    Reminder r = reminderDb.getReminderDAO().select(ix);
//    System.out.println(r.getId() + "\n" + r.getName() + "\n" + r.getTime());
//
//    System.out.println("############### update (compare ids)");
//    r.setName("Inne imie");
//    long ii = reminderDb.getReminderDAO().update(r);
//    System.out.println(ii);
//    System.out.println(ix);
//
//    System.out.println("############### select after update");
//    r = reminderDb.getReminderDAO().select(ix);
//    System.out.println(r.getId() + "\n" + r.getName() + "\n" + r.getTime());
//
//    System.out.println("############### selectAll");
//    List<Reminder> rr = reminderDb.getReminderDAO().selectAll();
//    for(Reminder ri: rr)
//        System.out.println(ri.getId() + "\n" + ri.getName() + "\n" + ri.getTime());
//
//    System.out.println("############### delete (compare ids)");
//    ii = reminderDb.getReminderDAO().delete(r);
//    System.out.println(ii);
//
//    System.out.println("############### selectAll after delete");
//    rr = reminderDb.getReminderDAO().selectAll();
//    for(Reminder ri: rr)
//        System.out.println(ri.getId() + "\n" + ri.getName() + "\n" + ri.getTime());
//    System.out.println("###############");
//}