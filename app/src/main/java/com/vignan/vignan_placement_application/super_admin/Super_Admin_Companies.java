package com.vignan.vignan_placement_application.super_admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.adapters.CompanyDisplayAdapter;


import java.util.ArrayList;

public class Super_Admin_Companies extends Fragment implements CompanyDisplayAdapter.CompanyOnClick {

    View root;
    ImageView add;

    RecyclerView CompanyViewRecyclerView;

    ArrayList<Company> companyArrayList;
    RecyclerView.Adapter companyViewadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.super__admin__companies, container, false);

        linkingFields();

        featureRecycler();
        companyArrayList = new ArrayList<>();
        companyViewadapter = new CompanyDisplayAdapter(companyArrayList,getContext(),this);
        CompanyViewRecyclerView.setAdapter(companyViewadapter);


        add.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),CompanyInsertion.class));
        });

        return root;
    }

    private void featureRecycler() {

        CompanyViewRecyclerView.setHasFixedSize(true);
        CompanyViewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        FirebaseDatabase.getInstance().getReference().child("Companies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.e("data :",dataSnapshot.getValue(Company.class).toString());
                    Company company = dataSnapshot.getValue(Company.class);
                    companyArrayList.add(company);

                }
                companyViewadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_SHORT).show();
            }
        });


        /*ArrayList<String> branches = new ArrayList<>();
        branches.add("CSE");
        branches.add("ECE");

        companyArrayList.add(new Company("epam","8-12lap","20Feb2023","safafsd","running","asdjkfh askdgjh aksdh aksdh  asd",branches));
        companyArrayList.add(new Company("epam","8-12lap","20Feb2023","safafsd","running","asdjkfh askdgjh aksdh aksdh  asd",branches));
        companyArrayList.add(new Company("epam","8-12lap","20Feb2023","safafsd","running","asdjkfh askdgjh aksdh aksdh  asd",branches));
        companyArrayList.add(new Company("epam","8-12lap","20Feb2023","safafsd","running","asdjkfh askdgjh aksdh aksdh  asd",branches));
        companyArrayList.add(new Company("epam","8-12lap","20Feb2023","safafsd","running","asdjkfh askdgjh aksdh aksdh  asd",branches));
*/



    }

    private void linkingFields() {

        add = root.findViewById(R.id.add_company);
        CompanyViewRecyclerView = root.findViewById(R.id.companies_display_recyclerView);
    }


    @Override
    public void onClickListener(Company company) {
        Intent intent  = new Intent(getContext(),adminCompanyDisplay.class);
        intent.putExtra("Company Details",company);
        startActivity(intent);
    }
}