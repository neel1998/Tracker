package com.example.customgraph.tracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SingleDay extends AppCompatActivity {

    DatabaseHelper myDb;
    Integer day,month,year,id;
    String exp="";
    String insert_head="\nHow did you study today?";
    String update_head="\nChange of mind..Update your response";
    boolean checked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_day);
        Intent i=getIntent();
        myDb=new DatabaseHelper(this);
      //  myDb.removetable();

        TextView txt=(TextView)findViewById(R.id.txt);
        TextView exp_txt=(TextView)findViewById(R.id.exp);
        TextView head_txt=(TextView)findViewById(R.id.head);

        String date=i.getStringExtra("Day")+"-"+i.getStringExtra("Month")+"-"+i.getStringExtra("Year");
        txt.setText(date);

        day=Integer.parseInt(i.getStringExtra("Day"));
        year=Integer.parseInt(i.getStringExtra("Year"));
        month=Integer.parseInt(i.getStringExtra("Month"));

        Cursor res=myDb.SingleData(day,month,year);
        if(res.getCount()>0) {
            if(res!=null && res.moveToFirst()){
            String result="You have previosly rated this day as "+res.getString(res.getColumnIndex("exp"));
            id=res.getInt(res.getColumnIndex("ID"));
            exp_txt.setText(result);
            checked=true;
            }
        }

        if(checked){
            head_txt.setText(update_head);
        }
        else{
            head_txt.setText(insert_head);
        }
        final RadioButton good_btn=(RadioButton) findViewById(R.id.good_btn);
        final RadioButton avg_btn=(RadioButton) findViewById(R.id.avg_btn);
        final RadioButton bad_btn=(RadioButton) findViewById(R.id.bad_btn);

        Button go_btn=(Button) findViewById(R.id.go_btn);


        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(good_btn.isChecked()){
                    exp="Good";
                }
                else if(avg_btn.isChecked()){
                    exp="Average";
                }
                else if(bad_btn.isChecked()){
                    exp="Bad";
                }
                if(!checked){
                myDb.addData(day,month,year,exp);
                Toast.makeText(SingleDay.this,"Response Recoreded",Toast.LENGTH_SHORT).show();
                }
                else{
                    //update
                    myDb.updateData(id,exp);
                    Toast.makeText(SingleDay.this,"Response Updated",Toast.LENGTH_SHORT).show();
                }
                Intent i=new Intent(SingleDay.this,CalendarActivity.class);
                startActivity(i);
            }
        });
        Button home_btn=(Button) findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SingleDay.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
