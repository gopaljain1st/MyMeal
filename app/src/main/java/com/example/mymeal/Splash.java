package com.example.mymeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity
{
    ImageView iv1,iv2,iv3;
    Animation fromBottom,fromTop,blink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        iv3=findViewById(R.id.iv3);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        iv3.setAnimation(fromBottom);
        fromTop=AnimationUtils.loadAnimation(this,R.anim.fromtop);
        iv1.setAnimation(fromTop);
        blink=AnimationUtils.loadAnimation(this,R.anim.blink);
        iv2.setAnimation(blink);

        //getSupportActionBar().hide();
        new MyThread().start();
    }
    class MyThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try {
                Thread.sleep(5000);
               Intent in=new Intent(Splash.this,Welcome.class);
               startActivity(in);
                finish();
            }
            catch (Exception e)
            {

            }
        }
    }
}

