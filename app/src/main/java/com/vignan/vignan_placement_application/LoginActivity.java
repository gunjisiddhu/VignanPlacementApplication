package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.vignan.vignan_placement_application.dept_cordinator.DeptCordinatorMainActivity;
import com.vignan.vignan_placement_application.student.StudentMainActivity;
import com.vignan.vignan_placement_application.super_admin.StudentData;
import com.vignan.vignan_placement_application.super_admin.SuperAdminMainActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText mail,password;
    Spinner type_of_user;
    String selected_user;
    androidx.appcompat.widget.AppCompatButton submit;
    TextView signup,forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        signup = findViewById(R.id.signin_signup);
        forgotpassword = findViewById(R.id.forgot_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
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
                if(selected_user.equals("") || selected_user.equals("Select Type of User")){
                    Toast.makeText(LoginActivity.this, "Please Choose User Type!", Toast.LENGTH_SHORT).show();
                    type_of_user.requestFocus();
                    return;
                }
                else if(selected_user.equals("Student")){
                    String username = mail.getText().toString();
                    String pass = password.getText().toString();

                    FirebaseDatabase.getInstance().getReference().child("StudentData").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                int flag = 0;
                                for(DataSnapshot branches:snapshot.getChildren()){
                                    if(branches.hasChild(username.toLowerCase(Locale.ROOT))){
                                        StudentData studentData = branches.child(username.toLowerCase(Locale.ROOT)).getValue(StudentData.class);
                                        if(studentData.getAccount_status().equalsIgnoreCase("pending")){
                                            Toast.makeText(LoginActivity.this, "Please get your account activated", Toast.LENGTH_SHORT).show();
                                            Bundle bundle = new Bundle();
                                            bundle.putParcelable("StudentData",studentData);
                                            Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
                                            intent.putExtra("flag",1);
                                            intent.putExtra("bundle",bundle);
                                            startActivity(intent);
                                            finish();
                                        }else if(studentData.getAccount_status().equalsIgnoreCase("activated")){
                                            String email = studentData.getMail();
                                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,studentData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful())
                                                    {
                                                        try
                                                        {
                                                            throw task.getException();
                                                        }
                                                        catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                                                        {
                                                            Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                                            password.requestFocus();
                                                            password.setError("Enter Correct Password!");
                                                        }
                                                        catch (Exception e)
                                                        {
                                                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(LoginActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(),StudentMainActivity.class));
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                        flag = 1;
                                    }
                                }
                                if(flag == 0){

                                }
                            }else{

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });






                    //startActivity(new Intent(LoginActivity.this, StudentMainActivity.class));
                }else if(selected_user.equals("Department Coordinator")){
                    startActivity(new Intent(LoginActivity.this, DeptCordinatorMainActivity.class));
                }else if(selected_user.equals("Administrator")){
                    startActivity(new Intent(LoginActivity.this, SuperAdminMainActivity.class));
                }
            }
        });




    }
}