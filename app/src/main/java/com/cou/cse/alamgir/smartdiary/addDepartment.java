package com.cou.cse.alamgir.smartdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cou.cse.alamgir.smartdiary.Model.Department;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addDepartment extends AppCompatActivity {
    EditText etName;
    Button save;


    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        etName= (EditText) findViewById(R.id.etdeptName);
        save= (Button) findViewById(R.id.dptSave);
        Intent intent=getIntent();
        String fkey=intent.getStringExtra("f_key");
        db= FirebaseDatabase.getInstance().getReference().child("Department").child(fkey);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                Department department=new Department(name);
                db.push().setValue(department);

            }
        });
    }
}
