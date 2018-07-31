package com.example.customgraph.tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AnalysisActivity extends AppCompatActivity {

    Integer sy,sm,total_good=0,total_avg=0,total_bad=0;
    DatabaseHelper myDb;
    TextView analysis_txt;
    ImageView imageView;
    ArrayList<Integer> good_days,bad_days,avg_days;
    dataPoints[] points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);


        imageView=(ImageView)findViewById(R.id.img);


        myDb=new DatabaseHelper(this);

        analysis_txt=(TextView)findViewById(R.id.analysis_txt);

        good_days=new ArrayList<>();
        bad_days=new ArrayList<>();
        avg_days=new ArrayList<>();
        points=new dataPoints[1000];

        Spinner month_select=(Spinner)findViewById(R.id.month);
        String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
        ArrayAdapter<String> monthAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,months);
        month_select.setAdapter(monthAdapter);
        month_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sm=i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner year_select=(Spinner)findViewById(R.id.year);
        String[] years=new String[31];
        for(int x=2000;x<=2030;x++){
            years[x-2000]=String.valueOf(x);
        }
        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,years);
        year_select.setAdapter(yearAdapter);
        year_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sy=i+2000;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button get_analysis_btn=(Button)findViewById(R.id.get_analysis_btn);
        get_analysis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_avg=0;
                total_good=0;
                total_bad=0;
                good_days.clear();
                bad_days.clear();
                avg_days.clear();
                Cursor res=myDb.getMonthAnalysis(sm,sy);
                if(res.getCount()>0){
                    while (res.moveToNext()){
                        String exp=res.getString(res.getColumnIndex("exp"));
                        Integer day=Integer.parseInt(res.getString(res.getColumnIndex("day")));
                        if(exp.equals("Good")){
                            total_good++;
                            good_days.add(day);
                        }
                        else if(exp.equals("Average")){
                            total_avg++;
                            avg_days.add(day);
                        }
                        else if(exp.equals("Bad")){
                            total_bad++;
                            bad_days.add(day);
                        }
                    }
                }
                float total_days=total_avg+total_good+total_bad;
                if(total_days==0){
                    total_days=1;
                }
                float good_per=(total_good*100/total_days);
                float avg_per=(total_avg*100/total_days);
                float bad_per=(total_bad*100/total_days);
                String text="Total Days:  "+total_days+
                        "\nGood Days:  "+total_good+"  (   "+new DecimalFormat("##.##").format(good_per)+"%"+"   )"+
                        "\nAverage Days:  "+total_avg+"  (   "+new DecimalFormat("##.##").format(avg_per)+"%"+"   )"+
                        "\nBad Days:  "+total_bad+"  (   "+new DecimalFormat("##.##").format(bad_per)+"%"+"   )";
                analysis_txt.setText(text);
                draw(findViewById(R.id.horizontalView));
            }
        });

    }
    public void draw(View view){
        Bitmap bitmap=Bitmap.createBitmap(1300,view.getHeight(),Bitmap.Config.ARGB_8888);

        imageView.setImageBitmap(bitmap);
        Canvas canvas=new Canvas(bitmap);
        canvas.drawColor(
                Color.TRANSPARENT,
                PorterDuff.Mode.CLEAR);
        float width=view.getWidth();
        float height=view.getHeight();
        Paint paint=new Paint();
        paint.setTextSize(20);

        Paint good_color=new Paint();
        good_color.setColor(getResources().getColor(R.color.good));
        good_color.setStrokeWidth(10);
        good_color.setAntiAlias(true);


        Paint avg_color=new Paint();
        avg_color.setColor(getResources().getColor(R.color.average));
        avg_color.setStrokeWidth(10);
        avg_color.setAntiAlias(true);


        Paint bad_color=new Paint();
        bad_color.setColor(getResources().getColor(R.color.bad));
        bad_color.setStrokeWidth(10);
        bad_color.setAntiAlias(true);

        int j=1;
        int x=0;
        for(int i=0;j<=31;i+=40){
            canvas.drawText(String.valueOf(j),i,height,paint);
            if(good_days.contains(j)){
                canvas.drawLine(i,height/10,i+10,height-20,good_color);
                points[x]=(new dataPoints(i,height/10));
                x++;
            }
            if(avg_days.contains(j)){
                canvas.drawLine(i,height/5,i+10,height-20,avg_color);
                points[x]=(new dataPoints(i,height/5));
                x++;
            }
            if(bad_days.contains(j)){
                canvas.drawLine(i,height/2,i+10,height-20,bad_color);
                points[x]=(new dataPoints(i,height/2));
                x++;
            }
            j++;
        }
        for(int i=0;i<x-1;i++){
            canvas.drawLine(points[i].getMx(),points[i].getMy(),points[i+1].getMx(),points[i+1].getMy(),paint);
        }
    }

}
