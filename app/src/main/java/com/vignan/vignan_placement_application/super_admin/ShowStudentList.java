package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.StudentDetailsForVerification;
import com.vignan.vignan_placement_application.StudentProfileForModificatiomActivity;
import com.vignan.vignan_placement_application.adapters.StudentApproveListAdapter;
import com.vignan.vignan_placement_application.adapters.StudentListOnClick;
import com.vignan.vignan_placement_application.excel_sheet_parsing.Student;
import com.vignan.vignan_placement_application.student.StudentCreationPOJO;

import org.apache.xmlbeans.impl.soap.Text;

import java.util.ArrayList;

public class ShowStudentList extends AppCompatActivity implements SearchView.OnQueryTextListener, StudentListOnClick {


    String branch;
    ArrayList<StudentData> studentDataArrayList;

    StudentApproveListAdapter studentApproveListAdapter;


    ListView listView;
    SearchView searchView;
    TextView Resultcount,branchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);

        Resultcount = findViewById(R.id.ResultCountForStudentNames);
        branchName = findViewById(R.id.branch_display);
        branch = getIntent().getStringExtra("branch");
        branchName.setText(branch+" Department");



        studentDataArrayList = new ArrayList<>();

        studentApproveListAdapter = new StudentApproveListAdapter(getApplicationContext(),R.layout.item_student_approve_list_row,studentDataArrayList,this);
        listView = findViewById(R.id.studentNameListView);
        searchView = findViewById(R.id.searchViewInStudentDisplay);
        listView.setAdapter(studentApproveListAdapter);


        //onclickOpenStudentProfile
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




            }
        });

        setupSearchView();


        getDataFromFirebase();
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
        //StudentData studentData = studentDataArrayList.get(i);

        Intent intent = new Intent(ShowStudentList.this, StudentProfileForModificatiomActivity.class);
        intent.putExtra("StudentData",studentData);
        startActivity(intent);

    }
}