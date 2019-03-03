package com.example.opeyemi.takeme;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        final TextView rateValueTextView = findViewById(R.id.rate_value_textView);

        RatingBar rb = findViewById(R.id.rating_bar);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValueTextView.setText(String.valueOf(ratingBar.getRating()));
            }
        });

        final EditText commentEditText = findViewById(R.id.comment);

        Button rateButton = findViewById(R.id.rate_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("rate", rateValueTextView.getText().toString());
                returnIntent.putExtra("comment", commentEditText.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
