package com.vignan.vignan_placement_application.dept_cordinator;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.StudentProfileForModificatiomActivity;
import com.vignan.vignan_placement_application.adapters.StudentApproveListAdapter;
import com.vignan.vignan_placement_application.adapters.StudentListOnClick;
import com.vignan.vignan_placement_application.super_admin.ShowStudentList;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import java.util.ArrayList;

public class dept_home extends Fragment implements SearchView.OnQueryTextListener, StudentListOnClick {

    View root;
    String branch;
    ArrayList<StudentData> studentDataArrayList;
    StudentApproveListAdapter studentApproveListAdapter;
    ListView listView;
    SearchView searchView;
    TextView Resultcount,branchName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.dept_home, container, false);


        Resultcount = root.findViewById(R.id.ResultCountForStudentNames);
        branchName = root.findViewById(R.id.branch_display);





        studentDataArrayList = new ArrayList<>();

        studentApproveListAdapter = new StudentApproveListAdapter(getContext(),R.layout.item_student_approve_list_row,studentDataArrayList,this);
        listView = root.findViewById(R.id.studentNameListView);
        searchView = root.findViewById(R.id.searchViewInStudentDisplay);
        listView.setAdapter(studentApproveListAdapter);
        setupSearchView();


        getCurrentBranch();




        return root;
    }

    private void getCurrentBranch(){
        FirebaseDatabase.getInstance().getReference().child("Coordinators").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Coordinator coordinator = snapshot.getValue(Coordinator.class);


                if (coordinator != null) {
                    branch = coordinator.getBranch();
                    branchName.setText(branch+" Department");
                    getDataFromFirebase();
                } else {
                    Toast.makeText(getContext(), "Error Finding Your Data!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataFromFirebase() {
        System.out.println("*******************"+branch);
        FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED").orderByChild("branch").equalTo(branch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.toString());
                studentDataArrayList.clear();
                for(DataSnapshot students:snapshot.getChildren()){
                    StudentData studentData = students.getValue(StudentData.class);
                    studentDataArrayList.add(studentData);
                }

                studentDataArrayList.forEach(System.out::println);
                System.out.println("*****************************************************");
                Resultcount.setText(studentDataArrayList.size()+"");
                studentApproveListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setupSearchView() {

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Search Here");


    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        StudentApproveListAdapter customAdapter = (StudentApproveListAdapter) listView.getAdapter();
        Filter filter = customAdapter.getFilter();
        filter.filter(newText);
        Resultcount.setText(StudentApproveListAdapter.getCountOfList() + "");
        return true;
    }

    @Override
    public void getStudentDetails(StudentData studentData) {

        Intent intent = new Intent(getContext(), StudentProfileForModificatiomActivity.class);
        intent.putExtra("StudentData",studentData);
        startActivity(intent);

    }
}