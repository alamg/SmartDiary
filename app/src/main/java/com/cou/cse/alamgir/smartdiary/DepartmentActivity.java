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
import com.cou.cse.alamgir.smartdiary.Model.Faculty;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DepartmentActivity extends AppCompatActivity {

    private FloatingActionButton Departmentfab;
    private ListView departmenttlist;
    private DatabaseReference db;
    private String f_key=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Departmentfab= (FloatingActionButton) findViewById(R.id.deptfab);

        departmenttlist= (ListView) findViewById(R.id.departmentlv);
        Intent intent=getIntent();
        f_key=intent.getStringExtra("Faculty_Key");
        db= FirebaseDatabase.getInstance().getReference().child("Department").child(f_key);

        Departmentfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog=new AlertDialog.Builder(DepartmentActivity.this);
                View mview= getLayoutInflater().inflate(R.layout.adddepartment,null);
                final EditText etname= (EditText) mview.findViewById(R.id.etdeptName);
                Button save=(Button)mview.findViewById(R.id.dptSave);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=etname.getText().toString();
                        Department department=new Department(name);
                        db.push().setValue(department);
                    }
                });
                myDialog.setView(mview);
                AlertDialog dialog=myDialog.create();
                dialog.show();
            }
        });
        FirebaseListAdapter<Department> listAdapter=new FirebaseListAdapter<Department>(DepartmentActivity.this,Department.class,R.layout.deptlist,db) {
            @Override
            protected void populateView(View v, final Department model, final int position) {
                TextView tvname= (TextView) v.findViewById(R.id.tvdname);
                Button btnedit=(Button) v.findViewById(R.id.deptedit);
               final String name=model.getDepartment();
                tvname.setText(name);
                v.setOnClickListener(new View.OnClickListener() {
                    String department_key=getRef(position).getKey();
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(DepartmentActivity.this,TeacherActivity.class);
                        intent.putExtra("department_key",department_key);
                        startActivity(intent);

                    }
                });
                btnedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key=getRef(position).getKey();
                        edit(key,name);
                    }
                });
            }
        };
        departmenttlist.setAdapter(listAdapter);
    }

public void edit(final String key,String name)
{
    AlertDialog.Builder mDialog=new AlertDialog.Builder(DepartmentActivity.this);
    View v=getLayoutInflater().inflate(R.layout.editdepartment,null);
    final EditText etdptname=(EditText)v.findViewById(R.id.etdeptname);
    Button delete= (Button) v.findViewById(R.id.btndepartmentdelete);
    Button update= (Button) v.findViewById(R.id.btndepartmentupdate);
    mDialog.setView(v);
    final AlertDialog dialog=mDialog.create();
    etdptname.setText(name);
    dialog.show();
        delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            db.child(key).removeValue();
            dialog.dismiss();
        }
    });
    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name=etdptname.getText().toString().trim();
            Department department=new Department(name);
            db.child(key).setValue(department);
        }
    });

}





}
