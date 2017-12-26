package com.cou.cse.alamgir.smartdiary.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.cou.cse.alamgir.smartdiary.DepartmentActivity;
import com.cou.cse.alamgir.smartdiary.FecultyActivity;
import com.cou.cse.alamgir.smartdiary.Model.Faculty;
import com.cou.cse.alamgir.smartdiary.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends Fragment {
    private FloatingActionButton btnfab;
    private DatabaseReference db;
    private ListView facultylist;



    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.activity_feculty, container, false);
        btnfab= (FloatingActionButton)v.findViewById(R.id.facultyfab);
        facultylist= (ListView)v.findViewById(R.id.facultlist);


        db= FirebaseDatabase.getInstance().getReference().child("Faculty");
        btnfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog=new AlertDialog.Builder(getActivity());
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
        FirebaseListAdapter<Faculty> listAdapter=new FirebaseListAdapter<Faculty>(getActivity(),Faculty.class,R.layout.facultylist,db) {
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
                        Intent intent=new Intent(getActivity(),DepartmentActivity.class);
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
        return v;
    }
    public void edit(final  String key, String name)
    {
        final AlertDialog.Builder mDialog=new AlertDialog.Builder(getActivity());
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
