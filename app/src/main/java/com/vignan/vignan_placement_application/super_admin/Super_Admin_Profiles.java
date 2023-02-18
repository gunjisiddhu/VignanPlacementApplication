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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.CoordinatorDisplayAdapter;
import com.vignan.vignan_placement_application.CoordinatorsDisplayHelperClass;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.dept_cordinator.Coordinator;
import com.vignan.vignan_placement_application.dept_cordinator.dept_profile;

import java.util.ArrayList;
import java.util.List;

public class Super_Admin_Profiles extends Fragment implements CoordinatorDisplayAdapter.CoordinatorOnClick {

   View root;
   RecyclerView coordinatorsRecyclerView;
    CoordinatorDisplayAdapter adapter;
   static ArrayList<Coordinator> listfCoordinators;
   List<CoordinatorsDisplayHelperClass> coordinatorMinorDetails;

   String selectedfiled = "";
    SearchView searchView;
    ImageView filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.super__admin__profiles, container, false);

        linkingFields();
        firebaseCall();
        featuredRecycler();
        //adapter = new CoordinatorDisplayAdapter(listfCoordinators,getContext(),this);
        adapter = new CoordinatorDisplayAdapter(listfCoordinators,getContext(),this);
        coordinatorsRecyclerView.setAdapter(adapter);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCoordintors(newText);
                return true;
            }
        });

        filter.setOnClickListener(view ->{

            String[] feilds = {"Name","Branch"};
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

    private void filterCoordintors(String searchedDetails) {
        ArrayList<Coordinator> filteredCoordinators = new ArrayList<>();
        if(selectedfiled.isBlank() || selectedfiled.toLowerCase().contains("name")) {
            for (Coordinator deptCordinator : listfCoordinators) {
                if(deptCordinator.getName().toLowerCase().contains(searchedDetails.toLowerCase()))
                    filteredCoordinators.add(deptCordinator);
            }
        }else {
            for (Coordinator deptCordinator : listfCoordinators) {
                if(deptCordinator.getBranch().toLowerCase().contains(searchedDetails.toLowerCase()))
                    filteredCoordinators.add(deptCordinator);
            }
        }
        
        if (filteredCoordinators.isEmpty()) {
            Toast.makeText(getActivity(), "No Records Found", Toast.LENGTH_SHORT).show();
        }else {
            adapter.filteredCoordinators(filteredCoordinators);
        }
    }

    private void featuredRecycler() {
        coordinatorsRecyclerView.setHasFixedSize(true);
        coordinatorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


    }

    private void linkingFields() {

        listfCoordinators = new ArrayList<>();
        coordinatorMinorDetails = new ArrayList<>();
        coordinatorsRecyclerView = root.findViewById(R.id.display_coordinators_recyclerView);
        searchView = root.findViewById(R.id.search_for_coordinator_details);
        filter = root.findViewById(R.id.coordinator_filter_icon);

    }


    private void firebaseCall() {

        FirebaseDatabase.getInstance().getReference().child("Coordinators").child("ACTIVATED").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot coordinators : snapshot.getChildren()) {
                    Coordinator coordinator = coordinators.getValue(Coordinator.class);
                    listfCoordinators.add(coordinator);
                    if(coordinator.getGender().equalsIgnoreCase("male"))
                        coordinatorMinorDetails.add(new CoordinatorsDisplayHelperClass(coordinator.getName(),coordinator.getBranch(),R.drawable.male));
                    else
                        coordinatorMinorDetails.add(new CoordinatorsDisplayHelperClass(coordinator.getName(),coordinator.getBranch(),R.drawable.female));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClickListener(Coordinator coordinator) {

        /*Intent intent = new Intent(getActivity(), dept_profile.class);
        intent.putExtra("Coordinator Details",coordinator);
        startActivity(intent);*/
        /*Fragment mFragment = new dept_profile();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dept_profile, mFragment).commit();*/

    }
}