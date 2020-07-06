package com.example.thefirstgame.JavaClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.thefirstgame.GameOver;
import com.example.thefirstgame.R;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private int fishX=10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth,canvasHeight;
    private Bitmap background;
    private Paint scorepaint = new Paint();
    private Bitmap life[] = new Bitmap[2];
    Rect rect ;
    int dWidth,dHeight;
    private  boolean touch =false;
    private  int yellowX,yellowY,yellowSpeed=14;
    private Paint yellowPaint = new Paint();
    private  int greenX,greenY,greenSpeed=17;
    private  Paint greenPaint = new Paint();

    private  int redX,redY,redSpeed=20;
    private Paint redPaint = new Paint();
    private  int score,lifeCounterFish;

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(50);
        scorepaint.setTypeface(Typeface.DEFAULT);
        scorepaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishY=550;
        score = 0;
        lifeCounterFish = 3;
        Point size = new Point();
        display.getSize(size);
        dWidth=size.x;
        dHeight=size.y;
        rect=new Rect(0,0,dWidth,dHeight);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawBitmap(background,0,0,null);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvas.drawBitmap(background,null,rect,null);
  //      canvas.drawBitmap(fish,0,0,null);
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight -fish[0].getHeight() * 3;
        fishY=fishY+fishSpeed;
        if (fishY<minFishY)
        {
            fishY=minFishY;
        }
        if (fishY>maxFishY)
        {
            fishY=maxFishY;
        }
        fishSpeed=fishSpeed+2;
        if (touch)
        {
          canvas.drawBitmap(fish[1],fishX,fishY,null);
          touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }
        yellowX = yellowX - yellowSpeed ;

        if (hitBallChecker(yellowX,yellowY))
        {
            score +=10 ;
            yellowX =-100 ;
        }
        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21 ;
            yellowY = (int)Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY ;
        }
        canvas.drawCircle(yellowX,yellowY,30,yellowPaint);

        //Green Ball
        greenX = greenX - greenSpeed ;

        if (hitBallChecker(greenX,greenY))
        {
            score +=20 ;
            greenX =-100 ;
        }
        if (greenX < 0)
        {
            greenX = canvasWidth + 21 ;
            greenY = (int)Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY ;
        }
        canvas.drawCircle(greenX,greenY,30,greenPaint);

        // Red Ball
        redX = redX- redSpeed ;

        if (hitBallChecker(redX,redY))
        {
         //  score=score-score;
            redX =-100 ;
            lifeCounterFish--;

            if (lifeCounterFish==0)
            {
                Bundle b = new Bundle();
                b.putInt("score",score);
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_LONG).show();
                Intent gameOver =new Intent(getContext(), GameOver.class);
                gameOver.putExtras(b);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameOver);
            }
        }
        if (redX < 0)
        {
            redX = canvasWidth + 21 ;
            redY = (int)Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY ;
        }
        canvas.drawCircle(redX,redY,30,redPaint);

        canvas.drawText("Score : "+score,20,60,scorepaint);
        for (int i=0;i<3;i++)
        {
            int x =(int) (400 + life[0].getWidth() *1.5 * i);
            int y = 30;
            if (i<lifeCounterFish) {
                canvas.drawBitmap(life[0], x ,y, null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }


     //   canvas.drawBitmap(life[0],400,10,null);
     //   canvas.drawBitmap(life[0],500,10,null);
      //  canvas.drawBitmap(life[0],600,10,null);
    }

    public boolean hitBallChecker(int x , int y)
    {
        if (fishX < x && x<(fishX+fish[0].getWidth()) && fishY < y && y< (fishY + fish[0].getHeight()))
        {
            return true ;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            touch=true;
            fishSpeed = -24;
        }
        return true;
    }
}
