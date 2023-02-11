package com.vignan.vignan_placement_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import com.vignan.vignan_placement_application.student.StudentCoordinators;
import com.vignan.vignan_placement_application.student.StudentCreationPOJO;
import com.vignan.vignan_placement_application.super_admin.StudentData;

public class OtpActivity extends AppCompatActivity {


    TextView name,email,password,branch,otp,regId;
    Button listOfCordinators;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        linkingFields();


        int flag = getIntent().getIntExtra("flag",-1);
        System.out.println("flag  "+flag);
        if(flag == 1){
            setValues();
        }

        listOfCordinators.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), StudentCoordinators.class));
            finish();
        });

    }

    private void linkingFields() {

        name = findViewById(R.id.student_name);
        regId = findViewById(R.id.student_regId);
        email = findViewById(R.id.student_mail);
        password = findViewById(R.id.student_password);
        branch = findViewById(R.id.student_branch);
        otp = findViewById(R.id.otp_generated);
        listOfCordinators = findViewById(R.id.cordinatorList);

    }

    void setValues(){

        Bundle bundle = getIntent().getBundleExtra("bundle");
        StudentCreationPOJO studentData = bundle.getParcelable("StudentData");
        name.setText(studentData.getName());
        email.setText(studentData.getMail());
        password.setText(studentData.getPassword());
        branch.setText(studentData.getBranch());
        otp.setText(studentData.getOtp());
        regId.setText(studentData.getRegId());


    }
}