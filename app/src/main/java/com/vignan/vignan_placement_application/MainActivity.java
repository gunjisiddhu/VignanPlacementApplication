package com.vignan.vignan_placement_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vignan.vignan_placement_application.dept_cordinator.DeptCordinatorMainActivity;
import com.vignan.vignan_placement_application.excel_sheet_parsing.ExcelParsing;
import com.vignan.vignan_placement_application.student.StudentMainActivity;
import com.vignan.vignan_placement_application.super_admin.SuperAdminMainActivity;

public class MainActivity extends AppCompatActivity {

    Button upload,superAdmin,deptCordinator,student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superAdmin = findViewById(R.id.superAdmin);
        deptCordinator = findViewById(R.id.deptCordinator);
        student = findViewById(R.id.student);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentMainActivity.class));
            }
        });


        deptCordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeptCordinatorMainActivity.class));
            }
        });


        superAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SuperAdminMainActivity.class));
            }
        });



        upload = findViewById(R.id.upload);
        upload.setOnClickListener (view -> {
                startActivity(new Intent(getApplicationContext(), ExcelParsing.class));
                finish();
            });



    }
}