package com.vignan.vignan_placement_application.super_admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Super_Admin_Companies extends Fragment {

    View root;
    ImageView add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.super__admin__companies, container, false);

        linkingFields();

        add.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),CompanyInsertion.class));
        });

        return root;
    }

    private void linkingFields() {

        add = root.findViewById(R.id.add_company);
    }


}