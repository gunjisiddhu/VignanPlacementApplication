package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.adapters.LoadingDialog;
import com.vignan.vignan_placement_application.super_admin.PlacedStudents;
import com.vignan.vignan_placement_application.super_admin.StudentData;
import com.vignan.vignan_placement_application.super_admin.statusChanging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DisplaySelectedStudents extends AppCompatActivity {

    TextView companyName, companyRounds, countOfStudents;
    ListView student_listView;
    String name, round;
    ArrayList<String> listOfStudents;
    ArrayAdapter adapter;

    AppCompatButton deleteAndAddNewData, addToExistingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_selected_students);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            name = getIntent().getExtras().getString("CompanyName");
            listOfStudents = (ArrayList<String>) getIntent().getExtras().get("SelectedList");
            Collections.sort(listOfStudents);
            round = getIntent().getExtras().getString("Round");
        }


        linkingFields();

        companyName.setText(name);
        companyRounds.setText(round);
        adapter = new ArrayAdapter<String>(DisplaySelectedStudents.this, R.layout.students_regdno_items_list, listOfStudents);
        student_listView.setAdapter(adapter);

        student_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LoadingDialog loadingDialog = new LoadingDialog(DisplaySelectedStudents.this);
                loadingDialog.load();
                String rollNum = listOfStudents.get(i).toLowerCase();
                FirebaseDatabase.getInstance().getReference().child("StudentData")
                        .child("ACTIVATED").orderByChild("regdNum").equalTo(rollNum).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot student : snapshot.getChildren()) {
                                        StudentData studentData = student.getValue(StudentData.class);
                                        if (studentData.getRegdNum().equalsIgnoreCase(rollNum)) {
                                            Toast.makeText(DisplaySelectedStudents.this, "Dorikesadu!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DisplaySelectedStudents.this, StudentProfileForModificatiomActivity.class);
                                            intent.putExtra("StudentData", studentData);
                                            startActivity(intent);


                                        }
                                    }

                                    loadingDialog.dismisss();
                                } else {
                                    loadingDialog.dismisss();
                                    //Alert Dialog
                                    new AlertDialog.Builder(DisplaySelectedStudents.this)
                                            .setTitle("Errorr!")
                                            .setMessage("The Selected student doesn't have an account")
                                            .setPositiveButton(android.R.string.yes, null)
                                            .setNegativeButton(android.R.string.no, null)
                                            .setIcon(R.drawable.editstudentprofile)
                                            .setCancelable(false)
                                            .show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


            }
        });

    }

    private void linkingFields() {

        companyName = findViewById(R.id.companyname);
        student_listView = findViewById(R.id.show_students);
        companyRounds = findViewById(R.id.companyRound);


        countOfStudents = findViewById(R.id.final_qualified_list_count);
        deleteAndAddNewData = findViewById(R.id.delete_old_and_add_new);
        addToExistingData = findViewById(R.id.add_to_exisitng);
    }
}