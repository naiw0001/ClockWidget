package com.example.inwon.clockwidget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetConfig extends AppCompatActivity{

    int widgetid;
    EditText nameedit,textedit;
    RemoteViews remoteViews;
    AppWidgetManager appWidgetManager;
    static String name="";
    static String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            widgetid = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        appWidgetManager = AppWidgetManager.getInstance(this);
        remoteViews = new RemoteViews(this.getPackageName(),R.layout.widget_layout);
        nameedit = (EditText)findViewById(R.id.nameedit);
        textedit = (EditText)findViewById(R.id.textedit);
    }

    public void okay(View view){
        if(nameedit.getText().toString().length() == 0 || textedit.getText().toString().length() ==0){
            Toast.makeText(getApplicationContext(),"빈칸을 채워주세요.",Toast.LENGTH_SHORT).show();
        }else {
            remoteViews.setTextViewText(R.id.widgettext, nameedit.getText().toString() + "아, " + textedit.getText().toString());
            name = nameedit.getText().toString();
            text = textedit.getText().toString();

            SharedPreferences pref = getSharedPreferences("widget_clock",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("name",name);
            editor.putString("text",text);
            editor.commit();

            appWidgetManager.updateAppWidget(widgetid, remoteViews);
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetid);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    }


}
