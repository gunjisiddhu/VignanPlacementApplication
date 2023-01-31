package com.vignan.vignan_placement_application.super_admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vignan.vignan_placement_application.R;

public class Super_Admin_Home extends Fragment {

    View root;

    Button addCoordinator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.super__admin__home, container, false);

        addCoordinator = root.findViewById(R.id.add_dept_coordinator);
        addCoordinator.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),signupDeptCoordinator.class));

        });
        return root;
    }
}