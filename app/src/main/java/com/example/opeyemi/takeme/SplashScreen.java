package com.example.opeyemi.takeme;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        changeStatusBarColor();

        startAnimations();
    }

    private void changeStatusBarColor(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGB_888);
    }

    public void startAnimations(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        ImageView takeMeImageView = findViewById(R.id.splash);
        takeMeImageView.clearAnimation();
        takeMeImageView.startAnimation(anim);



        final Animation anim3 = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.translate);
        anim3.reset();
        final TextView sloganTextView = findViewById(R.id.textSlogan);
        sloganTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                sloganTextView.clearAnimation();
                sloganTextView.startAnimation(anim3);

            }
        },400);

        splashThread = new Thread(){
            @Override
            public void run() {
                try{

                    int waited = 0;
                    //splash screen pauseTime
                    while (waited < 6000){
                        sleep(100);
                        waited += 100;

                    }

                    Intent intent = new Intent(SplashScreen.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();

                } catch (InterruptedException e){
                    Log.i("SplashScreenActivity", e.getMessage());
                }finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splashThread.start();
    }
}
