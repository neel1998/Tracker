package com.example.customgraph.tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Integer hour,min,sec;
    DatabaseHelper myDb;
    Calendar calendar=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        Notify();
        DatabaseHelper myDb=new DatabaseHelper(this);
        ImageButton go=(ImageButton) findViewById(R.id.btn);
        ImageButton analysis_btn=(ImageButton) findViewById(R.id.analysis_btn);
        ImageButton time=(ImageButton) findViewById(R.id.time_btn);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,CalendarActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
//        Button Analysis_btn=(Button) findViewById(R.id.analysis_btn);
        analysis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AnalysisActivity.class);
                startActivity(i);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TimeSelection.class);
                startActivity(i);
            }
        });
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i=new Intent(MainActivity.this,Information.class);
                    startActivity(i);
            }
        });
    }
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    public void Notify(){

        Cursor res=myDb.getTime();
        if(res.getCount()>0){
            while (res.moveToNext()){
                hour=res.getInt(res.getColumnIndex("hour"));
                min=res.getInt(res.getColumnIndex("min"));
            }
        }
        else{
            hour=23;
            min=59;
//            sec=0;
            Toast.makeText(MainActivity.this,"Please Select the the notification time First\nDefault it is 23:59",Toast.LENGTH_SHORT).show();
        }

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);

        Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
        PendingIntent broadcast=PendingIntent.getBroadcast(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
    }
}
