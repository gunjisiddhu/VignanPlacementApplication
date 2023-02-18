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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignan.vignan_placement_application.R;

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

public class statusChanging extends AppCompatActivity {

    ListView studentsList;
    String extension="";
    Button browse,save;
    Spinner status;
    TextView companyname;
    Company company;
    ArrayList<String> extractedData,finalVerifiedList;
    String statusFromSpinner="";
    ArrayAdapter adapter;

    ArrayList<String> writtenTest_Selected_List,TR_Selected_List,HR_Selected_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_changing);

        linkingFields();
        askPermissionAndBrowseFile();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            company  =  extras.getParcelable("Company Details");
        }

        companyname.setText(company.getCompanyName());

        browse.setOnClickListener(view -> {
            if(statusFromSpinner.equals("Running"))
                Toast.makeText(getApplicationContext(), "Select Appropriate Option", Toast.LENGTH_SHORT).show();
            else
                permissions();
        });


        //student profiles lo kuda update avaliii

        save.setOnClickListener(view -> {
            statusFromSpinner = status.getSelectedItem().toString();

            FirebaseDatabase.getInstance().getReference().child("Companies").child(company.getUniqueId()).child("hrstudentsList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot listOfStudents : snapshot.getChildren()) {
                        if(finalVerifiedList.contains(listOfStudents.getValue(String.class)))
                            finalVerifiedList.remove(listOfStudents.getValue(String.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            if(statusFromSpinner.equals("Written Test")) {

                writtenTest_Selected_List.addAll(finalVerifiedList);
                company.setWrittenStudentsList(writtenTest_Selected_List);

            }else if(statusFromSpinner.equals("TR Round")) {

                TR_Selected_List.addAll(finalVerifiedList);
                company.setTRStudentsList(TR_Selected_List);

            }else if(statusFromSpinner.equals("HR Round")) {

                HR_Selected_List.addAll(finalVerifiedList);
                company.setHRStudentsList(HR_Selected_List);

            }else if(statusFromSpinner.isEmpty() || statusFromSpinner.equals("Running"))
                Toast.makeText(getApplicationContext(), "Select Appropriate Option", Toast.LENGTH_SHORT).show();

            FirebaseDatabase.getInstance().getReference().child("Companies")
                    .child(company.getUniqueId()).setValue(company);
            Toast.makeText(getApplicationContext(), "yay data saved.", Toast.LENGTH_SHORT).show();

        });




    }

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

    private void ExtractData(String filePath) throws IOException{
        FileInputStream file = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(1);


        ArrayList<ArrayList<String>> details = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();

        while(rowIterator.hasNext()) {

            row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                if(cell.getCellType().equals(CellType.NUMERIC))
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

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot students : dataSnapshot.getChildren()) {
                        if (qualifiedList.contains(students.getKey())) {
                            finalVerifiedList.add(students.getKey());
                        }
                    }
                }
                adapter = new ArrayAdapter<String>(statusChanging.this,R.layout.students_regdno_items_list, finalVerifiedList);
                studentsList.setAdapter(adapter);

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



    }
}