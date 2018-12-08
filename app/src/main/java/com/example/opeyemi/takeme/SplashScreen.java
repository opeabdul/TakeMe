package com.example.opeyemi.takeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    TextView textSloganTextView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        btnSignUp = (Button) findViewById(R.id.btn_sign_up_splash);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in_splash);

        textSloganTextView = (TextView) findViewById(R.id.textSlogan);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SplashScreen.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SplashScreen.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
