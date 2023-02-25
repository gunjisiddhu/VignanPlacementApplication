package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.vignan.vignan_placement_application.adapters.ProfilePlacedCompaniesAdapter;
import com.vignan.vignan_placement_application.super_admin.PlacedStudents;
import com.vignan.vignan_placement_application.super_admin.StudentData;
import com.vignan.vignan_placement_application.super_admin.adminCompanyDisplay;

import java.util.ArrayList;
import java.util.Comparator;

public class DisplayFinalSelectedStudents extends AppCompatActivity {

    TextView companyName,placed_students_count;
    ListView student_listView;
    String name;
    ArrayList<PlacedStudents> listOfStudents;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_final_selected_students);

        Bundle extras = getIntent().getExtras();
        linkingFields();


        if (extras != null) {
            name = getIntent().getExtras().getString("CompanyName");
            listOfStudents = getIntent().getParcelableArrayListExtra("SelectedList");
            listOfStudents.sort(Comparator.comparing(PlacedStudents::getRegdno));
        }

        placed_students_count.setText("Total Count : "+listOfStudents.size()+"");
        ProfilePlacedCompaniesAdapter profilePlacedCompaniesAdapter;
        profilePlacedCompaniesAdapter = new ProfilePlacedCompaniesAdapter(getApplicationContext(),R.layout.item_student_approve_list_row,listOfStudents);
        student_listView.setAdapter(profilePlacedCompaniesAdapter);

        student_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlacedStudents placedStudents = listOfStudents.get(i);
                System.out.println(placedStudents.toString());

                new AlertDialog.Builder(DisplayFinalSelectedStudents.this)
                        .setTitle("Hello..!")
                        .setMessage("Do you want to open Student Details?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoadingDialog loadingDialog = new LoadingDialog(getParent());
                                //loadingDialog.load();
                                String rollNum = placedStudents.getRegdno();
                                FirebaseDatabase.getInstance().getReference().child("StudentData")
                                        .child("ACTIVATED").orderByChild("regdNum").equalTo(rollNum).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()){
                                                    for(DataSnapshot student:snapshot.getChildren()){
                                                        StudentData studentData = student.getValue(StudentData.class);
                                                        if(studentData.getRegdNum().equalsIgnoreCase(rollNum)){
                                                            Toast.makeText(DisplayFinalSelectedStudents.this, "Dorikesadu!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(DisplayFinalSelectedStudents.this, StudentProfileForModificatiomActivity.class);
                                                            intent.putExtra("StudentData",studentData);
                                                            startActivity(intent);


                                                        }
                                                    }

                                                    //loadingDialog.dismisss();
                                                }else{
                                                    //loadingDialog.dismisss();
                                                    //Alert Dialog
                                                    new AlertDialog.Builder(DisplayFinalSelectedStudents.this)
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
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.editstudentprofile)
                        .setCancelable(false)
                        .show();


            }
        });

    }

    private void linkingFields() {
        listOfStudents = new ArrayList<>();
        placed_students_count = findViewById(R.id.final_qualified_list_count);
        companyName = findViewById(R.id.companyname);
        student_listView = findViewById(R.id.show_students);
    }
}