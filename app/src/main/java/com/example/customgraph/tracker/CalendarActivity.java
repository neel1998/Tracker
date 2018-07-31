package com.example.customgraph.tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        CalendarView cal=(CalendarView)findViewById(R.id.cal);
        Calendar c=Calendar.getInstance();
        SimpleDateFormat custom_format=new SimpleDateFormat("yyyy-MM-dd");
        final String cur_date=custom_format.format(c.getTime());
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                String[] dates=cur_date.split("-");
                Log.d("",""+dates);
                if(i<=Integer.parseInt(dates[0]) && (i1+1)<=Integer.parseInt(dates[1]) && i2<=Integer.parseInt(dates[2])){
                Log.d("",""+i2+i1+i);
                Intent j=new Intent(CalendarActivity.this,SingleDay.class);
                j.putExtra("Day",String.valueOf(i2));
                j.putExtra("Month",String.valueOf(i1+1));
                j.putExtra("Year",String.valueOf(i));
                j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(j);
                }
                else{
                    Toast.makeText(CalendarActivity.this,"You cant predict the future!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button h=(Button)findViewById(R.id.home);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CalendarActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
