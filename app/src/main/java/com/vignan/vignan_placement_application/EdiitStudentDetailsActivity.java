package com.vignan.vignan_placement_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.vignan.vignan_placement_application.adapters.ProfilePlacedCompaniesAdapter;
import com.vignan.vignan_placement_application.super_admin.PlacedStudents;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EdiitStudentDetailsActivity extends AppCompatActivity {

    String current_branch;
    StudentData studentData;

    TextView name, regdNum, studentMail, phone, gender, branch, year, stay, backlogs, currentAggregate, interMarks;
    TextView sscMarks, aadharId, panId, placedCompanyCount, AttemptedCompanyCount;
    ImageView profilePic, editStudentDetails,resetStudentDetails;

    EditText studentName, studentPhone, studentBacklogs, studentCurrentMarks, student10thMarks, studentInterMarks, studentPanCard, studentAAdharCar;

    Spinner studentYear, studentBranch;

    RadioGroup stayGroup, studentGenderGroup;
    RadioButton male, female, hostel, dayscholar;
    List<String> attemptedCompanies;
    List<PlacedStudents> placedCompanies;

    ListView attemptedCompaniesView, placedCompaniesView;

    ProfilePlacedCompaniesAdapter profilePlacedCompaniesAdapter;
    ArrayAdapter<String> attemptedCompaniesAdapter;
    AppCompatButton modifyDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ediit_student_details);
        studentData = getIntent().getParcelableExtra("StudentData");
        System.out.println(studentData.toString());

        linkingFields();

        modifyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final StudentData studentData1 = studentData;

                final boolean[] inside = {true};

                //Name
                String name = studentName.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(EdiitStudentDetailsActivity.this, "Please Enter Name!", Toast.LENGTH_SHORT).show();
                }
                if (!studentData.getFullName().equalsIgnoreCase(name)) {
                        new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                                .setTitle("Warning!!")
                                .setMessage("Do you want to change Student Name? \n Old Data : " + studentData1.getFullName() + "" +
                                        " \n New Data : " + name)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        inside[0] = false;
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        inside[0] = false;
                                        return;
                                    }
                                })
                                .setIcon(R.drawable.warning)
                                .setCancelable(false)
                                .show();
                }

                //Phone
                String phone = studentPhone.getText().toString();
                if (phone.length() != 10) {
                    Toast.makeText(EdiitStudentDetailsActivity.this, "Please Enter 10 digit Phone number!", Toast.LENGTH_SHORT).show();
                }
                if (!studentData.getPhone().equalsIgnoreCase(phone)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Phone Number? \n Old Data : "+ studentData1.getPhone() + "" +
                                    " \n New Data : "+phone)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

                //Gender
                String gender = male.isChecked() ? "Male" : "Female";
                if (!studentData.getGender().equalsIgnoreCase(gender)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Gender? \n Old Data : "+ studentData1.getGender() + "" +
                                    " \n New Data : "+gender)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

                //Branch
                String branch = studentBranch.getSelectedItem().toString();
                if(branch.equalsIgnoreCase("Select Branch")){
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Error!!")
                            .setMessage("Choose Proper Value! ")
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no,null)
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();
                    return;
                }

                if (!studentData.getBranch().equalsIgnoreCase(branch)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Branch? \n Old Data : "+ studentData1.getBranch() + "" +
                                    " \n New Data : "+branch)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

                //Year
                String year = studentYear.getSelectedItem().toString();
                if (!studentData.getYear().equalsIgnoreCase(year)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Year? \n Old Data : "+ studentData1.getYear() + "" +
                                    " \n New Data : "+year)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

                //Hostel DayScholar
                String stay_type = hostel.isChecked() ? "Hostel" : "Day Scholar";
                if (!studentData.getHostelOrDayScholar().equalsIgnoreCase(stay_type)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Stay Type? \n Old Data : "+ studentData1.getHostelOrDayScholar() + "" +
                                    " \n New Data : "+stay_type)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

                //BackLog Count
                String backLogCount = studentBacklogs.getText().toString();
                if (!studentData.getBacklogCount().equalsIgnoreCase(backLogCount)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student BackLog Count? \n Old Data : "+ studentData1.getBacklogCount() + "" +
                                    " \n New Data : "+backLogCount)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }


                String sscMarks = student10thMarks.getText().toString();
                if (!studentData.getTenthMarks().equalsIgnoreCase(sscMarks)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student SSC Marks? \n Old Data : "+ studentData1.getTenthMarks() + "" +
                                    " \n New Data : "+sscMarks)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }


                String currMarks = studentCurrentMarks.getText().toString();
                if (!studentData.getOverallGrade().equalsIgnoreCase(currMarks)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Overall Aggregate? \n Old Data : "+ studentData1.getOverallGrade() + "" +
                                    " \n New Data : "+currMarks)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }


                String interMarks = studentInterMarks.getText().toString();
                if (!studentData.getInterMarks().equalsIgnoreCase(interMarks)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Branch? \n Old Data : "+ studentData1.getInterMarks() + "" +
                                    " \n New Data : "+interMarks)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }


                String PanCard = studentPanCard.getText().toString();
                if (!studentData.getPanCard().equalsIgnoreCase(PanCard)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Pan Card? \n Old Data : "+ studentData1.getPanCard() + "" +
                                    " \n New Data : "+PanCard)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }


                String aadharCard = studentAAdharCar.getText().toString();
                if (!studentData.getAadharCard().equalsIgnoreCase(aadharCard)) {
                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to change Student Branch? \n Old Data : "+ studentData1.getAadharCard() + "" +
                                    " \n New Data : "+aadharCard)
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

    //String regdNum, String fullName, String gender, String e_mail, String phone, String authId, String branch, String year, String password, String hostelOrDayScholar, String tenthMarks, String interMarks, String overallGrade, String backlogCount, String aadharCard, String panCard, List<String> appliedCompanies, List<PlacedStudents> placedCompanies) {
                //
                StudentData studentNewData = new StudentData(
                        studentData1.getRegdNum(),
                        name,
                        gender,
                        studentData1.getE_mail(),
                        phone,
                        studentData1.getAuthId(),
                        branch,
                        year,
                        studentData1.getPassword(),
                        stay_type,
                        sscMarks,
                        interMarks,
                        currMarks,
                        backLogCount,
                        aadharCard,
                        PanCard,
                        studentData1.getAppliedCompanies(),
                        studentData.getPlacedCompanies()
                        );

                FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED")
                        .child(studentData.getAuthId()).setValue(studentNewData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EdiitStudentDetailsActivity.this, "Successfully changed Student Data!!", Toast.LENGTH_SHORT).show();


                            }
                        });

            }
        });

        resetStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new AlertDialog.Builder(EdiitStudentDetailsActivity.this)
                            .setTitle("Warning!!")
                            .setMessage("Do you want to Reset Student Details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    assignDataToViews();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .setCancelable(false)
                            .show();


                }

        });
    }


    private void linkingFields() {

          /*EditText studentName,studentMail,studentPhone,studentBacklogs,studentCurrentMarks,student10thMarks,studentInterMarks,studentPanCard,studentAAdharCar;

    Spinner studentYear;
    RadioButton male,female;
    RadioGroup stayGroup,studentGenderGroup;*/

        resetStudentDetails = findViewById(R.id.editStudentDetails);

        studentYear = findViewById(R.id.student_profile_year);


        stayGroup = findViewById(R.id.stayTYpeStudentProfile);
        studentGenderGroup = findViewById(R.id.student_profile_gender_group);

        attemptedCompanies = new ArrayList<>();
        placedCompanies = new ArrayList<>();

        attemptedCompaniesView = findViewById(R.id.student_profile_ListView_attmpt);
        placedCompaniesView = findViewById(R.id.student_profile_listView_placed);


        current_branch = "";

        editStudentDetails = findViewById(R.id.editStudentDetails);
        studentName = findViewById(R.id.student_profile_name);
        regdNum = findViewById(R.id.student_profile_regdNum);
        studentMail = findViewById(R.id.student_profile_mail);
        studentPhone = findViewById(R.id.student_profile_phone);
        male = findViewById(R.id.male_radio_button);
        female = findViewById(R.id.female_radio_button);
        hostel = findViewById(R.id.hostel_radio_button);
        dayscholar = findViewById(R.id.day_scholar_radio_button);
        studentBranch = findViewById(R.id.student_profile_branch);

        studentBacklogs = findViewById(R.id.student_profile_backlogs);
        studentCurrentMarks = findViewById(R.id.student_profile_curr_agg);
        studentInterMarks = findViewById(R.id.student_profile_intermarks);
        student10thMarks = findViewById(R.id.student_profile_ssc_marks);
        studentAAdharCar = findViewById(R.id.student_profile_aadhar);
        studentPanCard = findViewById(R.id.student_profile_pan_card);
        placedCompanyCount = findViewById(R.id.student_profile_placed_companies_count);
        AttemptedCompanyCount = findViewById(R.id.student_profile_attempt_count);

        profilePic = findViewById(R.id.student_profile_pic);

        modifyDetails = findViewById(R.id.change_student_details);

        assignDataToViews();


    }

    private void assignDataToViews() {
        current_branch = studentData.getBranch();


        studentName.setText(studentData.getFullName());
        regdNum.setText(studentData.getRegdNum().toUpperCase(Locale.ROOT));
        studentMail.setText(studentData.getE_mail());
        studentPhone.setText(studentData.getPhone());


        if (studentData.getGender().equalsIgnoreCase("Female")) {
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.female_new));
            female.setChecked(true);
        } else {
            male.setChecked(true);
        }

        if (studentData.getHostelOrDayScholar().equalsIgnoreCase("hostel")) {
            hostel.setChecked(true);
        } else {
            dayscholar.setChecked(true);
        }

        //branch.setText(studentData.getBranch());
        //studentYear.setText(studentData.getYear());

        switch (studentData.getYear()) {
            case "1":
                studentYear.setSelection(0);
                break;
            case "2":
                studentYear.setSelection(1);
                break;
            case "3":
                studentYear.setSelection(2);
                break;
            case "4":
                studentYear.setSelection(3);
                break;

        }

        studentBacklogs.setText(studentData.getBacklogCount());
        studentCurrentMarks.setText(studentData.getOverallGrade());
        studentInterMarks.setText(studentData.getInterMarks());
        student10thMarks.setText(studentData.getTenthMarks());
        studentAAdharCar.setText(studentData.getAadharCard());
        studentPanCard.setText(studentData.getPanCard());
        //placedCompanyCount.setText(studentData.getPlacedCompanies().size());
        //AttemptedCompanyCount.setText(studentData.getAppliedCompanies().size());


    }
}