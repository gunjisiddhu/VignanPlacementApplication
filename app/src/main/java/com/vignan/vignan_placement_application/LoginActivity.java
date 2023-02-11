package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.adapters.LoadingDialog;
import com.vignan.vignan_placement_application.dept_cordinator.Coordinator;
import com.vignan.vignan_placement_application.dept_cordinator.DeptCordinatorMainActivity;
import com.vignan.vignan_placement_application.student.StudentCreationPOJO;
import com.vignan.vignan_placement_application.student.StudentMainActivity;
import com.vignan.vignan_placement_application.super_admin.StudentData;
import com.vignan.vignan_placement_application.super_admin.SuperAdminMainActivity;
import com.vignan.vignan_placement_application.super_admin.signupDeptCoordinator;

import org.apache.poi.ss.formula.functions.Log;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText mail, password;
    Spinner type_of_user;
    String selected_user;
    androidx.appcompat.widget.AppCompatButton submit;
    TextView signup, forgotpassword;
    final String MY_PREFS_NAME = "status";


    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loadingDialog = new LoadingDialog(this);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean("isLoggedIn", false)) {
            String userType = prefs.getString("user", "No name defined");
            if (userType.equals("Student")) {
                startActivity(new Intent(LoginActivity.this, StudentMainActivity.class));

            } else if (userType.equals("Coordinator")) {
                startActivity(new Intent(LoginActivity.this, DeptCordinatorMainActivity.class));
            } else if (userType.equals("Administrator")) {
                startActivity(new Intent(LoginActivity.this, SuperAdminMainActivity.class));
            }

            finish();
        }


        signup = findViewById(R.id.signin_signup);
        forgotpassword = findViewById(R.id.forgot_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

        mail = findViewById(R.id.input_email_id);
        password = findViewById(R.id.input_password);
        type_of_user = findViewById(R.id.type_of_user);
        selected_user = "";
        String[] usersTypes = getResources().getStringArray(R.array.user_type);

        type_of_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_user = usersTypes[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit = findViewById(R.id.phone_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.load();
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));

                if (selected_user.equals("") || selected_user.equals("Select Type of User")) {
                    Toast.makeText(LoginActivity.this, "Please Choose User Type!", Toast.LENGTH_SHORT).show();
                    type_of_user.requestFocus();
                    loadingDialog.dismisss();
                    return;
                } else if (selected_user.equals("Student")) {
                    String username = mail.getText().toString().toLowerCase(Locale.ROOT).trim();
                    String pass = password.getText().toString();

                    FirebaseDatabase.getInstance().getReference().child("StudentData").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int flag = 0;
                            if (snapshot.child("NEED_TO_BE_ACTIVATED").hasChild(username)) {
                                StudentData studentData = snapshot.child("NEED_TO_BE_ACTIVATED").child(username).getValue(StudentData.class);
                                Toast.makeText(LoginActivity.this, "Please get your account activated from Coordinator", Toast.LENGTH_SHORT).show();
                                flag = 1;

                                FirebaseDatabase.getInstance().getReference().child("StudentOTPData").child(studentData.getRegdNum()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String actual_otp = snapshot.getValue(String.class);

                                        StudentCreationPOJO studentCreationPOJO = new StudentCreationPOJO(studentData.getFullName(), studentData.getRegdNum(), studentData.getPassword(), studentData.getE_mail(), studentData.getBranch(), studentData.getGender(), "NEED_TO_BE_ACTIVATED", actual_otp, "NOTCreated");
                                        //String name, String regId, String password, String mail, String branch, String gender, String account_status, String otp, String authId
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("StudentData", studentCreationPOJO);
                                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                        intent.putExtra("flag", 1);
                                        intent.putExtra("bundle", bundle);
                                        startActivity(intent);
                                        loadingDialog.dismisss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            } else if (snapshot.child("NEED_TO_BE_CREATED").hasChild(username)) {
                                StudentData studentData = snapshot.child("NEED_TO_BE_CREATED").child(username).getValue(StudentData.class);

                                flag = 1;
                                if (pass.equals(studentData.getPassword())) {
                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(studentData.getE_mail(), studentData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseDatabase.getInstance().getReference().child("StudentData").child("NEED_TO_BE_CREATED").child(studentData.getRegdNum()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        studentData.setAuthId(FirebaseAuth.getInstance().getUid());
                                                        FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).setValue(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            loadingDialog.dismisss();
                                                                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                                            editor.putString("user", "Student");
                                                                            editor.putBoolean("isLoggedIn", true);
                                                                            editor.apply();

                                                                            Toast.makeText(LoginActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                                                                            startActivity(new Intent(getApplicationContext(), StudentMainActivity.class));
                                                                            finish();




                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Errorr!!  " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                for (DataSnapshot students : snapshot.child("ACTIVATED").getChildren()) {
                                    StudentData st = students.getValue(StudentData.class);
                                    if (st.getRegdNum().toString().equals(username)) {

                                        StudentData studentData = students.getValue(StudentData.class);
                                        String email = studentData.getE_mail();
                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    try {
                                                        throw task.getException();
                                                    } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                                        password.requestFocus();
                                                        password.setError("Enter Correct Password!");
                                                    } catch (Exception e) {
                                                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {


                                                    //sharedPreferences
                                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                    editor.putString("user", "Student");
                                                    editor.putBoolean("isLoggedIn", true);
                                                    editor.apply();

                                                    Toast.makeText(LoginActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), StudentMainActivity.class));
                                                    finish();
                                                }

                                            }
                                        });

                                        flag = 1;
                                        break;

                                    }


                                }
                            }
                            if (flag == 0)
                                Toast.makeText(LoginActivity.this, "Please Create an Account First!!!", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (selected_user.equals("Department Coordinator")) {


                    String username = mail.getText().toString();
                    String pass = password.getText().toString();

                    FirebaseDatabase.getInstance().getReference().child("Coordinators").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int flag = 0;
                            if(snapshot.child("NEED_TO_BE_CREATED").hasChild(username)){
                                Coordinator coordinator = snapshot.child("NEED_TO_BE_CREATED").child(username).getValue(Coordinator.class);

                                if (coordinator.getUsername().equals(mail.getText().toString().trim()) && coordinator.getPassword().equals(password.getText().toString().trim())) {
                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(coordinator.getMail(), coordinator.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                coordinator.setAuthId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                coordinator.setAccountStatus("ACTIVATED");

                                                FirebaseDatabase.getInstance().getReference().child("Coordinators").child("NEED_TO_BE_CREATED").child(username).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        FirebaseDatabase.getInstance().getReference().child("Coordinators").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).setValue(coordinator).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {


                                                                loadingDialog.dismisss();
                                                                //Shared Preference
                                                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                                editor.putString("user", "Coordinator");
                                                                editor.putBoolean("isLoggedIn", true);
                                                                editor.apply();


                                                                startActivity(new Intent(LoginActivity.this, DeptCordinatorMainActivity.class));
                                                            }
                                                        });

                                                    }
                                                });



                                            } else {
                                                loadingDialog.dismisss();
                                                Toast.makeText(LoginActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    loadingDialog.dismisss();
                                    Toast.makeText(LoginActivity.this, "Id & Password Combination Doesn't Match", Toast.LENGTH_SHORT).show();
                                }


                                flag =1;
                            }else{
                                for(DataSnapshot cords:snapshot.child("ACTIVATED").getChildren()){
                                    Coordinator coordinator = cords.getValue(Coordinator.class);
                                    if(coordinator.getUsername().equals(username)){
                                        System.out.println(pass+"****************************"+coordinator.getPassword());
                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(coordinator.getMail(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {

                                                    loadingDialog.dismisss();
                                                    //Shared Prefernce
                                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                    editor.putString("user", "Coordinator");
                                                    editor.putBoolean("isLoggedIn", true);
                                                    editor.apply();


                                                    startActivity(new Intent(LoginActivity.this, DeptCordinatorMainActivity.class));
                                                } else {
                                                    loadingDialog.dismisss();

                                                    /**************************** Change Cheyyali **************************/

                                                    Toast.makeText(LoginActivity.this, "Incorrect Password!!", Toast.LENGTH_SHORT).show();
                                                    //startActivity(new Intent(LoginActivity.this,DeptCordinatorMainActivity.class));
                                                }
                                            }
                                        });

                                        flag = 1;
                                        break;
                                    }
                                }
                            }

                            if(flag == 0){
                                loadingDialog.dismisss();
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Error!!")
                                        .setMessage("Your data is not saved in database, Please Contact Super Admin")
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

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //startActivity(new Intent(LoginActivity.this, DeptCordinatorMainActivity.class));
                } else if (selected_user.equals("Administrator")) {
                    loadingDialog.dismisss();

                    startActivity(new Intent(LoginActivity.this, SuperAdminMainActivity.class));
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Warning!")
                .setMessage("Are you sure want to exit?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }
}