package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.dept_cordinator.Coordinator;

public class signupDeptCoordinator extends AppCompatActivity {

    Spinner gender,branch;
    Button save;
    EditText name,mail,phoneNummber,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_dept_coordinator);

        linkingFields();

        save.setOnClickListener(view -> {
            Coordinator coordinator = getCoordinatorData();


            FirebaseDatabase.getInstance().getReference().child("Coordinators").child("NEED_TO_BE_CREATED").child(coordinator.getUsername())
                    .setValue(coordinator).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(signupDeptCoordinator.this, "Done Creating Account now they can Login!", Toast.LENGTH_SHORT).show();
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);

                            }else{
                                Toast.makeText(signupDeptCoordinator.this, "Error Creating Account!!"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



            /*FirebaseAuth.getInstance().createUserWithEmailAndPassword(coordinator.getMail(), coordinator.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                coordinator.setAuthId(FirebaseAuth.getInstance().getCurrentUser().getUid());



                            }else{
                                Toast.makeText(signupDeptCoordinator.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/


            Toast.makeText(getApplicationContext(), "yay! saved.", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private Coordinator getCoordinatorData() {
        Coordinator coordinator = new Coordinator();

        //name,authId,mail,mobileNumber,branch,gender,password,username,accountStatus
        coordinator.setName(name.getText().toString());
        coordinator.setAuthId("");
        coordinator.setMail(mail.getText().toString());
        coordinator.setMobileNumber(phoneNummber.getText().toString());
        coordinator.setBranch(branch.getSelectedItem().toString());
        coordinator.setGender(gender.getSelectedItem().toString());
        coordinator.setPassword("123456");
        coordinator.setUsername(username.getText().toString());
        coordinator.setAccountStatus("acivated_creation_pending");
        return coordinator;
    }

    private void linkingFields() {

        gender = findViewById(R.id.coordinator_gender);
        branch = findViewById(R.id.coordinator_branch);
        name = findViewById(R.id.coordinator_name);
        mail = findViewById(R.id.coordinator_email);
        phoneNummber = findViewById(R.id.coordinator_mobile_number);
        save = findViewById(R.id.cooridnator_submit);
        username = findViewById(R.id.coordinator_username);
    }
}