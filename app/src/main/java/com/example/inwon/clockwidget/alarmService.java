package com.example.inwon.clockwidget;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;


public class alarmService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        update();
        return super.onStartCommand(intent, flags, startId);

    }

    private int[] iiten = {R.id.ja, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five1, R.id.five2, R.id.six1, R.id.six2, R.id.seven1, R.id.seven2,
            R.id.eight1, R.id.eight2, R.id.nine1, R.id.nine2, R.id.ten, R.id.han, R.id.du};
    private int[] iimin_units = {R.id.ill, R.id.yee2, R.id.sam2, R.id.sa2, R.id.oh3, R.id.yuk, R.id.chil, R.id.pal, R.id.gu};
    private int[] iimin_tens = {R.id.sip, R.id.yee, R.id.sam, R.id.sa, R.id.oh};
    private String min;
    RemoteViews views;
    private void update(){
        String time = getTime();
        String hour = getHour();
        min = getMin();
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName timewidget = new ComponentName(this,WidgetProvider.class);

        int[] ids = manager.getAppWidgetIds(timewidget);
        final int N = ids.length;
        for(int i = 0;i<N;i++) {
            int awID = ids[i];
            views = new RemoteViews(getPackageName(), R.layout.widget_layout);
            SharedPreferences pref = getSharedPreferences("widget_clock",MODE_PRIVATE);
            String name = pref.getString("name","");
            String text = pref.getString("text","");
            if(!name.equals("") && !text.equals("")) {
                views.setTextViewText(R.id.widgettext, name + "아, " + text);
            }
            views.setTextColor(R.id.si, Color.WHITE);
            views.setTextColor(R.id.bun, Color.WHITE);
            // HOUR SET
            int ampm = Integer.parseInt(hour);

            if (ampm >= 12) {
                views.setTextColor(R.id.PM, Color.WHITE);
                views.setTextColor(R.id.AM, Color.GRAY);
            } else {
                views.setTextColor(R.id.PM, Color.GRAY);
                views.setTextColor(R.id.AM, Color.WHITE);
            }

            if (hour.equals("12") && min.equals("00")) {
                views.setTextColor(R.id.jung, Color.WHITE);
                views.setTextColor(R.id.oh2, Color.WHITE);
                views.setTextColor(R.id.si, Color.GRAY);
                views.setTextColor(R.id.bun, Color.WHITE);
            } else if (hour.equals("24") && min.equals("00")) {
                views.setTextColor(R.id.ja, Color.WHITE);
                views.setTextColor(R.id.oh2, Color.WHITE);
                views.setTextColor(R.id.si, Color.GRAY);
                views.setTextColor(R.id.bun, Color.GRAY);
            } else {
                views.setTextColor(R.id.jung, Color.GRAY);
                views.setTextColor(R.id.oh2, Color.GRAY);
                views.setTextColor(R.id.si, Color.GRAY);
                views.setTextColor(R.id.bun, Color.GRAY);

                for (int j = 0; j < 13; j++) { //0~12
                    int i_tens = Integer.parseInt(hour);
                    views.setTextColor(R.id.si,Color.WHITE);
                    if (i_tens >= 12) i_tens -= 12;
                    if (i_tens == 12 || i_tens == 00) {
                        inithourtext();
                        views.setTextColor(iiten[15], Color.WHITE);
                        views.setTextColor(iiten[17], Color.WHITE);
                        break;
                    }
                    if (i_tens == j) {
                        inithourtext();
                        if (i_tens < 5) {
                            views.setTextColor(iiten[j], Color.WHITE);
                            break;
                        } else if (i_tens >= 5) { //5시
                            if (i_tens == 10) {
                                views.setTextColor(iiten[15], Color.WHITE);
                                break;
                            } else if (i_tens == 11) {
                                views.setTextColor(iiten[15], Color.WHITE);
                                views.setTextColor(iiten[16], Color.WHITE);
                                break;
                            } else if (i_tens == 12) {
                                views.setTextColor(iiten[15], Color.WHITE);
                                views.setTextColor(iiten[17], Color.WHITE);
                                break;
                            } else {
                                int a = i_tens - 5;
                                views.setTextColor(iiten[j + a], Color.WHITE);
                                views.setTextColor(iiten[j + a + 1], Color.WHITE);
                                break;
                            }
                        }
                    }
                }
                setMin();
            }
            manager.updateAppWidget(awID,views);
        }
    }
    private void setMin() {
        String min_ten = min.substring(0, 1);
        String min_unit = min.substring(1, min.length());
        int imin_ten = Integer.parseInt(min_ten);
        int imin_unit = Integer.parseInt(min_unit);
        for (int i = 0; i <= 5; i++) {
            if (imin_ten == i) {
                initmintext();
                if (imin_ten == 0) {
                    break;
                } else if (imin_ten == 5) {
                    views.setTextColor(iimin_tens[4],Color.WHITE);
                    views.setTextColor(iimin_tens[0],Color.WHITE);
                    break;
                } else if (imin_ten == 1) {
                    views.setTextColor(iimin_tens[0],Color.WHITE);
                    break;
                } else {
                    views.setTextColor(iimin_tens[0],Color.WHITE);
                    views.setTextColor(iimin_tens[i-1],Color.WHITE);
                    break;
                }
            }
        }
        for (int j = 0; j <= 9; j++) {
            views.setTextColor(R.id.bun,Color.WHITE);
            initmin_utext();
            if (imin_unit == j) {
                if (imin_unit == 0) {
                    if(imin_ten ==0) {
                        views.setTextColor(R.id.bun, Color.GRAY);
                    }
                    break;
                } else if (imin_unit == 9) {
                    views.setTextColor(iimin_units[8],Color.WHITE);
                    break;
                } else {
                    views.setTextColor(iimin_units[j-1],Color.WHITE);
                    break;
                }
            }
        }
    }


    private void inithourtext() {
        views.setTextColor(R.id.si,Color.WHITE);
        for (int i = 0; i < iiten.length; i++) {
            views.setTextColor(iiten[i],Color.GRAY);
        }
    }

    private void initmintext() {
        for (int i = 0; i < iimin_tens.length; i++) {
            views.setTextColor(iimin_tens[i],Color.GRAY);
        }
    }

    private void initmin_utext() {
        views.setTextColor(R.id.bun,Color.WHITE);
        for (int i = 0; i < iimin_units.length; i++) {
            views.setTextColor(iimin_units[i],Color.GRAY);
        }
    }

    public String getTime(){
        String time = null;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
        time = timeformat.format(date);
        return time;
    }
    public String getHour(){
        String time = null;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat timeformat = new SimpleDateFormat("HH");
        time = timeformat.format(date);
        return time;
    }
    public String getMin(){
        String time = null;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat timeformat = new SimpleDateFormat("mm");
        time = timeformat.format(date);
        return time;
    }


}
