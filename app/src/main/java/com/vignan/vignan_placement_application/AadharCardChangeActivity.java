package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AadharCardChangeActivity extends AppCompatActivity {

    EditText aadharCard;
    TextView name;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_card_change);

        linkingFields();
        FirebaseDatabase.getInstance().getReference("StudentData").child("ACTIVATED").child(FirebaseAuth.getInstance().getUid()).child("fullName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("vachaa ::",snapshot.toString());
                name.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(view ->{
            if(aadharCard.getText().length()==12) {
                FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("aadharCard").setValue(aadharCard.getText().toString());
                Toast.makeText(getApplicationContext(), "Yay!! data modified.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Invalid data"+aadharCard.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        
    }

    private void linkingFields() {

        aadharCard = findViewById(R.id.updateAadhar_editText);
        name = findViewById(R.id.studentName);
        save = findViewById(R.id.updateAadhar_button);
    }
}