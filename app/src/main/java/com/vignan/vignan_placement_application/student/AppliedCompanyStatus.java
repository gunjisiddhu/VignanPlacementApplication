package com.vignan.vignan_placement_application.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vignan.vignan_placement_application.R;

public class AppliedCompanyStatus extends Fragment {

   View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.student_applied_company_status, container, false);


        return root;
    }
}