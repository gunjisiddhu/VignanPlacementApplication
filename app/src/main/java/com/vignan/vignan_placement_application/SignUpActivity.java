package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.vignan.vignan_placement_application.student.StudentCreationPOJO;
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

                //Need To add checking mechanism

                e_name = name.getText().toString().trim();
                e_password = password.getText().toString().trim();
                e_collegeid = collegeid.getText().toString().toLowerCase().trim();
                e_mail = mail.getText().toString().trim();
                e_gender = gender_selected.trim();
                e_branch = branch_selected.trim();



                StudentData studentData = createNewStudentData(e_name,e_collegeid,e_password,e_mail,e_branch,e_gender,"pending",getRandomNumberString(),"not Assigned");
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

    //regdNum,fullName, section,branch, year, password,  hostelOrDayScholar,tenthMarks, interMarks, overallGrade, backlogCount, aadharCard, panCard, List<String> qualifiedCompanies, List<String> appliedCompany
    // regdNum,fullName,gender,e_mail,branch,year,password,hostelOrDayScholar,tenthMarks,interMarks,overallGrade,backlogCount,aadharCard,panCard;

    private StudentData createNewStudentData(String e_name, String e_collegeid, String e_password, String e_mail, String e_branch, String e_gender, String pending, String randomNumberString, String not_assigned) {

        return new StudentData(e_collegeid,e_name,e_gender,e_mail,"9966033888","pending",e_branch,"4",e_password,"Day Scholar","Need To Fill","Need To Fill","Need To Fill","Need To Fill","Need To Fill","Need To Fill",new ArrayList<>(),new ArrayList<>());
    }


    private void verifyStudentData(StudentData studentData){
        FirebaseDatabase.getInstance().getReference().child("ExcelSheetData").child(studentData.getBranch()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.hasChild(studentData.getRegdNum())){
                        StudentData firebaseData = snapshot.child(studentData.getRegdNum()).getValue(StudentData.class);
                        Toast.makeText(SignUpActivity.this, "User Data is saved in Firebase!!", Toast.LENGTH_SHORT).show();
                        saveStudentDetails(studentData);

                    }else{
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("Error!!")
                                .setMessage("Your data is not saved in database, Please Contact Department Coordinator")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(R.drawable.baseline_grass_24)
                                .setCancelable(false)
                                .show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveStudentDetails(StudentData studentData) {
        FirebaseDatabase.getInstance().getReference().child("StudentData").child("NEED_TO_BE_ACTIVATED").child(studentData.getRegdNum()).setValue(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String generateOtp = getRandomNumberString();
                FirebaseDatabase.getInstance().getReference().child("StudentOTPData").child(studentData.getRegdNum()).setValue(generateOtp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StudentCreationPOJO studentCreationPOJO = new StudentCreationPOJO(studentData.getFullName(),studentData.getRegdNum(), studentData.getPassword(), studentData.getE_mail(), studentData.getBranch(), studentData.getGender(),"NEED_TO_BE_ACTIVATED",generateOtp,"NOTCreated" );
                        //String name, String regId, String password, String mail, String branch, String gender, String account_status, String otp, String authId
                        Toast.makeText(SignUpActivity.this, "Please get your account activated from coordinator", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("StudentData",studentCreationPOJO);
                        Intent intent = new Intent(SignUpActivity.this,OtpActivity.class);
                        intent.putExtra("flag",1);
                        intent.putExtra("bundle",bundle);
                        startActivity(intent);
                    }
                });
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