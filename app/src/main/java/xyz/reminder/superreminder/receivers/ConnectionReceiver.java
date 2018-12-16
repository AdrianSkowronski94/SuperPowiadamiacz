package xyz.reminder.superreminder.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import xyz.reminder.superreminder.activities.MainActivity;
import xyz.reminder.superreminder.controllers.StyleController;
import xyz.reminder.superreminder.activities.Class;

/**
 * Created by Joanna on 14.12.2018.
 */

public class ConnectionReceiver extends BroadcastReceiver {

    private NetworkInfo networkInfo;
    private StyleController styleController;

    @Override
    public void onReceive(Context context, Intent intent) {
        styleController = Class.activity.getStyleController();

        if(intent.getExtras()!= null)
        {
            networkInfo = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED)
            {
                styleController.setOnline();
                System.out.println("online");
            }
            else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE))
            {
                styleController.setOffline();
                System.out.println("offline");
            }

            styleController.applyColorsRefresh(Class.activity,
                    Class.activity.getBackgroundColorMap(), null,
                    Class.activity.getImageColorMap());
        }
    }
}
