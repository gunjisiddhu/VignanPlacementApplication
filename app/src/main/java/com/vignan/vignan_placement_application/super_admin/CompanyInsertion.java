package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class CompanyInsertion extends AppCompatActivity  implements RemoveItemsFromBranchList{


    ImageView calendarIcon,endCalendarIcon;
    EditText selectedDate,companyNamefield,ctcField,description,selectedEndDate;
    Calendar serverTime;
    CompanyAddingListAdapter companyAddingListAdapter;

    boolean isCompanyPresent= false;
    String companyUniqueId;
    Button save;
    ArrayList<String> addedBranches;
    ListView branchesListView;

    Spinner branches,status;

    Company newCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_insertion);

        addedBranches = new ArrayList<>();
        newCompany = new Company();

        linkingFields();

        companyAddingListAdapter  = new CompanyAddingListAdapter(this,R.layout.items_companylist_row,addedBranches);
        branchesListView.setAdapter(companyAddingListAdapter);
        companyAddingListAdapter.notifyDataSetChanged();



        branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!branches.getSelectedItem().toString().equalsIgnoreCase("Select Branch"))
                    if(!addedBranches.contains(branches.getSelectedItem().toString()))
                        addedBranches.add(branches.getSelectedItem().toString());
                companyAddingListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CompanyInsertion.this, android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        //selectedmonth = selectedmonth + 1;
                        selectedDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        serverTime.set(Calendar.YEAR, selectedyear);
                        serverTime.set(Calendar.MONTH, selectedmonth);
                        serverTime.set(Calendar.DATE, selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Set Company Start Date");
                mDatePicker.show();
            }
        });

        endCalendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CompanyInsertion.this, android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        //selectedmonth = selectedmonth + 1;
                        selectedEndDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        serverTime.set(Calendar.YEAR, selectedyear);
                        serverTime.set(Calendar.MONTH, selectedmonth);
                        serverTime.set(Calendar.DATE, selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Set Company End Date");
                mDatePicker.show();
            }
        });

        save.setOnClickListener(view -> {

            companyUniqueId = UUID.randomUUID().toString();
            companyUniqueId = companyUniqueId.replaceAll("[-+.^:, ]","");


            newCompany = new Company(companyNamefield.getText().toString(),ctcField.getText().toString(), selectedDate.getText().toString(),selectedEndDate.getText().toString(),companyUniqueId,status.getSelectedItem().toString(),description.getText().toString(),addedBranches);

     /*       FirebaseDatabase.getInstance().getReference().child("Companies").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot companies : snapshot.getChildren()) {
                        Company companyDetails = companies.getValue(Company.class);
                        if(companyDetails.equals(newCompany)) {
                            isCompanyPresent = true;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
*/

           // if(!isCompanyPresent) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Companies")
                        .child(newCompany.getUniqueId()).setValue(newCompany);
                Toast.makeText(getApplicationContext(), "yay! Company Saved", Toast.LENGTH_LONG).show();
            //}
        });


    }

    private void linkingFields() {


        save = findViewById(R.id.superAdmin_save_Comapany);
        serverTime = Calendar.getInstance();
        companyNamefield = findViewById(R.id.superAdmin_company_name);
        ctcField = findViewById(R.id.superAdmin_ctc);
        selectedDate = findViewById(R.id.superAdmin_date);
        selectedEndDate = findViewById(R.id.superAdmin_end_date);
        calendarIcon = findViewById(R.id.superAdmin_calendar_icon);
        endCalendarIcon = findViewById(R.id.end_date_calendar_icon);
        description = findViewById(R.id.superAdmin_desc);
        branches = findViewById(R.id.superAdmin_branchSelecting);
        status = findViewById(R.id.superAdmin_company_status);
        branchesListView = findViewById(R.id.superAdmin_BranchList);

    }
    @Override
    public void removeItemFromList(int position) {
        addedBranches.remove(position);
        companyAddingListAdapter.notifyDataSetChanged();
    }
}