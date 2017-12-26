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

import com.cou.cse.alamgir.smartdiary.Model.Diary;
import com.cou.cse.alamgir.smartdiary.Model.Faculty;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FecultyActivity extends AppCompatActivity {
    private FloatingActionButton btnfab;
    private DatabaseReference db;
    private ListView facultylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feculty);
        btnfab= (FloatingActionButton) findViewById(R.id.facultyfab);
        facultylist= (ListView) findViewById(R.id.facultlist);

        db= FirebaseDatabase.getInstance().getReference().child("Faculty");
        btnfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog=new AlertDialog.Builder(FecultyActivity.this);
                View mview= getLayoutInflater().inflate(R.layout.add_faculty,null);
                final EditText etname= (EditText) mview.findViewById(R.id.etfName);
                Button save=(Button)mview.findViewById(R.id.facultySave);
                myDialog.setView(mview);
                final AlertDialog dialog=myDialog.create();
                dialog.show();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=etname.getText().toString();
                        Faculty faculty=new Faculty(name);

                        db.push().setValue(faculty);
                        dialog.dismiss();
                    }
                });

            }
        });
        FirebaseListAdapter<Faculty> listAdapter=new FirebaseListAdapter<Faculty>(FecultyActivity.this,Faculty.class,R.layout.facultylist,db) {
            @Override
            protected void populateView(View v, final Faculty model, final int position) {
                TextView tvname= (TextView) v.findViewById(R.id.tvfname);
                Button edit=(Button) v.findViewById(R.id.edit);
               final String name=model.getFacultyName();
                tvname.setText(name);
                v.setOnClickListener(new View.OnClickListener() {
                    String Faculty_Key=getRef(position).getKey();
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(FecultyActivity.this,DepartmentActivity.class);
                        intent.putExtra("Faculty_Key",Faculty_Key);
                        startActivity(intent);

                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         String key=getRef(position).getKey();
                        //db.child(key).setValue();
                        edit(key,name);

                    }
                });
            }
        };
         facultylist.setAdapter(listAdapter);
    }
    public void edit(final  String key, String name)
    {
        final AlertDialog.Builder mDialog=new AlertDialog.Builder(FecultyActivity.this);
        View v=getLayoutInflater().inflate(R.layout.activity_editfaculty,null);
         final EditText etFacultyName;
         Button btndelete,btnupdate;
        etFacultyName=(EditText)v.findViewById(R.id.editName);
        btndelete=(Button)v.findViewById(R.id.fdelete);
        btnupdate=(Button)v.findViewById(R.id.fupdate);
        mDialog.setView(v);
        final AlertDialog dialog=mDialog.create();
        dialog.show();
        etFacultyName.setText(name);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child(key).removeValue();
                dialog.dismiss();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etFacultyName.getText().toString().trim();
                Faculty faculty=new Faculty(name);
                db.child(key).setValue(faculty);
                dialog.dismiss();
            }
        });

    }

}
