package com.vignan.vignan_placement_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vignan.vignan_placement_application.super_admin.PlacedStudents;
import com.vignan.vignan_placement_application.super_admin.statusChanging;

import java.util.ArrayList;

public class DisplaySelectedStudents extends AppCompatActivity {

    TextView companyName,companyRounds;
    ListView student_listView;
    String name,round;
    ArrayList<String> listOfStudents;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_selected_students);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            name = getIntent().getExtras().getString("CompanyName");
            listOfStudents = (ArrayList<String>) getIntent().getExtras().get("SelectedList");
            round = getIntent().getExtras().getString("Round");
        }

        linkingFields();

        companyName.setText(name);
        companyRounds.setText(round);
        adapter = new ArrayAdapter<String>(DisplaySelectedStudents.this,R.layout.students_regdno_items_list, listOfStudents);
        student_listView.setAdapter(adapter);


    }

    private void linkingFields() {

        companyName = findViewById(R.id.companyname);
        student_listView = findViewById(R.id.show_students);
        companyRounds = findViewById(R.id.companyRound);
    }
}