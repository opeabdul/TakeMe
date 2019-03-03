package com.example.opeyemi.takeme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    private MaterialEditText mEditPhone, mEditPassword, mEditName;
    private Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEditPhone = findViewById(R.id.edit_phone_signup);
        mEditPassword = findViewById(R.id.edit_password_signup);
        mEditName = findViewById(R.id.edit_name_signup);
        btnSignUp = findViewById((R.id.btn_sign_up));
        TextView loginTextView = findViewById(R.id.login_textView);
        CheckBox termOfServiceCheckBox = findViewById(R.id.terms_of_service_checkBox);


        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
        });



        termOfServiceCheckBox.setChecked(false);
        termOfServiceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                btnSignUp.setClickable(b);
            }
        });


        //intialize Firebase
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = mDatabase.getReference("user");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mProgressDialog = new ProgressDialog(SignUpActivity.this);
                mProgressDialog.setMessage("Signing up...");
                mProgressDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        if (dataSnapshot.child(mEditPhone.getText().toString()).exists())
                        {

                            mProgressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,
                                    "Phone number already registered",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            mProgressDialog.dismiss();
                            User user = new User(mEditName.getText().toString(),
                                    mEditPassword.getText().toString(), mEditPhone.getText().toString(),null);
                            table_user.child(mEditPhone.getText().toString()).setValue(user);
                            Common.currentUser = user;
                            Toast.makeText(SignUpActivity.this,
                                    "Sign up successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            SignUpActivity.this.finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();
        if(Common.currentUser != null){
            startActivity(new Intent (SignUpActivity.this, MainActivity.class));
        }
    }
}
