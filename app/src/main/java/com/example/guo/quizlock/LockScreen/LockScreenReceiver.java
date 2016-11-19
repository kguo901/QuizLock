package com.example.guo.quizlock.LockScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //detect screen off and boot completed to start lock screen
        if (action.equals(Intent.ACTION_SCREEN_OFF)
                || action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            start_lockscreen(context);
        }
    }

    /**
     * Start new lock screen activity
     * TODO: check that only one lock screen activity i
     * @param context
     */
    private void start_lockscreen(Context context) {
        Intent intent = new Intent(context, LockScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}