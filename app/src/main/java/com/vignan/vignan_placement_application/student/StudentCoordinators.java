
package com.vignan.vignan_placement_application.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.adapters.StudentCordinatorHelperClass;
import com.vignan.vignan_placement_application.adapters.StudentCordinatorListAdapter;

import java.util.ArrayList;
import java.util.List;


public class StudentCoordinators extends AppCompatActivity {

    RecyclerView studentCordinatorRecyclerView;
    RecyclerView.Adapter adapter;
    String c_branch;
    List<StudentCordinatorHelperClass> cordinatorsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_coordinators);
        c_branch = getIntent().getStringExtra("Branch");
        linkingFields();

        featureRecycler();

    }

    private void featureRecycler() {
        studentCordinatorRecyclerView.setHasFixedSize(true);
        studentCordinatorRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        cordinatorsList= new ArrayList<>();
        /*cordinatorsList.add(new StudentCordinatorHelperClass("shasank","shasank@gmail.com","7780681762","CSE","Male","1234"));
        cordinatorsList.add(new StudentCordinatorHelperClass("ravii","ravi@gmail.com","7780681762","CSE","Male","1234"));
        cordinatorsList.add(new StudentCo;rdinatorHelperClass("hemanth","hemanth@gmail.com","7780681762","CSE","Male","1234"));*/

        adapter = new StudentCordinatorListAdapter((ArrayList<StudentCordinatorHelperClass>) cordinatorsList);
        studentCordinatorRecyclerView.setAdapter(adapter);

        if((c_branch!=null) && !c_branch.equals(""))
            retreiveData();
    }

    private void retreiveData() {
        FirebaseDatabase.getInstance().getReference().child("Coordinators").child(c_branch).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    System.out.println(snap.toString());
                    cordinatorsList.add(snap.getValue(StudentCordinatorHelperClass.class));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void linkingFields() {

        System.out.println("Branch  "+c_branch );
        studentCordinatorRecyclerView = findViewById(R.id.student_deptCoordinator_recyclerView);
    }
}