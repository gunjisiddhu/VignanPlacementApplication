package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.DisplayFinalSelectedStudents;
import com.vignan.vignan_placement_application.DisplaySelectedStudents;
import com.vignan.vignan_placement_application.EdiitStudentDetailsActivity;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.StudentProfileForModificatiomActivity;
import com.vignan.vignan_placement_application.adapters.LoadingDialog;
import com.vignan.vignan_placement_application.adapters.ProfilePlacedCompaniesAdapter;
import com.vignan.vignan_placement_application.adapters.StudentApproveListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class adminCompanyDisplay extends AppCompatActivity {

    TextView companyName,ctc,status,desc,time,endtime, WT_Count, TR_Count, HR_Count,final_Count;
    TextView placed_students_count;
    ListView placedStudentsListView;
    ImageView openPlacedStudentsList;

    ArrayList<String> listOfBranches;
    ListView branches;
    Button save;
    Company company;
    ArrayAdapter adapter;
    ImageView status_icon,final_list_icon,tr_list_icon,wt_list_icon,hr_list_icon;

    LinearLayout finalListDisplay,submitfinalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_company_display);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           company  =  extras.getParcelable("Company Details");
        }

        linkingFields();
        setValues();

        //blockingLayouts();

        displayingListOfStudents();


        save.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),UploadingPlacedSudents.class);
            intent.putExtra("Company Details",company);
            startActivity(intent);

        });

        status_icon.setOnClickListener(view ->{
            Intent intent = new Intent(getApplicationContext(), statusChanging.class);
            intent.putExtra("Company Details",company);
            intent.putExtra("Current Status",company.getStatus());
            startActivity(intent);
        });

    }

    private void displayingListOfStudents() {


        wt_list_icon.setOnClickListener(view -> {
            if(company.getWrittenStudentsList() == null)
                Toast.makeText(getApplicationContext(), "No records found!!", Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent(getApplicationContext(),DisplaySelectedStudents.class);
                intent.putExtra("CompanyName",company.getCompanyName());
                intent.putExtra("SelectedList",company.getWrittenStudentsList());
                intent.putExtra("Round","Written Test Results.");
                startActivity(intent);
            }
        });

        tr_list_icon.setOnClickListener(view -> {
            if(company.getTRStudentsList() == null)
                Toast.makeText(getApplicationContext(), "No records found!!", Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent(getApplicationContext(),DisplaySelectedStudents.class);
                intent.putExtra("CompanyName",company.getCompanyName());
                intent.putExtra("SelectedList",company.getTRStudentsList());
                intent.putExtra("Round","TR Round Results.");
                startActivity(intent);
            }
        });

        hr_list_icon.setOnClickListener(view -> {
            if(company.getHRStudentsList() == null)
                Toast.makeText(getApplicationContext(), "No records found!!", Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent(getApplicationContext(),DisplaySelectedStudents.class);
                intent.putExtra("CompanyName",company.getCompanyName());
                intent.putExtra("SelectedList",company.getHRStudentsList());
                intent.putExtra("Round","HR Round Results.");
                startActivity(intent);
            }
        });

    }

    private void setValues() {

        companyName.setText(company.getCompanyName());
        ctc.setText(company.getCtc());
        status.setText(company.getStatus());
        desc.setText(company.getDescription());

        adapter = new ArrayAdapter<String>(adminCompanyDisplay.this,R.layout.students_regdno_items_list, company.getBranches());
        branches.setAdapter(adapter);


        Calendar date = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            date.setTime(sdf.parse(company.getDateOfStart()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        Date date1 = date.getTime();
        String startDate = formatter.format(date1);
        time.setText(startDate);

        if(company.getEndOfHiring() != null && company.equals("")){
            try {
                date.setTime(sdf.parse(company.getEndOfHiring()));// all done
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date1 = date.getTime();
            String endDate = formatter.format(date1);
            time.setText(endDate);
        }


        if(company.getWrittenStudentsList()!=null) {
            WT_Count.setText(company.getWrittenStudentsList().size() + "");
        }


        if(company.getTRStudentsList() != null){
            TR_Count.setText(company.getTRStudentsList().size() + "");
        }

        if(company.getHRStudentsList() != null){
            HR_Count.setText(company.getHRStudentsList().size() + "");
        }

        if( company.getFinalQualifiedList() != null){
            save.setVisibility(View.GONE);
            openPlacedStudentsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(adminCompanyDisplay.this, DisplayFinalSelectedStudents.class);
                    intent.putExtra("CompanyName",company.getCompanyName());
                    intent.putExtra("SelectedList",company.getFinalQualifiedList());
                    startActivity(intent);
                }
            });
            placed_students_count.setText(company.getFinalQualifiedList().size()+"");
            ProfilePlacedCompaniesAdapter profilePlacedCompaniesAdapter;
            profilePlacedCompaniesAdapter = new ProfilePlacedCompaniesAdapter(getApplicationContext(),R.layout.item_student_approve_list_row,company.getFinalQualifiedList());
            placedStudentsListView.setAdapter(profilePlacedCompaniesAdapter);
            placedStudentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PlacedStudents placedStudents = company.getFinalQualifiedList().get(i);
                    System.out.println(placedStudents.toString());

                    new AlertDialog.Builder(adminCompanyDisplay.this)
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
                                                                Toast.makeText(adminCompanyDisplay.this, "Dorikesadu!", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(adminCompanyDisplay.this, StudentProfileForModificatiomActivity.class);
                                                                intent.putExtra("StudentData",studentData);
                                                                startActivity(intent);


                                                            }
                                                        }
                                                        
                                                        //loadingDialog.dismisss();
                                                    }else{
                                                        //loadingDialog.dismisss();
                                                        //Alert Dialog
                                                        new AlertDialog.Builder(adminCompanyDisplay.this)
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
            placedStudentsListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });

        }else{
            openPlacedStudentsList.setVisibility(View.GONE);
            CardView cardView = findViewById(R.id.hide_this_whole_thing);
            cardView.setVisibility(View.GONE);

            View view = findViewById(R.id.to_be_hided);
            view.setVisibility(View.GONE);
        }

    }

    private void linkingFields() {

        companyName = findViewById(R.id.admin_company_name);
        ctc = findViewById(R.id.admin_ctc);
        status = findViewById(R.id.admin_status);
        desc = findViewById(R.id.admin_company_description);
        time = findViewById(R.id.admin_company_startDate);
        endtime = findViewById(R.id.admin_company_endDate);
        listOfBranches = new ArrayList<>();
        status_icon = findViewById(R.id.admin_status_edit_icon);
        branches = findViewById(R.id.admin_show_branches);
        save = findViewById(R.id.admin_company_manage_button);
        WT_Count = findViewById(R.id.written_test_count);
        TR_Count = findViewById(R.id.tr_count);
        HR_Count = findViewById(R.id.hr_count);

        wt_list_icon = findViewById(R.id.written_list_arrow);
        tr_list_icon = findViewById(R.id.tr_list_arrow);
        hr_list_icon = findViewById(R.id.hr_list_arrow);

        placed_students_count = findViewById(R.id.student_profile_placed_companies_count);
        placedStudentsListView = findViewById(R.id.student_profile_listView_placed);
        openPlacedStudentsList = findViewById(R.id.open_qualified_students);



        submitfinalList = findViewById(R.id.upload_final_list_layout);
    }
}