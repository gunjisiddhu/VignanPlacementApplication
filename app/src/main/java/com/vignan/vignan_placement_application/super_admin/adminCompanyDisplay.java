package com.vignan.vignan_placement_application.super_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.vignan.vignan_placement_application.DisplaySelectedStudents;
import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;


public class adminCompanyDisplay extends AppCompatActivity {

    TextView companyName,ctc,status,desc,time,endtime, WT_Count, TR_Count, HR_Count,final_Count;
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

        blockingLayouts();

        displayingListOfStudents();


        save.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),UploadingPlacedSudents.class);
            intent.putExtra("Company Details",company);
            startActivity(intent);

        });

        status_icon.setOnClickListener(view ->{
            Intent intent = new Intent(getApplicationContext(), statusChanging.class);
            intent.putExtra("Company Details",company);
            startActivity(intent);
        });

    }

    private void displayingListOfStudents() {

        final_list_icon.setOnClickListener(view -> {
            if(company.getFinalQualifiedList() == null)
                Toast.makeText(getApplicationContext(), "No records found!!", Toast.LENGTH_SHORT).show();
            else {
                //startActivity(new Intent(getApplicationContext(),adminCompanyDisplay.class));
            }
        });

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

    private void blockingLayouts() {

        if(company.getFinalQualifiedList() == null) {
            finalListDisplay.setVisibility(View.GONE);
        }else {
            submitfinalList.setVisibility(View.GONE);
        }
    }

    private void setValues() {

        companyName.setText(company.getCompanyName());
        ctc.setText(company.getCtc());
        status.setText(company.getStatus());
        desc.setText(company.getDescription());
        time.setText(company.getDateOfStart());
        endtime.setText(company.getEndOfHiring());
        adapter = new ArrayAdapter<String>(adminCompanyDisplay.this,R.layout.students_regdno_items_list, company.getBranches());
        branches.setAdapter(adapter);
        if(company.getWrittenStudentsList()!=null || company.getTRStudentsList() != null || company.getHRStudentsList() != null || company.getFinalQualifiedList() != null) {
            WT_Count.setText(company.getWrittenStudentsList().size() + "");
            TR_Count.setText(company.getTRStudentsList().size() + "");
            HR_Count.setText(company.getHRStudentsList().size() + "");
            final_Count.setText(company.getFinalQualifiedList().size() + "");
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
        final_Count = findViewById(R.id.final_list_count);
        final_list_icon = findViewById(R.id.final_list_Arrow);
        wt_list_icon = findViewById(R.id.written_list_arrow);
        tr_list_icon = findViewById(R.id.tr_list_arrow);
        hr_list_icon = findViewById(R.id.hr_list_arrow);

        finalListDisplay = findViewById(R.id.final_list_display_layout);
        submitfinalList = findViewById(R.id.upload_final_list_layout);
    }
}