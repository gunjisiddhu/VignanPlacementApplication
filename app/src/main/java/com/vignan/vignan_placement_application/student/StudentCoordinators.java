
package com.vignan.vignan_placement_application.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.StudentCordinatorHelperClass;
import com.vignan.vignan_placement_application.StudentCordinatorListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StudentCoordinators extends AppCompatActivity {

    RecyclerView studentCordinatorRecyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_coordinators);
        linkingFields();

        featureRecycler();

    }

    private void featureRecycler() {
        studentCordinatorRecyclerView.setHasFixedSize(true);
        studentCordinatorRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        List<StudentCordinatorHelperClass> cordinatorsList= new ArrayList<>();



        /*cordinatorsList.add(new StudentCordinatorHelperClass("shasank","shasank@gmail.com","7780681762","CSE","Male","1234"));
        cordinatorsList.add(new StudentCordinatorHelperClass("ravii","ravi@gmail.com","7780681762","CSE","Male","1234"));
        cordinatorsList.add(new StudentCordinatorHelperClass("hemanth","hemanth@gmail.com","7780681762","CSE","Male","1234"));*/

        adapter = new StudentCordinatorListAdapter((ArrayList<StudentCordinatorHelperClass>) cordinatorsList);
        studentCordinatorRecyclerView.setAdapter(adapter);
    }

    private void linkingFields() {
        studentCordinatorRecyclerView = findViewById(R.id.student_deptCoordinator_recyclerView);
    }
}