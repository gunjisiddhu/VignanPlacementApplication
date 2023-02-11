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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.adapters.LoadingDialog;
import com.vignan.vignan_placement_application.student.StudentCoordinators;
import com.vignan.vignan_placement_application.student.StudentCreationPOJO;
import com.vignan.vignan_placement_application.super_admin.StudentData;

public class StudentDetailsForVerification extends AppCompatActivity {

    TextView name,email,branch,otp,regId;
    EditText current_otp;
    String actual_otp;
    Button activate_account;
    StudentCreationPOJO studentData;
    StudentData originalData;


    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details_for_verification);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.load();


        linkingFields();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        originalData = bundle.getParcelable("OriginalData");
        studentData = bundle.getParcelable("StudentData");
        setValues();




        activate_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("");
                String otp = current_otp.getText().toString().trim();
                if(otp.equals("")){
                    current_otp.setError("Please Enter OTP");
                    current_otp.requestFocus();
                    return;
                }
                System.out.println(otp+"   "+studentData.getOtp());
                if(otp.equals(actual_otp)){

                    loadingDialog.load();

                    FirebaseDatabase.getInstance().getReference().child("StudentData").child("NEED_TO_BE_ACTIVATED").child(originalData.getRegdNum()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference().child("StudentData").child("NEED_TO_BE_CREATED").child(originalData.getRegdNum()).setValue(originalData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingDialog.dismisss();
                                        if(task.isSuccessful()){
                                            Toast.makeText(StudentDetailsForVerification.this, "Account Activated!!, Student Can Login Now!!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(StudentDetailsForVerification.this, "Error Activatin Account!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(StudentDetailsForVerification.this, "Error In Retreiving And Chaning Data!!", Toast.LENGTH_SHORT).show();
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

        name.setText(studentData.getName());
        email.setText(studentData.getMail());
        branch.setText(studentData.getBranch());
        regId.setText(studentData.getRegId());

        FirebaseDatabase.getInstance().getReference().child("StudentOTPData").child(studentData.getRegId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                actual_otp = snapshot.getValue(String.class);
                loadingDialog.dismisss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}