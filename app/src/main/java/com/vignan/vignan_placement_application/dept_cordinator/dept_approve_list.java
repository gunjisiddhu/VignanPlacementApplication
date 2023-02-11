package com.vignan.vignan_placement_application.dept_cordinator;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.LoginActivity;
import com.vignan.vignan_placement_application.OtpActivity;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.StudentDetailsForVerification;
import com.vignan.vignan_placement_application.adapters.StudentApproveListAdapter;
import com.vignan.vignan_placement_application.adapters.StudentListOnClick;
import com.vignan.vignan_placement_application.student.StudentCreationPOJO;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class dept_approve_list extends Fragment implements SearchView.OnQueryTextListener, StudentListOnClick {

    View root;

    String authId, currentBranch;
    Coordinator coordinator;
    ArrayList<StudentData> studentList;


    StudentApproveListAdapter studentApproveListAdapter;
    List<StudentData> list;
    ListView listView;
    SearchView searchView;
    HashMap<String, StudentData> facultyDetails;
    HashSet<StudentData> globalList;
    int count, curr_count = 0;
    TextView Resultcount;
    HashMap<Integer, String> actualTime;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dept_approve_list, container, false);
        authId = "";
        currentBranch = "";
        coordinator = null;
        Resultcount = root.findViewById(R.id.ResultCountForFreeFaculty);
        searchView = root.findViewById(R.id.searchViewForFreeFaculty);

        studentList = new ArrayList<>();
        studentApproveListAdapter = new StudentApproveListAdapter(getContext(), R.layout.item_student_approve_list_row, studentList, this);
        listView = root.findViewById(R.id.FacultylistView);
        listView.setAdapter(studentApproveListAdapter);
        listView.setTextFilterEnabled(false);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

        } else {
            authId = FirebaseAuth.getInstance().getUid().toString();
        }

        getUserDetails();


        facultyDetails = new HashMap<>();
        list = new ArrayList<>();


        return root;
    }

    private void getUserDetails() {
        FirebaseDatabase.getInstance().getReference().child("Coordinators").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                coordinator = snapshot.getValue(Coordinator.class);


                if (coordinator != null) {
                    startFetchingStudentLists();
                } else {
                    Toast.makeText(getContext(), "Error Finding Your Data!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void startFetchingStudentLists() {

        System.out.println("Dorkindi ID");
        currentBranch = coordinator.getBranch();
        FirebaseDatabase.getInstance().getReference().child("StudentData").child("NEED_TO_BE_ACTIVATED").orderByChild("branch").equalTo(currentBranch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    studentList.clear();
                    for (DataSnapshot students : snapshot.getChildren()) {
                        StudentData studentData = students.getValue(StudentData.class);
                        studentList.add(studentData);
                    }
                }
                Resultcount.setText("" + studentList.size());

                studentApproveListAdapter.notifyDataSetChanged();
                sendToPrint();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    /* .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                list.clear();
                for(DataSnapshot students:snapshot.getChildren()){
                    StudentData student = students.getValue(StudentData.class);
                    System.out.println("Mundu Printing   : "+student.toString());
                    if(student.getAccount_status().equals("pending")){
                        studentList.add(student);
                        list.add(student);
                    }
                }

                for(StudentData studentData:studentList)
                    System.out.println(studentData.toString());
                sendToPrint();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


    }


    private void sendToPrint() {
        //Resultcount.setText(StudentApproveListAdapter.getCountOfList()+"");
        studentApproveListAdapter.notifyDataSetChanged();
        //Resultcount.setText(StudentApproveListAdapter.getCountOfList()+"");
        setupSearchView();
        list.clear();
    }

    private void setupSearchView() {

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
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
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Resultcount.setText(StudentApproveListAdapter.getCountOfList() + "");
        return true;
    }

    @Override
    public void getStudentDetails(StudentData studentData) {
        //System.out.println(studentData.toString());
        Bundle bundle = new Bundle();
        StudentCreationPOJO studentCreationPOJO = new StudentCreationPOJO(studentData.getFullName(), studentData.getRegdNum(), studentData.getPassword(), studentData.getE_mail(), studentData.getBranch(), studentData.getGender(), "NEED_TO_BE_ACTIVATED", "123456", "NOT_ASSIGNED");
        bundle.putParcelable("StudentData", studentCreationPOJO);
        bundle.putParcelable("OriginalData", studentData);
        Intent intent = new Intent(getContext(), StudentDetailsForVerification.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
        //288961

    }
}