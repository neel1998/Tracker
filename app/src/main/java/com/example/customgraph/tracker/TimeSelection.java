package com.example.customgraph.tracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeSelection extends AppCompatActivity {

    int hour=23,min=59,id=0;
    DatabaseHelper myDb;
    TextView time_txt;
    boolean checked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_selection);
        time_txt=(TextView)findViewById(R.id.time_txt);

        myDb=new DatabaseHelper(this);

        Cursor res=myDb.getTime();
        if(res.getCount()>0){
            if(res!=null && res.moveToFirst()) {
                String text="Your current notification time is " + Integer.parseInt(res.getString(res.getColumnIndex("hour"))) + ":"
                        + Integer.parseInt(res.getString(res.getColumnIndex("min")));
                time_txt.setText(text);
                id = Integer.parseInt(res.getString(res.getColumnIndex("ID")));
                checked = true;
            }
        }
        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour=i;
                min=i1;
            }
        });

        Button set_time=(Button) findViewById(R.id.set_time_btn);
        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TimeSelection.this,MainActivity.class);
                if(checked){
                    myDb.updateTime(id,hour,min);
                }
                else{
                    myDb.addTime(hour,min);
                }
                startActivity(i);
            }
        });
    }
}
