package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.adapters.LoadingDialog;
import com.vignan.vignan_placement_application.adapters.StudentHighestStatusInCompany;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;

public class statusChanging extends AppCompatActivity {

    ListView studentsList;
    String extension = "";
    Button browse, save;
    Spinner status;
    TextView companyname, currentStatus;
    Company company;
    ArrayList<String> extractedData, finalVerifiedList;
    String statusFromSpinner = "";
    ArrayAdapter adapter;

    ArrayList<String> writtenTest_Selected_List, TR_Selected_List, HR_Selected_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_changing);

        linkingFields();
        askPermissionAndBrowseFile();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            company = extras.getParcelable("Company Details");
        }

        companyname.setText(company.getCompanyName());
        currentStatus.setText(company.getStatus());

        save.setVisibility(View.GONE);

        browse.setOnClickListener(view -> {

            statusFromSpinner = status.getSelectedItem().toString();
            if (statusFromSpinner.equalsIgnoreCase("Running"))
                Toast.makeText(getApplicationContext(), "Select Appropriate Option", Toast.LENGTH_SHORT).show();
            else if (statusFromSpinner.equalsIgnoreCase("Written Test")) {


                LoadingDialog loadingDialog = new LoadingDialog(statusChanging.this);
                loadingDialog.load();
                //setStudentsInListView
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(statusChanging.this, R.layout.drop_down_item, company.getWrittenStudentsList());
                studentsList.setAdapter(arrayAdapter);
                //done
                loadingDialog.dismisss();

                new AlertDialog.Builder(statusChanging.this)
                        .setTitle("Warning!")
                        .setMessage("Do you want to stop accepting applications? \nCurrently Showing applied Students")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //change Status
                                company.setStatus(statusFromSpinner);


                                FirebaseDatabase.getInstance().getReference().child("Companies").child(company.getUniqueId()).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ArrayList<StudentHighestStatusInCompany> studentsList = new ArrayList<>();
                                        company.getWrittenStudentsList().forEach((String uId) -> {
                                            StudentHighestStatusInCompany s = new StudentHighestStatusInCompany(uId.toLowerCase(Locale.ROOT),"Written Test");
                                            studentsList.add(s);
                                        });

                                        FirebaseDatabase.getInstance().getReference().child("CompanyStundentMaxQualifiedList")
                                                .child(company.getUniqueId()).setValue(studentsList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(statusChanging.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.editstudentprofile)
                        .setCancelable(false)
                        .show();


            } else if (statusFromSpinner.equalsIgnoreCase("TR Round")) {
                if (company.getStatus().equalsIgnoreCase("Written Test")) {
                    permissions();
                } else {
                    new AlertDialog.Builder(statusChanging.this)
                            .setTitle("Warning!")
                            .setMessage("Select Proper Status")
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.editstudentprofile)
                            .setCancelable(false)
                            .show();
                }
            } else {
                if (company.getStatus().equalsIgnoreCase("TR Round")) {
                    permissions();
                } else {
                    new AlertDialog.Builder(statusChanging.this)
                            .setTitle("Warning!")
                            .setMessage("Select Proper Status")
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.editstudentprofile)
                            .setCancelable(false)
                            .show();
                }

            }
        });




        //student profiles lo kuda update avaliii

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog(statusChanging.this);
                loadingDialog.load();

                company.setStatus(status.getSelectedItem().toString());
                FirebaseDatabase.getInstance().getReference().child("Companies").child(company.getUniqueId()).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        FirebaseDatabase.getInstance().getReference().child("CompanyStundentMaxQualifiedList").child(company.getUniqueId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                HashSet<String> regdNums = finalVerifiedList.stream().collect(Collectors.toCollection(HashSet::new));
                                ArrayList<StudentHighestStatusInCompany> students = new ArrayList<>();
                                for(DataSnapshot student:snapshot.getChildren()){
                                    StudentHighestStatusInCompany st = student.getValue(StudentHighestStatusInCompany.class);
                                    if(regdNums.contains(st.getuId())){
                                        st.setRoundQualified(status.getSelectedItem().toString());
                                    }
                                    students.add(st);
                                }
                                FirebaseDatabase.getInstance().getReference().child("CompanyStundentMaxQualifiedList")
                                        .child(company.getUniqueId()).setValue(students).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                loadingDialog.dismisss();
                                                Toast.makeText(statusChanging.this, "Done Successfully!", Toast.LENGTH_SHORT).show();

                                            }
                                        });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        }
                });





            }
        });

    }

    public void permissions() {
        String[] mimetypes =
                {"application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                };
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, 100);
    }

    public void askPermissionAndBrowseFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {

                // If you don't have access, launch a new activity to show the user the system's dialog
                // to allow access to the external storage
            } else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri content_describer = data.getData();
        String type = "";

        InputStream in = null;
        OutputStream out = null;
        try {
            // open the user-picked file for reading:
            try {
                in = getContentResolver().openInputStream(content_describer);
                type = getContentResolver().getType(content_describer);
                Log.d("path1", type);
                if (type.equalsIgnoreCase("application/vnd.ms-excel")) {
                    extension = ".xls";
                } else if (type.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    extension = ".xlsx";
                } else {
                    extension = ".xls";
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // open the output-file:
            try {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/EntireStudentData");
                if (!file.exists()) {
                    file.mkdirs();
                }
                out = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString() + "/EntireStudentData/data" + extension));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // copy the content:
            byte[] buffer = new byte[1024];
            int len = 0;
            while (true) {
                try {
                    if (!((len = in.read(buffer)) != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.write(buffer, 0, len);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Contents are copied!
            try {
                ExtractData(Environment.getExternalStorageDirectory() + "/EntireStudentData/data" + extension);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ExtractData(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(1);


        ArrayList<ArrayList<String>> details = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();

        while (rowIterator.hasNext()) {

            row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                if (cell.getCellType().equals(CellType.NUMERIC))
                    cell.setCellType(CellType.STRING);
                extractedData.add(cell.getStringCellValue().toLowerCase());
            }

        }
        verifyStudents(extractedData);

    }

    private void verifyStudents(ArrayList<String> qualifiedList) {
        FirebaseDatabase.getInstance().getReference().child("ExcelSheetData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot students : dataSnapshot.getChildren()) {
                        if (qualifiedList.contains(students.getKey())) {
                            finalVerifiedList.add(students.getKey().toLowerCase(Locale.ROOT));
                        }
                    }
                }
                adapter = new ArrayAdapter<String>(statusChanging.this, R.layout.students_regdno_items_list, finalVerifiedList);
                studentsList.setAdapter(adapter);
                save.setVisibility(View.VISIBLE);
                new AlertDialog.Builder(statusChanging.this)
                        .setTitle("Warning!")
                        .setMessage("Check The students and press on save!")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.editstudentprofile)
                        .setCancelable(false)
                        .show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void linkingFields() {
        companyname = findViewById(R.id.companyName_display);
        status = findViewById(R.id.status_changing);
        browse = findViewById(R.id.browse_file);
        studentsList = findViewById(R.id.listOfStudents_statusChanging);
        save = findViewById(R.id.uploadList_atStatusChanging);

        extractedData = new ArrayList<>();
        finalVerifiedList = new ArrayList<>();
        writtenTest_Selected_List = new ArrayList<>();
        TR_Selected_List = new ArrayList<>();
        HR_Selected_List = new ArrayList<>();

        currentStatus = findViewById(R.id.current_company_status);

    }
}