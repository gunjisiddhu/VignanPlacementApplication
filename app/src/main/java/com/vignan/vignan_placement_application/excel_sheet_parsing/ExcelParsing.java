package com.vignan.vignan_placement_application.excel_sheet_parsing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.vignan.vignan_placement_application.R;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ExcelParsing extends AppCompatActivity {

    Button browse,save;
    String extension="";
    List<Student> StudentData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_parsing);
        askPermissionAndBrowseFile();
        browse = findViewById(R.id.Browse);

        browse.setOnClickListener(view -> permissions());

        save = findViewById(R.id.save);
        save.setOnClickListener(view ->{
            for(Student student : StudentData) {

                FirebaseDatabase.getInstance().getReference().child("ExcelSheetData").child(student.getBranch())
                        .child(student.getRegdNo()).setValue(student);
            }
        });


    }

    @SuppressWarnings("deprecation")
    private void permissions() {
        String[] mimetypes =
                { "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                };
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, 100);
    }

    private void askPermissionAndBrowseFile() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

        XSSFSheet sheet = workbook.getSheet("Student Data");

        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {

            CellRangeAddress c = sheet.getMergedRegion(i);
            String val=sheet.getRow(c.getFirstRow()).getCell(c.getFirstColumn()).getStringCellValue();
            int firstCol=c.getFirstColumn();
            int lastCol=c.getLastColumn();
            int startRow=c.getFirstRow();
            int lastRow=c.getLastRow();

            for(int x=startRow;x<=lastRow;x+=1)
                for(int y=firstCol;y<=lastCol;y+=1)

                    sheet.getRow(x).getCell(y).setCellValue(val);

        }

        List<ArrayList<String>> StudentDetails = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();
        while(rowIterator.hasNext()) {

            row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            ArrayList<String> data = new ArrayList<>();
            while(cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                if(cell.getCellType().equals(CellType.NUMERIC))
                    cell.setCellType(CellType.STRING);
                data.add(cell.getStringCellValue());
            }
            StudentDetails.add(data);

        }



        for(ArrayList<String> student : StudentDetails) {
            if(student.size()==5)
                StudentData.add(dataVerification(student));
        }



    }

    private Student dataVerification(ArrayList<String> student) {
        
        Student studentdata = new Student();

        if(isEmail(student.get(0)))
            studentdata.setEmail(student.get(0));
        else
            studentdata.setEmail("None");

        if(isRegisterNumber(student.get(1)))
            studentdata.setRegdNo(student.get(1));
        else
            studentdata.setRegdNo("None");

        if(isName(student.get(2)))
            studentdata.setName(student.get(2));
        else
            studentdata.setName("None");

        if (isMobileNumber(student.get(3)))
            studentdata.setMobNumber(student.get(3));
        else
            studentdata.setMobNumber("None");

        try {

            if(isBranch(student.get(4)))
                studentdata.setBranch(student.get(4));
            else
                studentdata.setBranch("None");


        }catch (Exception e) {
            if(isBranch(student.get(3)))
                studentdata.setBranch(student.get(3));
            else
                studentdata.setBranch("None");
        }

        return studentdata;
    }

    private boolean isBranch(String branch) {
        return true;
    }

    private boolean isMobileNumber(String number) {
        char[] mobile = number.toCharArray();
        for(Character ele : mobile) {
            if(isNumber(ele)) {
               continue;
            }else {
                return false;
            }
        }
        return true;
    }

    private boolean isNumber(Character ele) {
        if(Character.isDigit(ele))
            return true;
        else
            return false;
    }

    private boolean isName(String name) {

        if(isNotName(name))
            return true;
        else
            return false;
    }

    private boolean isNotName(String name) {
        return !name.isEmpty();
    }

    private boolean isRegisterNumber(String regdno) {
        if(regdno.contains("F") || regdno.contains("f"))
            return true;
        else
            return false;
    }

    private boolean isEmail(String email) {

        boolean result  = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}