package com.cou.cse.alamgir.smartdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    private EditText etname,etemail,etpasward;
    private Button btnSignUp, tbn;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        etname=(EditText)findViewById(R.id.etsignUpName);
        etemail=(EditText)findViewById(R.id.etsignUpEmail);
        etpasward=(EditText)findViewById(R.id.etsignUpPasward);
        btnSignUp=(Button) findViewById(R.id.Creat);
        //tbn=(Button)findViewById(R.id.diaryAictivty);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        /*tbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,DiaryActiviry.class));
            }
        });*/
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Register();
            }
        });

        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),DiaryActiviry.class));

        }

    }
    public void Register()
    {
        String name=etname.getText().toString().trim();
        String email=etemail.getText().toString().trim();
        String passward=etpasward.getText().toString().trim();
        if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(email) && !TextUtils.isEmpty(passward))
        {
            progressDialog.setMessage("Registarting......");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,passward).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),DiaryActiviry.class));

                    }

                }
            });

        }
    }


}