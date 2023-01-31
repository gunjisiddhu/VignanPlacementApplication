package com.vignan.vignan_placement_application.super_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.dept_cordinator.Coordinator;

public class signupDeptCoordinator extends AppCompatActivity {

    Spinner gender,branch;
    Button save;
    EditText name,mail,phoneNummber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_dept_coordinator);

        linkingFields();

        save.setOnClickListener(view -> {
            Coordinator coordinator = getCoordinatorData();

            FirebaseDatabase.getInstance().getReference().child("Coordinators")
                    .child(coordinator.getBranch())
                    .child(coordinator.getName())
                    .setValue(coordinator);
            Toast.makeText(getApplicationContext(), "yay! saved.", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private Coordinator getCoordinatorData() {
        Coordinator coordinator = new Coordinator();

        coordinator.setName(name.getText().toString());
        coordinator.setMail(mail.getText().toString());
        coordinator.setMobileNumber(phoneNummber.getText().toString());
        coordinator.setPassword("1234");
        coordinator.setBranch(branch.getSelectedItem().toString());
        coordinator.setGender(gender.getSelectedItem().toString());

        return coordinator;
    }

    private void linkingFields() {

        gender = findViewById(R.id.coordinator_gender);
        branch = findViewById(R.id.coordinator_branch);
        name = findViewById(R.id.coordinator_name);
        mail = findViewById(R.id.coordinator_email);
        phoneNummber = findViewById(R.id.coordinator_mobile_number);
        save = findViewById(R.id.cooridnator_submit);
    }
}