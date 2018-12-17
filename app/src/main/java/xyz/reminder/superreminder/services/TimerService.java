package xyz.reminder.superreminder.services;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;
import xyz.reminder.superreminder.R;
import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.controllers.StyleController;
import xyz.reminder.superreminder.database.ReminderDbManager;
import xyz.reminder.superreminder.database.reminder.Reminder;
import xyz.reminder.superreminder.database.reminder.ReminderDAO;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service  {

    IBinder mBinder = new MyBinder();
    Timer remindTimer = new Timer();
    List<Reminder> reminders;
    MainActivity activity;
    ReminderDAO reminderDAO;
    Ringtone ringtone;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {

        int period = getResources().getInteger(R.integer.timer_reminder_period);
        TimerService service = this;
        remindTimer.schedule(new TimerTask() {

            Handler handler = new Handler();
            Ringtone defaultRingtone = RingtoneManager.getRingtone(activity,
                    Settings.System.DEFAULT_RINGTONE_URI);

            @Override
            public void run() {
                try {
                    reminders = reminderDAO.selectAll();
                    Timestamp t = new Timestamp(Calendar.getInstance().getTime().getTime());
                    t.setYear(t.getYear() + 1900);

                    for (Reminder reminder : reminders) {
                        Timestamp time = reminder.getTime();
                        if (time.getYear() == t.getYear() &&
                            time.getMonth() == t.getMonth() &&
                            time.getDate() == t.getDate() &&
                            time.getHours() == t.getHours() &&
                            time.getMinutes() == t.getMinutes()) {

                            Intent notificationIntent = new Intent(activity, MainActivity.class);
                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent intent = PendingIntent.getActivity(activity, 0, notificationIntent, 0);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity)
                                    .setSmallIcon(R.drawable.ic_date)
                                    .setContentTitle(reminder.getName())
                                    .setContentIntent(intent)
                                    .setSound(RingtoneManager.getActualDefaultRingtoneUri(activity.getApplicationContext(), RingtoneManager.TYPE_RINGTONE))
                                    .setPriority(Notification.PRIORITY_HIGH) //private static final PRIORITY_HIGH = 5;
                                    .setContentText(getString(R.string.notification_description))
                                    .setAutoCancel(true)
                                    .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
                            NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(0, mBuilder.build());

                            reminderDAO.delete(reminder);
                            TimerService.this.activity.retachFragment();

                        } else if  (time.getTime() + period < t.getTime()) {
                            reminderDAO.delete(reminder);
                            activity.retachFragment();
                        }
                    }



                } catch (NullPointerException e) {
                }
            }
        }, 0, period);
    }

    @Override
    public boolean onUnbind(Intent intent) {
       return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    public class MyBinder extends Binder {
        public TimerService getService(MainActivity activity) {
            TimerService.this.reminderDAO = activity.getDb().getReminderDAO();
            TimerService.this.activity = activity;
            Uri currentRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(activity.getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
            TimerService.this.ringtone = RingtoneManager.getRingtone(activity, currentRintoneUri);

            return TimerService.this;
        }
    }
}
