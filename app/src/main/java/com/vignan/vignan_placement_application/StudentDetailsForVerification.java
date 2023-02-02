package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.vignan.vignan_placement_application.student.StudentCoordinators;
import com.vignan.vignan_placement_application.super_admin.StudentData;

public class StudentDetailsForVerification extends AppCompatActivity {

    TextView name,email,branch,otp,regId;
    EditText current_otp;
    Button activate_account;
    StudentData studentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details_for_verification);

        linkingFields();
        setValues();

        activate_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = current_otp.getText().toString().trim();
                if(otp.equals("")){
                    current_otp.setError("Please Enter OTP");
                    current_otp.requestFocus();
                    return;
                }
                System.out.println(otp+"   "+studentData.getOtp());
                if(otp.equals(studentData.getOtp())){
                    studentData.setAccount_status("acivated_creation_pending");
                    FirebaseDatabase.getInstance().getReference().child("StudentData").child(studentData.getBranch()).child(studentData.getRegId()).setValue(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(StudentDetailsForVerification.this, "Account Created!! Now Student Can Login", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(StudentDetailsForVerification.this, "Error!!"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(StudentDetailsForVerification.this, "Wrong OTP!! Please Enter correct OTP", Toast.LENGTH_SHORT).show();
                }


            }
        });




    }

    private void linkingFields() {

        name = findViewById(R.id.student_name);
        regId = findViewById(R.id.student_regId);
        email = findViewById(R.id.student_mail);
        branch = findViewById(R.id.student_branch);
        otp = findViewById(R.id.otp_generated);
        activate_account = findViewById(R.id.activate_student_account);
        current_otp = findViewById(R.id.otp_generated);

    }

    void setValues(){

        Bundle bundle = getIntent().getBundleExtra("bundle");
        StudentData studentData = bundle.getParcelable("StudentData");
        this.studentData = studentData;
        name.setText(studentData.getName());
        email.setText(studentData.getMail());
        branch.setText(studentData.getBranch());
        regId.setText(studentData.getRegId());


    }
}