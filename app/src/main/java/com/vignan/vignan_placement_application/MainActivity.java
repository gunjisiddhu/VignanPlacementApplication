package com.vignan.vignan_placement_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = findViewById(R.id.upload);

        upload.setOnClickListener (view -> {
                startActivity(new Intent(getApplicationContext(),ExcelParsing.class));
                finish();
            });



    }
}