package com.vignan.vignan_placement_application.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.super_admin.PlacedStudents;

import java.util.List;

public class ProfilePlacedCompaniesAdapter extends ArrayAdapter<PlacedStudents> {
    List<PlacedStudents> list;
    Context context;
    public ProfilePlacedCompaniesAdapter(@NonNull Context context, int resource, List<PlacedStudents> list) {
        super(context, R.layout.item_student_approve_list_row,list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_student_approve_list_row,parent,false);
        TextView t1,t2;
        t1 = v.findViewById(R.id.free_facult_id);
        t2 = v.findViewById(R.id.free_faculty_name);
        PlacedStudents f = list.get(position);
        t1.setText(f.getRegdno());
        t2.setText(f.getSalary());

        return v;
    }
}
