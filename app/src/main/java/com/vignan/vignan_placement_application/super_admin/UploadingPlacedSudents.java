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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;
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
import java.util.Iterator;
import java.util.List;

public class UploadingPlacedSudents extends AppCompatActivity {

    ExcelParsing parsing;
    Button browse, unkownlistOfStudentsSave;
    String extension="";
    ListView listOfStudentsView;
    Company respectiveCompany;
    ArrayList<placedStudents> placedStudentsDetails,finalVerifiedList;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_placed_sudents);

        placedStudentsDetails = new ArrayList<>();
        finalVerifiedList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            respectiveCompany  =  extras.getParcelable("Company Details");
        }

        linkingFields();

        askPermissionAndBrowseFile();

        browse.setOnClickListener(view -> permissions());
        unkownlistOfStudentsSave.setOnClickListener(view ->{
            saveToFirebase();
        });

       /* adapter = new ArrayAdapter<>(this,R.layout.final_qualified_students_list_item, finalVerifiedList);
        listOfStudentsView.setAdapter(adapter);*/


    }

    private void saveToFirebase() {

        respectiveCompany.setFinalQualifiedList(finalVerifiedList);
        FirebaseDatabase.getInstance().getReference().child("Companies")
                .child(respectiveCompany.getUniqueId()).setValue(respectiveCompany);
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
                { "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                };
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, 100);
    }

    public void askPermissionAndBrowseFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()){

                // If you don't have access, launch a new activity to show the user the system's dialog
                // to allow access to the external storage
            }else{
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
        String type="";

        InputStream in = null;
        OutputStream out = null;
        try {
            // open the user-picked file for reading:
            try {
                in = getContentResolver().openInputStream(content_describer);
                type=getContentResolver().getType(content_describer);
                Log.d("path1",type);
                if(type.equalsIgnoreCase("application/vnd.ms-excel")){
                    extension=".xls";
                }
                else if(type.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                {
                    extension=".xlsx";
                }
                else{
                    extension=".xls";
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
                out = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString()+"/EntireStudentData/data"+extension));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // copy the content:
            byte[] buffer = new byte[1024];
            int len=0;
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
                ExtractData(Environment.getExternalStorageDirectory()+"/EntireStudentData/data"+extension);
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
            if (out != null){
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



        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();

        while(rowIterator.hasNext()) {

            row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            ArrayList<String> data = new ArrayList<>();
            ArrayList<String> details = new ArrayList<>();
            while(cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                if(cell.getCellType().equals(CellType.NUMERIC))
                    cell.setCellType(CellType.STRING);
                data.add(cell.getStringCellValue());
            }

            String name = data.get(0).toLowerCase();
            String salary = data.get(data.size()-1);
            placedStudentsDetails.add(new placedStudents(name,salary));

        }

        /*for( placedStudents student : placedStudentsDetails) {
            System.out.println(student);
        }*/
        VerifyStudents();



    }

    private void VerifyStudents() {

        FirebaseDatabase.getInstance().getReference().child("ExcelSheetData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot students : dataSnapshot.getChildren()) {
                        for(placedStudents student : placedStudentsDetails)
                            if(students.getKey().equals(student.getRegdno()))
                                finalVerifiedList.add(student);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}