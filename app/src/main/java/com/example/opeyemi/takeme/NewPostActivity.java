package com.example.opeyemi.takeme;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;
import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.Job;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.Date;


public class NewPostActivity extends BaseActivity {

    private ImageView mJobImageView;
    private MaterialEditText mTitleEditText;
    private MaterialEditText mDescriptionEditText;
    private MaterialEditText mAmountEditText;
    private MaterialEditText mAddressEditText;
    private MaterialEditText mAreaEditText;
    private MaterialEditText mCityEditText;
    private MaterialEditText mStateEditText;
    private CardView mJobCardView;
    private CheckBox mMakeCallCheckbox;


    private String mJobTitle;
    private String mJobDescription;
    private String mJobAmount;
    private String mJobAddress;
    private String mJobArea;
    private String mJobCity;
    private String mJobState;
    private String mAllowCall;



    FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    DatabaseReference jobDatabaseReference =  FirebaseDatabase.getInstance().getReference("job");


    private Uri filePath;
    private final int REQUEST_IMAGE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mJobImageView = findViewById(R.id.new_job_image_view);
        mTitleEditText = findViewById(R.id.job_title_editText);
        mDescriptionEditText = findViewById(R.id.job_description_editText);
        mAmountEditText = findViewById(R.id.amount_payable_edit_text);
        mAddressEditText = findViewById(R.id.address_edit_text);
        mAreaEditText = findViewById(R.id.area_edit_text);
        mCityEditText = findViewById(R.id.city_edit_text);
        mStateEditText = findViewById(R.id.state_edit_text);
        mJobCardView = findViewById(R.id.job_image_card_view);
        mMakeCallCheckbox = findViewById(R.id.make_a_call_checkbox);


        mJobCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data.getData() != null && data != null){

                filePath = data.getData();
            try{

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mJobImageView.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(NewPostActivity.this, MainActivity.class));
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();
        switch (menuID) {
            case R.id.create_job_post:
                if (checkEnteredDetails()) {
                    uploadImage();
                } else {
                    Toast.makeText(NewPostActivity.this,
                            "Please fill all necessary fields", Toast.LENGTH_SHORT).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadImage() {
        if (filePath != null){
            final StorageReference storageReference = mFirebaseStorage.getReference(Common.currentUser.getPhoneNumber())
                    .child(String.valueOf(System.currentTimeMillis()));
            UploadTask uploadTask = storageReference.putFile(filePath);

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            });

            task.addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    createNewPost(task.getResult().toString());
                }
            });





        }
    }

    private boolean checkEnteredDetails() {

        mJobTitle = mTitleEditText.getText().toString();
        mJobDescription = mDescriptionEditText.getText().toString();
        mJobAmount = mAmountEditText.getText().toString();
        mJobAddress = mAddressEditText.getText().toString();
        mJobCity = mCityEditText.getText().toString();
        mJobState = mStateEditText.getText().toString();
        mJobArea = mAreaEditText.getText().toString();

        if ( mJobTitle.equals("")|| mJobTitle == null) {
            return false;
        } else if (mJobDescription.equals("") || mJobDescription == null) {
            return false;
        } else if (mJobAmount.equals("") || mJobAmount == null) {
            return false;
        } else if (mJobAddress.equals("") || mJobAddress == null) {
            return false;
        }else if (mJobArea.equals("") || mJobArea == null) {
            return false;
        } else if (mJobCity.equals("") || mJobCity == null) {
            return false;
        } else if (mJobState.equals("") || mJobState == null) {
            return false;
        }

        return true;
    }

    private void createNewPost(String imageUrl) {
        //Create a new job from details of the edit texts.
        Date date = new Date();
        String day = DateFormat.format("dd", date).toString();
        String monthString = DateFormat.format("MMM", date).toString();
        String currentUserID = Common.currentUser.getPhoneNumber();

        Log.e("NewPostActivity","currentUserId: "+ currentUserID);

        Job job = new Job(mJobTitle, mJobDescription, imageUrl,"", mJobAmount, day, monthString, "1", currentUserID);

        //push job post details to the database
        jobDatabaseReference.orderByKey();
        jobDatabaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(job);

        Toast.makeText(NewPostActivity.this,"Job AD successfully created", Toast.LENGTH_SHORT).show();
        finish();

    }

    //overriding BaseActivity method
    public int getContentViewId() {
        return R.layout.activity_new_post;
    }

    public int getNavigationMenuItemId() {
        return R.id.navigation_add;
    }
}
