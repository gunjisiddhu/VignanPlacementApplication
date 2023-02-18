package com.vignan.vignan_placement_application.super_admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
import com.vignan.vignan_placement_application.dept_cordinator.dept_profile;


import java.util.ArrayList;
import java.util.List;

public class Super_Admin_Companies extends Fragment implements CompanyDisplayAdapter.CompanyOnClick {

    View root;
    ImageView add;

    RecyclerView CompanyViewRecyclerView;
    SearchView searchView;
    ImageView filter;
    String selectedfiled="";

    ArrayList<Company> companyArrayList;
    CompanyDisplayAdapter companyViewadapter;

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

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCompaines(newText);
                return true;
            }
        });

        filter.setOnClickListener(view ->{

            String[] feilds = {"Company name","CTC"};
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("Search Filters");
            alertDialog.setSingleChoiceItems(feilds, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                         selectedfiled = feilds[i];
                    Toast.makeText(getContext(), feilds[i]+"Selected option", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialog.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();

        });


        return root;
    }

    private void filterCompaines(String searchedCompany) {

        ArrayList<Company> filterCompany = new ArrayList<>();
        if(selectedfiled.isBlank() || selectedfiled.contains("name")) {
            for (Company company : companyArrayList) {
                if (company.getCompanyName().toLowerCase().contains(searchedCompany.toLowerCase())) {
                    filterCompany.add(company);
                }
            }
        }else {
            for (Company company : companyArrayList) {
                if (company.getCtc().toLowerCase().contains(searchedCompany.toLowerCase())) {
                    filterCompany.add(company);
                }
            }
        }

        if(filterCompany.isEmpty()) {
            Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_SHORT).show();
        }
        else {
            companyViewadapter.filteredCompanies(filterCompany);
        }
    }


    private void featureRecycler() {

        CompanyViewRecyclerView.setHasFixedSize(true);
        CompanyViewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        FirebaseDatabase.getInstance().getReference().child("Companies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                companyArrayList.clear();
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
        searchView = root.findViewById(R.id.search_for_company_details);
        filter = root.findViewById(R.id.filter_icon);

    }


    @Override
    public void onClickListener(Company company) {
        Intent intent  = new Intent(getContext(),adminCompanyDisplay.class);
        intent.putExtra("Company Details",company);
        startActivity(intent);

    }
}