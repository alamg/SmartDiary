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

import com.cou.cse.alamgir.smartdiary.Model.Department;
import com.cou.cse.alamgir.smartdiary.Model.Teacher;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherActivity extends AppCompatActivity {
    private FloatingActionButton btnfab;
    private DatabaseReference db;
    private ListView teachertlist;
    private String f_key=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        btnfab= (FloatingActionButton) findViewById(R.id.tearfab);
        //db= FirebaseDatabase.getInstance().getReference();
        teachertlist= (ListView) findViewById(R.id.Tlist);
        Intent intent=getIntent();
        f_key=intent.getStringExtra("department_key");
        db= FirebaseDatabase.getInstance().getReference().child("Teacher").child(f_key);

        btnfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog=new AlertDialog.Builder(TeacherActivity.this);
                View mview= getLayoutInflater().inflate(R.layout.add_teacher,null);
                final EditText etname= (EditText) mview.findViewById(R.id.etteacerName);
                final EditText etdesignation= (EditText) mview.findViewById(R.id.etDesignation);
                final EditText etemail= (EditText) mview.findViewById(R.id.etEmail);
                final EditText etmobile= (EditText) mview.findViewById(R.id.ettMobile);
                Button save=(Button)mview.findViewById(R.id.teacherSave);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=etname.getText().toString();
                        String designation=etdesignation.getText().toString();
                        String email=etemail.getText().toString();
                        String mobile=etmobile.getText().toString();
                        Teacher teacher=new Teacher(name,designation,email,mobile);
                        db.push().setValue(teacher);
                    }
                });
                myDialog.setView(mview);
                AlertDialog dialog=myDialog.create();
                dialog.show();
            }
        });
        FirebaseListAdapter<Teacher> listAdapter=new FirebaseListAdapter<Teacher>(TeacherActivity.this,Teacher.class,R.layout.teacherlist,db) {
            @Override
            protected void populateView(View v, final Teacher model, final int position) {
                TextView tvname= (TextView) v.findViewById(R.id.tvname);
                final Button edit=(Button)v.findViewById(R.id.teacherEdit);
                final String name=model.getTeacherName();
                final String degignation=model.getDesignation();
                final String email=model.getEmail();
                final String mobile=model.getMobile();
                final String teacher_Key=getRef(position).getKey();
                tvname.setText(name);
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(TeacherActivity.this,TeacherDetailActivity.class);
                        intent.putExtra("teacher_Key",teacher_Key);
                        intent.putExtra("department_key",f_key);
                        intent.putExtra("Name",name);
                        intent.putExtra("Degicnation",degignation);
                        intent.putExtra("Email",email);
                        intent.putExtra("Mobile",mobile);
                        startActivity(intent);

                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtitteacher(teacher_Key,name,degignation,email,mobile);
                    }
                });
            }
        };
        teachertlist.setAdapter(listAdapter);

    }
    public void edtitteacher(final String key, String name, final String desgnation, final String email, final String mobile)
    {
        AlertDialog.Builder Dialog=new AlertDialog.Builder(TeacherActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.editteacher,null);
        final EditText etname,etdesiignation,etemail,etmobile;
        etname=(EditText)mview.findViewById(R.id.editteachername);
        etdesiignation=(EditText)mview.findViewById(R.id.editdesignation);
        etemail=(EditText)mview.findViewById(R.id.editteacherEmail);
        etmobile=(EditText)mview.findViewById(R.id.editteachermoblie);
        Button btndelete=(Button) mview.findViewById(R.id.teacherdelete);
        Button btnUpdate=(Button) mview.findViewById(R.id.teacherupdate);

        Dialog.setView(mview);
        final AlertDialog dialog=Dialog.create();
        dialog.show();
        etname.setText(name);
        etdesiignation.setText(desgnation);
        etemail.setText(email);
        etmobile.setText(mobile);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child(key).removeValue();
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etname.getText().toString().trim();
                String desgnation=etdesiignation.getText().toString().trim();
                String email=etemail.getText().toString().trim();
                String mobile=etmobile.getText().toString().trim();
                Teacher teacher=new Teacher(name,desgnation,email,mobile);
                db.child(key).setValue(teacher);
                dialog.dismiss();
            }
        });



    }
}
