package com.cou.cse.alamgir.smartdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherDetailActivity extends AppCompatActivity {
    TextView tvname,tvdesignation,tvemail,tvmobile;
    private String name=null, designation=null,email=null,mobile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        tvname= (TextView) findViewById(R.id.name);
        tvdesignation= (TextView) findViewById(R.id.designation);
        tvemail= (TextView) findViewById(R.id.email);
       tvmobile= (TextView) findViewById(R.id.tmobile);
        Intent intent=getIntent();
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Degicnation");
        designation=intent.getStringExtra("Email");
        mobile=intent.getStringExtra("Mobile");
        tvname.setText(name);
        tvdesignation.setText(designation);
        tvemail.setText(email);
        tvmobile.setText(mobile);

    }
}
