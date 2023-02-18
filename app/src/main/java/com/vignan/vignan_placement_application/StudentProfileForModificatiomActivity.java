package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.adapters.ProfilePlacedCompaniesAdapter;
import com.vignan.vignan_placement_application.student.StudentCoordinators;
import com.vignan.vignan_placement_application.super_admin.PlacedStudents;
import com.vignan.vignan_placement_application.super_admin.ShowStudentList;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import java.util.ArrayList;
import java.util.List;

public class StudentProfileForModificatiomActivity extends AppCompatActivity {

    String current_branch;
    final String MY_PREFS_NAME = "status";
    StudentData studentData;

    TextView name, regdNum, mail, phone, gender, branch, year, stay, backlogs, currentAggregate, interMarks;
    TextView sscMarks, aadharId, panId, placedCompanyCount, AttemptedCompanyCount;
    ImageView profilePic,editStudentDetails;


    List<String> attemptedCompanies;
    List<PlacedStudents> placedCompanies;

    ListView attemptedCompaniesView,placedCompaniesView;

    ProfilePlacedCompaniesAdapter profilePlacedCompaniesAdapter;
    ArrayAdapter<String> attemptedCompaniesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_for_modificatiom);

        studentData = getIntent().getParcelableExtra("StudentData");
        System.out.println(studentData.toString());

        linkingFields();


        editStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(StudentProfileForModificatiomActivity.this)
                        .setTitle("Warning!!")
                        .setMessage("Do you want to change Student Details?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(StudentProfileForModificatiomActivity.this, EdiitStudentDetailsActivity.class);
                                intent.putExtra("StudentData",studentData);
                                startActivity(intent);
                                finish();
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

        attemptedCompanies = new ArrayList<>();
        placedCompanies = new ArrayList<>();

        attemptedCompaniesView = findViewById(R.id.student_profile_ListView_attmpt);
        placedCompaniesView = findViewById(R.id.student_profile_listView_placed);

        profilePlacedCompaniesAdapter = new ProfilePlacedCompaniesAdapter(getApplicationContext(),R.layout.item_student_approve_list_row,placedCompanies);
        attemptedCompaniesAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,attemptedCompanies);

        placedCompaniesView.setAdapter(profilePlacedCompaniesAdapter);
        attemptedCompaniesView.setAdapter(attemptedCompaniesAdapter);

        current_branch = "";

        editStudentDetails = findViewById(R.id.editStudentDetails);
        name = findViewById(R.id.student_profile_name);
        regdNum = findViewById(R.id.student_profile_regdNum);
        mail = findViewById(R.id.student_profile_mail);
        phone = findViewById(R.id.student_profile_phone);
        gender = findViewById(R.id.student_profile_gender);
        branch = findViewById(R.id.student_profile_branch);
        year = findViewById(R.id.student_profile_year);
        stay = findViewById(R.id.student_profile_stay);
        backlogs = findViewById(R.id.student_profile_backlogs);
        currentAggregate = findViewById(R.id.student_profile_curr_agg);
        interMarks = findViewById(R.id.student_profile_intermarks);
        sscMarks = findViewById(R.id.student_profile_ssc_marks);
        aadharId = findViewById(R.id.student_profile_aadhar);
        panId = findViewById(R.id.student_profile_pan_card);
        placedCompanyCount = findViewById(R.id.student_profile_placed_companies_count);
        AttemptedCompanyCount = findViewById(R.id.student_profile_attempt_count);

        profilePic = findViewById(R.id.student_profile_pic);

        assignDataToViews();



    }

    private void assignDataToViews() {
        current_branch = studentData.getBranch();

        name.setText(studentData.getFullName());
        regdNum.setText(studentData.getRegdNum());
        mail.setText(studentData.getE_mail());
        phone.setText(studentData.getPhone());
        gender.setText(studentData.getGender());
        if(studentData.getGender().equalsIgnoreCase("Female")){
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.female_new));
        }

        branch.setText(studentData.getBranch());
        year.setText(studentData.getYear());
        stay.setText(studentData.getHostelOrDayScholar());
        backlogs.setText(studentData.getBacklogCount());
        currentAggregate.setText(studentData.getOverallGrade());
        interMarks.setText(studentData.getInterMarks());
        sscMarks.setText(studentData.getTenthMarks());
        aadharId.setText(studentData.getAadharCard());
        panId.setText(studentData.getPanCard());
        //placedCompanyCount.setText(studentData.getPlacedCompanies().size());
        //AttemptedCompanyCount.setText(studentData.getAppliedCompanies().size());


        if(studentData.getAppliedCompanies() != null){
            AttemptedCompanyCount.setText(studentData.getAppliedCompanies().size());
            attemptedCompanies = studentData.getAppliedCompanies();
        }else{
            AttemptedCompanyCount.setText(0+"");
        }
        if(studentData.getPlacedCompanies() != null){
            placedCompanyCount.setText(studentData.getPlacedCompanies().size());
            placedCompanies = studentData.getPlacedCompanies();
        }else{
            placedCompanyCount.setText(0+"");
        }

        profilePlacedCompaniesAdapter.notifyDataSetChanged();
        attemptedCompaniesAdapter.notifyDataSetChanged();

    }
}