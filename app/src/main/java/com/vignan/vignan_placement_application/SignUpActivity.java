package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    EditText name,collegeid,mail,password;
    Spinner gender,branch;
    Button button;
    TextView signIn;
    String gender_selected,branch_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        name = findViewById(R.id.otp_name);
        collegeid = findViewById(R.id.otp_collegeid);
        signIn = findViewById(R.id.signup_signin);
        mail = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        gender = findViewById(R.id.otp_gender);
        branch = findViewById(R.id.otp_branch);

        gender_selected = "";
        ArrayList<String> genders = new ArrayList<>();
        genders.add(new String("Gender"));
        genders.add(new String("Male"));
        genders.add(new String("Female"));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,genders);
        gender.setAdapter(genderAdapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender_selected = genders.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        branch_selected = "";
        ArrayList<String> branches = new ArrayList<>();
        branches.add("Branch");
        branches.add("CSE");
        branches.add("ECE");
        branches.add("Mech");
        branches.add("EEE");
        branches.add("Civil");
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item,branches);
        branch.setAdapter(branchAdapter);
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch_selected = branches.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button = findViewById(R.id.signup_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e_name,e_collegeid,e_password,e_mail,e_gender,e_branch;
                long e_orders,e_money;

                e_name = name.getText().toString();
                e_password = password.getText().toString();
                e_collegeid = collegeid.getText().toString();
                e_mail = mail.getText().toString();
                e_gender = gender_selected;
                e_branch = branch_selected;

                StudentData studentData = new StudentData(e_name,e_collegeid,e_password,e_mail,e_branch,e_gender,"pending",getRandomNumberString());
                verifyStudentData(studentData);

            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });







    }


    private void verifyStudentData(StudentData studentData){
        FirebaseDatabase.getInstance().getReference().child("ExcelSheetData").child(studentData.getBranch())
                .child(studentData.getRegId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot != null){
                                Toast.makeText(SignUpActivity.this, "Yay!! You have an account", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child("StudentData").child(studentData.getBranch()).child(studentData.getRegId().toLowerCase(Locale.ROOT)).setValue(studentData)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SignUpActivity.this, "Account Data Saved! Please get your account activated", Toast.LENGTH_SHORT).show();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putParcelable("StudentData",studentData);
                                                    bundle.putInt("flag",1);
                                                    Intent intent = new Intent(SignUpActivity.this,OtpActivity.class);
                                                    intent.putExtra("flag",1);
                                                    intent.putExtra("bundle",bundle);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                            }
                        }else{
                            new AlertDialog.Builder(SignUpActivity.this)
                                    .setTitle("Error")
                                    .setMessage("Your Data is not Saved in Database. Please Contact Department Coordinator")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    /*private void creatStudentProfile(StudentData studentData){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(studentData.getMail(), studentData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference("StudentData").child(
                                FirebaseAuth.getInstance().getCurrentUser().getUid()
                        ).setValue(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SignUpPage.this, "Success!! You Can Login Now", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(SignUpPage.this,LoginActivity.class));
                                finish();
                            }
                        });
                    }else{
                        try
                        {
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException weakPassword)
                        {
                            Toast.makeText(SignUpPage.this, "Weak Password!!", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                        {
                            Toast.makeText(SignUpPage.this, "Give Valid Email", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthUserCollisionException existEmail)
                        {
                            Toast.makeText(SignUpPage.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        } catch (Exception e)
                        {
                            Toast.makeText(SignUpPage.this, "Error !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }*/


    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}