package com.vignan.vignan_placement_application.super_admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.adapters.StudentApproveListAdapter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Super_Admin_Home extends Fragment implements SearchView.OnQueryTextListener {

    View root;
    Button addCoordinator;

    ListView listView;
    SearchView searchView;
    List<String> branches;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.super__admin__home, container, false);

        addCoordinator = root.findViewById(R.id.add_dept_coordinator);
        addCoordinator.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),signupDeptCoordinator.class));

        });


        branches = Arrays.stream(getResources().getStringArray(R.array.Branches)).collect(Collectors.toList());
        branches.remove(0);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.drop_down_item,branches);
        listView = root.findViewById(R.id.branchesListDisplay);
        searchView = root.findViewById(R.id.searchViewForStudents);


        listView.setAdapter(arrayAdapter);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String branch = branches.get(i);
                Intent intent = new Intent(getContext(),ShowStudentList.class);
                intent.putExtra("branch",branch);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayAdapter<String> customAdapter = (ArrayAdapter<String>) listView.getAdapter();
        Filter filter = customAdapter.getFilter();
        filter.filter(newText);
        return true;
    }
}