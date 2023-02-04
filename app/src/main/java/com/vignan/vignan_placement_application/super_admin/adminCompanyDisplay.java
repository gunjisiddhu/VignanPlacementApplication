package com.vignan.vignan_placement_application.super_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import com.vignan.vignan_placement_application.MyCompany;
import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;


public class adminCompanyDisplay extends AppCompatActivity {

    TextView companyName,ctc,status,desc,time;
    ArrayList<String> listOfBranches;
    TextView c_branches;
    Company company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_company_display);

        company = getIntent().getParcelableExtra("company");

        linkingFields();
        setValues();

    }

    private void setValues() {

        if(company!=null){
            companyName.setText(company.getCompanyName());
            ctc.setText(company.getCompanyName());
            status.setText(company.getCompanyName());
            desc.setText(company.getCompanyName());
            time.setText(company.getCompanyName());
            String branches = "";
            for(String branch:company.getBranches())
                branches+=branch+" ,";
            c_branches.setText(branches+"");
        }



        companyName.setText(MyCompany.companyName);
        ctc.setText(MyCompany.ctc);
        status.setText(MyCompany.status);
        desc.setText(MyCompany.description);
        time.setText(MyCompany.dateOfStart);


    }

    private void linkingFields() {

        companyName = findViewById(R.id.admin_company_name);
        ctc = findViewById(R.id.admin_ctc);
        status = findViewById(R.id.admin_status);
        desc = findViewById(R.id.admin_company_description);
        time = findViewById(R.id.admin_company_startDate);
        listOfBranches = new ArrayList<>();
        c_branches = findViewById(R.id.admin_branchesList);
    }
}