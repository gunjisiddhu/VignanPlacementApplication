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

public class PanCardChangeActivity extends AppCompatActivity {

    EditText pancard;
    TextView name;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_card_change);

        linkingFields();

        FirebaseDatabase.getInstance().getReference("StudentData").child("ACTIVATED").child(FirebaseAuth.getInstance().getUid()).child("fullName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(view ->{
            if(pancard.getText().length()==10) {
                FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("panCard").setValue(pancard.getText().toString());
                Toast.makeText(getApplicationContext(), "Yay!! data modified.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void linkingFields() {

        pancard = findViewById(R.id.updatePnCard_editText);
        name = findViewById(R.id.studentName_pan);
        save = findViewById(R.id.updatepan_button);

    }
}