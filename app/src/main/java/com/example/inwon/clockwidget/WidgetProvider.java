package com.example.inwon.clockwidget;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Created by inwon on 2017-02-14.
 */

public class WidgetProvider extends AppWidgetProvider{

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        setTime(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }


    public void setTime(Context context){
        PendingIntent service = null;
        final AlarmManager m = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        final Intent si = new Intent(context,alarmService.class);

        if(service == null){
            service = PendingIntent.getService(context,0,si,PendingIntent.FLAG_CANCEL_CURRENT);
        }
        m.setRepeating(AlarmManager.RTC,System.currentTimeMillis(),1000,service);
    }

}
