package com.example.thefirstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.thefirstgame.JavaClass.FlyingFishView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    private FlyingFishView gameview;

    private Handler handler = new Handler();
    private final static long longinterval =30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_second);
        gameview = new FlyingFishView(this);
        setContentView(gameview);

        Timer timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                         gameview.invalidate();
                    }
                });
            }
        },0,longinterval);

    }
}