package com.vignan.vignan_placement_application.super_admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vignan.vignan_placement_application.R;

public class Super_Admin_Profiles extends Fragment {

   View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.super__admin__profiles, container, false);

        return root;
    }
}