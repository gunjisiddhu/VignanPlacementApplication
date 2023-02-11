package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.super_admin.StudentData;

public class ForgotPassword extends AppCompatActivity {

    EditText _username;
    androidx.appcompat.widget.AppCompatButton submit;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        _username = findViewById(R.id.forogotpass_username);
        submit = findViewById(R.id.forgotpass_button);
        reference = FirebaseDatabase.getInstance().getReference("StudentData").child("ACTIVATED");
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = _username.getText().toString();
                //Query checkUser = reference.child(username);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int flag =1;

                            for(DataSnapshot students:snapshot.getChildren()){
                                StudentData studentData = students.getValue(StudentData.class);
                                if(studentData.getE_mail().equalsIgnoreCase(username)){
                                    flag = 0;
                                    mAuth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(ForgotPassword.this, "Password Link Send to E-Mail Successfully!", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        Toast.makeText(ForgotPassword.this, "User Doesn't Exist!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }

                        }
                        if(flag == 1){
                            Toast.makeText(ForgotPassword.this, "user doesn't Exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
}