package com.example.retrofitproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.EntryModule.LoginAct;

public class SplashScreenAct extends AppCompatActivity {
    ImageView imageView;
    Intent intent;

    PreferenceManger preferenceManger;
    Boolean logstatus ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.imageView);
        preferenceManger = new PreferenceManger(SplashScreenAct.this);
        logstatus  = preferenceManger.getLogInSession(VariableBag.SESSION_CHECK, false);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        imageView.startAnimation(fadeIn);


        Animation slideIn = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );

        slideIn.setDuration(2000);
        imageView.startAnimation(slideIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (logstatus) {
                    intent = new Intent(SplashScreenAct.this, HomeActivity.class);
                } else {
                    intent = new Intent(SplashScreenAct.this, LoginAct.class);
                }
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}