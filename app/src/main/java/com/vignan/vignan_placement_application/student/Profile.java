package com.vignan.vignan_placement_application.student;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.LoginActivity;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.adapters.LoadingDialog;
import com.vignan.vignan_placement_application.adapters.ProfilePlacedCompaniesAdapter;
import com.vignan.vignan_placement_application.super_admin.PlacedStudents;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    View root;
    String current_branch;
    LinearLayout cordinatorbutton, logout;
    final String MY_PREFS_NAME = "status";

    TextView name, regdNum, mail, phone, gender, branch, year, stay, backlogs, currentAggregate, interMarks;
    TextView sscMarks, password, aadharId, panId, placedCompanyCount, AttemptedCompanyCount;
    ImageView profilePic,passwordChange,aadharChange,panChange;


    List<String> attemptedCompanies;
    List<PlacedStudents> placedCompanies;

    ListView attemptedCompaniesView,placedCompaniesView;

    ProfilePlacedCompaniesAdapter profilePlacedCompaniesAdapter;
    ArrayAdapter<String> attemptedCompaniesAdapter;

    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.student_profile, container, false);

        linkingFields();
        cordinatorbutton.setOnClickListener(view -> {
            if (!current_branch.equals("")) {
                Intent intent = new Intent(getContext(), StudentCoordinators.class);
                intent.putExtra("Branch", current_branch);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Please Wait Until Data is Retreived!", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("user", "none");
                                editor.putBoolean("isLoggedIn", false);
                                editor.apply();


                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.baseline_grass_24)
                        .setCancelable(false)
                        .show();
            }
        });


        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        aadharChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        panChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fetchDataFromFirebase();
        return root;
    }

    private void fetchDataFromFirebase() {
        if (FirebaseAuth.getInstance().getCurrentUser().getUid() == null)
            getActivity().finish();
        else {
            FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    StudentData studentData = snapshot.getValue(StudentData.class);
                    assignDataToViews(studentData);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void linkingFields() {

        attemptedCompanies = new ArrayList<>();
        placedCompanies = new ArrayList<>();

        attemptedCompaniesView = root.findViewById(R.id.student_profile_ListView_attmpt);
        placedCompaniesView = root.findViewById(R.id.student_profile_listView_placed);

        profilePlacedCompaniesAdapter = new ProfilePlacedCompaniesAdapter(getContext(),R.layout.item_student_approve_list_row,placedCompanies);
        attemptedCompaniesAdapter = new ArrayAdapter<>(getContext(),R.layout.drop_down_item,attemptedCompanies);

        placedCompaniesView.setAdapter(profilePlacedCompaniesAdapter);
        attemptedCompaniesView.setAdapter(attemptedCompaniesAdapter);

        current_branch = "";
        logout = root.findViewById(R.id.department_cordinator_logut);
        cordinatorbutton = root.findViewById(R.id.student_department_edit_icon);
        passwordChange = root.findViewById(R.id.student_profile_password_change);
        aadharChange = root.findViewById(R.id.student_profile_aadhar_change);
        panChange = root.findViewById(R.id.student_profile_pan_change);

        name = root.findViewById(R.id.student_profile_name);
        regdNum = root.findViewById(R.id.student_profile_regdNum);
        mail = root.findViewById(R.id.student_profile_mail);
        phone = root.findViewById(R.id.student_profile_phone);
        gender = root.findViewById(R.id.student_profile_gender);
        branch = root.findViewById(R.id.student_profile_branch);
        year = root.findViewById(R.id.student_profile_year);
        stay = root.findViewById(R.id.student_profile_stay);
        backlogs = root.findViewById(R.id.student_profile_backlogs);
        currentAggregate = root.findViewById(R.id.student_profile_curr_agg);
        interMarks = root.findViewById(R.id.student_profile_intermarks);
        sscMarks = root.findViewById(R.id.student_profile_ssc_marks);
        password = root.findViewById(R.id.student_profile_password);
        aadharId = root.findViewById(R.id.student_profile_aadhar);
        panId = root.findViewById(R.id.student_profile_pan_card);
        placedCompanyCount = root.findViewById(R.id.student_profile_placed_companies_count);
        AttemptedCompanyCount = root.findViewById(R.id.student_profile_attempt_count);

        profilePic = root.findViewById(R.id.student_profile_pic);




    }

    private void assignDataToViews(StudentData studentData) {
        current_branch = studentData.getBranch();

        name.setText(studentData.getFullName());
        regdNum.setText(studentData.getRegdNum());
        mail.setText(studentData.getE_mail());
        phone.setText(studentData.getPhone());
        gender.setText(studentData.getGender());
        if(studentData.getGender().equalsIgnoreCase("Female")){
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.female_new));
        }

        branch.setText(studentData.getBranch());
        year.setText(studentData.getYear());
        stay.setText(studentData.getHostelOrDayScholar());
        backlogs.setText(studentData.getBacklogCount());
        currentAggregate.setText(studentData.getOverallGrade());
        interMarks.setText(studentData.getInterMarks());
        sscMarks.setText(studentData.getTenthMarks());
        password.setText(studentData.getPassword());
        aadharId.setText(studentData.getAadharCard());
        panId.setText(studentData.getPanCard());
        //placedCompanyCount.setText(studentData.getPlacedCompanies().size());
        //AttemptedCompanyCount.setText(studentData.getAppliedCompanies().size());


        if(studentData.getAppliedCompanies() != null)
            attemptedCompanies = studentData.getAppliedCompanies();
        if(studentData.getPlacedCompanies() != null)
            placedCompanies = studentData.getPlacedCompanies();

        profilePlacedCompaniesAdapter.notifyDataSetChanged();
        attemptedCompaniesAdapter.notifyDataSetChanged();

    }
}