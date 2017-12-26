package com.cou.cse.alamgir.smartdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cou.cse.alamgir.smartdiary.Model.Diary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
   private EditText etLogEmail,etLogPasward;
    private Button btnlogIn;
    private TextView btncreatNew,registration;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        etLogEmail= (EditText) findViewById(R.id.etLogInEmail);
        etLogPasward= (EditText) findViewById(R.id.etLogInPasward);
        btnlogIn= (Button) findViewById(R.id.LogInButton);
        btncreatNew=(TextView) findViewById(R.id.CreatNewAccount);
        registration=(TextView) findViewById(R.id.registration);
        tv=(TextView) findViewById(R.id.show_login);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),DiaryActiviry.class));

        }
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this,HomeActivity.class));
            }
        });
        btnlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogIn();
            }
        });
        btncreatNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpassward();
            }
        });

    }
    public void UserLogIn()
    {
        String email=etLogEmail.getText().toString().trim();
        String pasward=etLogPasward.getText().toString().trim();
        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(pasward)) {
            progressDialog.setMessage("LogIn.....");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, pasward).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), DiaryActiviry.class));
                    }


                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"Email and pasward are not matching",Toast.LENGTH_SHORT).show();
        }

    }
    public void forgotpassward()
    {
        AlertDialog.Builder myDialog=new AlertDialog.Builder(LogInActivity.this);
        View view=getLayoutInflater().inflate(R.layout.forgot_pasward,null);
        final EditText etemail=(EditText)view.findViewById(R.id.forgotemail);
        TextView retrive=(TextView) view.findViewById(R.id.retrivepasward);
        TextView cancel=(TextView) view.findViewById(R.id.cancel);
        myDialog.setView(view);
        final AlertDialog dialog=myDialog.create();
        dialog.setTitle("Enter email for new passward");
        dialog.show();
        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etemail.getText().toString();
                if(!TextUtils.isEmpty(email))
                {
                    dialog.dismiss();
                    progressDialog.setTitle("new pasward sending to your email");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();

                                tv.setText("Cheack your Email");

                            }

                        }
                    });
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
