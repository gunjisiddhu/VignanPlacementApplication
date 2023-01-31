package com.vignan.vignan_placement_application.student;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vignan.vignan_placement_application.R;

public class Profile extends Fragment {

    View root;

    ImageView name,gender,email,regdno,branch,cordinatorbutton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.student_profile, container, false);

        linkingFields();

        cordinatorbutton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),StudentCoordinators.class));
        });

        return root;
    }

    private void linkingFields() {

        name = root.findViewById(R.id.student_username_edit_icon);
        gender = root.findViewById(R.id.student_gender_edit_icon);
        email = root.findViewById(R.id.student_email_edit_icon);
        regdno = root.findViewById(R.id.student_regdno_edit_icon);
        branch = root.findViewById(R.id.student_branch_edit_icon);
        cordinatorbutton = root.findViewById(R.id.student_department_edit_icon);

    }
}