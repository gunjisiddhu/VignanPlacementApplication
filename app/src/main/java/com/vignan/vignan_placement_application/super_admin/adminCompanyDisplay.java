package com.vignan.vignan_placement_application.super_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;


public class adminCompanyDisplay extends AppCompatActivity {

    TextView companyName,ctc,status,desc,time, WT_Count, TR_Count, HR_Count;
    ArrayList<String> listOfBranches;
    ListView branches;
    Button save;
    Company company;
    ArrayAdapter adapter;
    ImageView status_icon;

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

    private void setValues() {

        companyName.setText(company.getCompanyName());
        ctc.setText(company.getCtc());
        status.setText(company.getStatus());
        desc.setText(company.getDescription());
        time.setText(company.getDateOfStart().substring(0,11));
        adapter = new ArrayAdapter<String>(adminCompanyDisplay.this,R.layout.students_regdno_items_list, company.getBranches());
        branches.setAdapter(adapter);
        WT_Count.setText(company.getWrittenStudentsList().size()+"");
        TR_Count.setText(company.getTRStudentsList().size()+"");
        HR_Count.setText(company.getHRStudentsList().size()+"");

    }

    private void linkingFields() {

        companyName = findViewById(R.id.admin_company_name);
        ctc = findViewById(R.id.admin_ctc);
        status = findViewById(R.id.admin_status);
        desc = findViewById(R.id.admin_company_description);
        time = findViewById(R.id.admin_company_startDate);
        listOfBranches = new ArrayList<>();
        status_icon = findViewById(R.id.admin_status_edit_icon);
        branches = findViewById(R.id.admin_show_branches);
        save = findViewById(R.id.admin_company_manage_button);
        WT_Count = findViewById(R.id.written_test_count);
        TR_Count = findViewById(R.id.tr_count);
        HR_Count = findViewById(R.id.hr_count);
    }
}