package com.cou.cse.alamgir.smartdiary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cou.cse.alamgir.smartdiary.Model.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfile extends AppCompatActivity {
    private ListView list;
    private FloatingActionButton fbtn;
    private DatabaseReference db;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        list=(ListView)findViewById(R.id.myprofilelist);
        fbtn=(FloatingActionButton)findViewById(R.id.myprofilefab);
        firebaseAuth=FirebaseAuth.getInstance();
        String UserId=firebaseAuth.getCurrentUser().getUid();
        db= FirebaseDatabase.getInstance().getReference("User").child(UserId);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // addmyprofile();
                startActivity(new Intent(MyProfile.this,Main2Activity.class));

            }
        });
        FirebaseListAdapter<User> listAdapter=new FirebaseListAdapter<User>(MyProfile.this,User.class,R.layout.show_profile,db) {
            @Override
            protected void populateView(View v, User model, int position) {
                TextView tvname=(TextView)v.findViewById(R.id.tvprofilename);
                tvname.setText(model.getUserName());

            }
        };
        list.setAdapter(listAdapter);
    }
    public void addmyprofile()
    {
        AlertDialog.Builder mDialog=new AlertDialog.Builder(MyProfile.this);
        View v=getLayoutInflater().inflate(R.layout.myprofile,null);
        final EditText etname=(EditText)v.findViewById(R.id.etmyprofilename) ;
        final EditText etemail=(EditText) v.findViewById(R.id.etmyprofileemail);
        final EditText etmobile=(EditText) v.findViewById(R.id.myprofilephone);
        Button save=(Button)v.findViewById(R.id.myprofilesave);
        mDialog.setView(v);
        final AlertDialog dalog=mDialog.create();
        dalog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etname.getText().toString().trim();
                String email=etemail.getText().toString().trim();
                String phone=etmobile.getText().toString().trim();
                User user=new User(name,email,phone);
                db.push().setValue(user);
                dalog.dismiss();
            }
        });


    }
}
