package xyz.reminder.superreminder.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.controllers.StyleController;
import xyz.reminder.superreminder.database.ReminderDbManager;
import xyz.reminder.superreminder.fragments.AddReminderFragment;
import xyz.reminder.superreminder.fragments.ListFragment;
import xyz.reminder.superreminder.fragments.SettingsFragment;
import xyz.reminder.superreminder.receivers.ConnectionReceiver;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int fragmentId;
    private ReminderDbManager reminderDb;
    private StyleController styleController;
    Fragment fragment;
//    TimerService timerService;
//    ServiceConnection serviceConn;cd

    private Map<Integer, Integer> backgroundColorMap;
    private Map<Integer, Integer> imageColorMap;

    public MainActivity() {
        super();
        fillColorMaps();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        styleController = new StyleController(false, getSharedPreferences().getInt("colors", 0));

        if (savedInstanceState == null) {
            changeFragment(new ListFragment());
        }
        styleController.applyColors(this, backgroundColorMap, null, imageColorMap);

        reminderDb = new ReminderDbManager(this);
        Class.activity = this;
        registerReceiver(new ConnectionReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public SharedPreferences getSharedPreferences() {
        return super.getSharedPreferences("DATA", Context.MODE_PRIVATE);
    }

    void onToolbarItemClick(View view) {

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

    public ReminderDbManager getDb() {
        return reminderDb;
    }

    public StyleController getStyleController() {
        return styleController;
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();

        fm.popBackStack();
        fm.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
        this.fragment = fragment;
        fragmentId = fragment.getId();
    }

    public void retachFragment() {
        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

    private void fillColorMaps() {
        backgroundColorMap = new HashMap<>(1);
        backgroundColorMap.put(R.id.toolbar, StyleController.SECONDARY);
        backgroundColorMap.put(R.id.activity_layout, StyleController.BACKGROUND);

        imageColorMap = new HashMap<>(3);
        imageColorMap.put(R.id.bar_list, StyleController.TEXT_SECONDARY);
        imageColorMap.put(R.id.bar_addreminder, StyleController.TEXT_SECONDARY);
        imageColorMap.put(R.id.bar_settings, StyleController.TEXT_SECONDARY);
    }

    public Map<Integer, Integer> getBackgroundColorMap() {
        return backgroundColorMap;
    }

    public Map<Integer, Integer> getImageColorMap() {
        return imageColorMap;
    }

    public int getFragmentId() {
        return fragmentId;
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