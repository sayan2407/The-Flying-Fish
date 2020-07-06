package com.example.thefirstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    int score;

    TextView s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        s=findViewById(R.id.score);
        Intent intent = getIntent();
        Bundle b= intent.getExtras();
        score = b.getInt("score");
        s.setText("Your score : "+score);
    }

    public void play_again(View view) {

        startActivity(new Intent(getApplicationContext(),SecondActivity.class));
        finish();
    }
}