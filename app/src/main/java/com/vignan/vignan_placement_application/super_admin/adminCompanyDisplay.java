package com.vignan.vignan_placement_application.super_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;


public class adminCompanyDisplay extends AppCompatActivity {

    TextView companyName,ctc,status,desc,time;
    ArrayList<String> listOfBranches;
    TextView branches;
    Button save;
    Company company;

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

        status.setOnClickListener(view ->{
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
        System.out.println(company.getBranches()+"----------");
        String data = "";
        for(String branch : company.getBranches())
            if(branch.equals(company.getBranches().size()-1))
                data = data+".";
            else
                data = data+branch+", ";

        branches.setText(data);
    }

    private void linkingFields() {

        companyName = findViewById(R.id.admin_company_name);
        ctc = findViewById(R.id.admin_ctc);
        status = findViewById(R.id.admin_status);
        desc = findViewById(R.id.admin_company_description);
        time = findViewById(R.id.admin_company_startDate);
        listOfBranches = new ArrayList<>();
        branches = findViewById(R.id.admin_branchesList);
        save = findViewById(R.id.admin_company_manage_button);
    }
}