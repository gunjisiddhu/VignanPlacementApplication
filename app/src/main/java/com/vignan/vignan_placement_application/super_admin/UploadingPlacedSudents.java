package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.vignan.vignan_placement_application.excel_sheet_parsing.ExcelParsing;
import com.vignan.vignan_placement_application.excel_sheet_parsing.Student;

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
import java.util.stream.Collectors;

public class UploadingPlacedSudents extends AppCompatActivity {

    ExcelParsing parsing;
    Button browse, unkownlistOfStudentsSave;
    String extension = "";
    ListView listOfStudentsView;
    ArrayAdapter<ArrayList<String>> listOfStudentsAdapter;
    Company company;
    ArrayList<PlacedStudents> placedStudentDetails, finalVerifiedList;
    ArrayList<Student> selectedStudentsToSave;
    TextView companyName;

    //list view create cheyalii for displaying regdno and salary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_placed_sudents);
        selectedStudentsToSave = new ArrayList<>();
        finalVerifiedList = new ArrayList<>();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            company = extras.getParcelable("Company Details");
        }

        companyName = findViewById(R.id.companyName_display_uploading);
        companyName.setText(company.getCompanyName());

        linkingFields();

        askPermissionAndBrowseFile();

        browse.setOnClickListener(view -> permissions());
        unkownlistOfStudentsSave.setOnClickListener(view -> {
            saveToFirebase();
        });

    }

    private void saveToFirebase() {
        company.setFinalQualifiedList(finalVerifiedList);

        FirebaseDatabase.getInstance().getReference().child("Companies").child(company.getUniqueId())
                .setValue(company);

        Toast.makeText(getApplicationContext(), "yay saved", Toast.LENGTH_SHORT).show();
    }

    private void linkingFields() {
        browse = findViewById(R.id.uploadPlacedStudents_button);
        unkownlistOfStudentsSave = findViewById(R.id.saveStudents);
        listOfStudentsView = findViewById(R.id.listOfStudents);
        browse = findViewById(R.id.uploadPlacedStudents_button);
    }

    @SuppressWarnings("deprecation")
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

    private void ExtractData(String FileLocation) throws IOException {

        FileInputStream file = new FileInputStream(new File(FileLocation));
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheet("Sheet1");

        placedStudentDetails = new ArrayList<PlacedStudents>();

        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();
        while (rowIterator.hasNext()) {

            row = rowIterator.next();
            PlacedStudents placedStudent = new PlacedStudents();

            Iterator<Cell> cellIterator = row.cellIterator();
            ArrayList<String> data = new ArrayList<>();
            ArrayList<String> details = new ArrayList<>();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                if (cell.getCellType().equals(CellType.NUMERIC))
                    cell.setCellType(CellType.STRING);
                data.add(cell.getStringCellValue());
            }
            details.add(data.get(0).toLowerCase());
            details.add(data.get(data.size() - 1));
            String name = data.get(0).toLowerCase();
            String salary = data.get(data.size() - 1);
            placedStudentDetails.add(new PlacedStudents(name, salary));

        }
/*
        for(PlacedStudents student : placedStudentDetails) {
            System.out.println(student);
        }*/

        VerifyStudents();


    }

    private void VerifyStudents() {

        FirebaseDatabase.getInstance().getReference().child("ExcelSheetData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot students : dataSnapshot.getChildren()) {
                        for (PlacedStudents student : placedStudentDetails)
                            if (students.getKey().equals(student.getRegdno()))
                                //System.out.println(student);
                                finalVerifiedList.add(student);
                    }
                }

                saveDataToFirebase();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveDataToFirebase() {
        LoadingDialog loadingDialog = new LoadingDialog(UploadingPlacedSudents.this);
        loadingDialog.load();
        company.setFinalQualifiedList(finalVerifiedList);
        FirebaseDatabase.getInstance().getReference().child("Companies").child(company.getUniqueId()).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child("CompanyStundentMaxQualifiedList").child(company.getUniqueId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashSet<PlacedStudents> regdNums = finalVerifiedList.stream().collect(Collectors.toCollection(HashSet::new));
                        ArrayList<StudentHighestStatusInCompany> students = new ArrayList<>();
                        System.out.println(snapshot.toString());

                        for (DataSnapshot student : snapshot.getChildren()) {
                            StudentHighestStatusInCompany st = student.getValue(StudentHighestStatusInCompany.class);
                            if (checkIfExist(regdNums,st.getuId())) {
                                //System.out.println("Veedi Number Undi Bro !!!!!!!!!!!!!!!!!!!!!!");
                                st.setRoundQualified("Selected");
                            }
                            students.add(st);
                        }
                        FirebaseDatabase.getInstance().getReference().child("CompanyStundentMaxQualifiedList")
                                .child(company.getUniqueId()).setValue(students).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        finalVerifiedList.forEach((PlacedStudents rollNum) -> {
                                            rollNum.setRegdno(rollNum.getRegdno().toLowerCase());
                                            FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED").orderByChild("regdNum").equalTo(rollNum.getRegdno()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //System.out.println(snapshot.toString()+"");
                                                    for(DataSnapshot child:snapshot.getChildren()){
                                                        StudentData studentData = child.getValue(StudentData.class);
                                                        if(studentData.getRegdNum().equalsIgnoreCase(rollNum.getRegdno())){
                                                            System.out.println("Siddhu Undi****************");
                                                            if(studentData.getPlacedCompanies() == null){
                                                                ArrayList<PlacedStudents> list = new ArrayList<>();
                                                                list.add(new PlacedStudents(company.getCompanyName(), rollNum.getSalary()));
                                                                studentData.setPlacedCompanies(list);
                                                            }
                                                            else{
                                                            studentData.getPlacedCompanies().add(new PlacedStudents(company.getCompanyName(), rollNum.getSalary()));}


                                                            FirebaseDatabase.getInstance().getReference().child("StudentData").child("ACTIVATED")
                                                                    .child(studentData.getAuthId()).setValue(studentData);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        });
                                        loadingDialog.dismisss();


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

    private boolean checkIfExist(HashSet<PlacedStudents> regdNums, String getuId) {

        Iterator<PlacedStudents> it = regdNums.iterator();
        while(it.hasNext()){
            if(it.next().getRegdno().equalsIgnoreCase(getuId))
                return true;
        }
        return false;
    }


}