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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.LoginActivity;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.SignUpActivity;
import com.vignan.vignan_placement_application.super_admin.StudentData;

public class Profile extends Fragment {

    View root;
    String current_branch;
    ImageView name, gender, email, regdno, branch, cordinatorbutton, logout;
    TextView uname, ugender, uemail, uregno, ubranch;
    final String MY_PREFS_NAME = "status";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.student_profile, container, false);

        linkingFields();
        System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
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

        return root;
    }

    private void linkingFields() {


        current_branch = "";
        uname = root.findViewById(R.id.student_username);
        ugender = root.findViewById(R.id.student_gender);
        uemail = root.findViewById(R.id.student_email);
        uregno = root.findViewById(R.id.student_regdno);
        ubranch = root.findViewById(R.id.sudent_branch);
        logout = root.findViewById(R.id.department_cordinator_logut);

        name = root.findViewById(R.id.student_username_edit_icon);
        gender = root.findViewById(R.id.student_gender_edit_icon);
        email = root.findViewById(R.id.student_email_edit_icon);
        regdno = root.findViewById(R.id.student_regdno_edit_icon);
        branch = root.findViewById(R.id.student_branch_edit_icon);
        cordinatorbutton = root.findViewById(R.id.student_department_edit_icon);

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

    private void assignDataToViews(StudentData studentData) {
        uname.setText(studentData.getFullName());
        ugender.setText(studentData.getGender());
        uemail.setText(studentData.getE_mail());
        uregno.setText(studentData.getRegdNum());
        ubranch.setText(studentData.getBranch());
        current_branch = studentData.getBranch();
    }
}