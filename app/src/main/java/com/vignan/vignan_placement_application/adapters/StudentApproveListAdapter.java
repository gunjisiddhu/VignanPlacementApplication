package com.vignan.vignan_placement_application.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.super_admin.StudentData;

import java.util.ArrayList;

public class StudentApproveListAdapter extends ArrayAdapter<StudentData> {
    ArrayList<StudentData> list,orig;
    Context context;
    static int count = 0;

    public StudentApproveListAdapter(@NonNull Context context, int resource,ArrayList<StudentData> list) {
        super(context, R.layout.item_student_approve_list_row,list);
        this.context = context;
        this.list = list;
        this.count = list.size();
    }

    public static int getCountOfList(){
        return count;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<StudentData> results = new ArrayList<StudentData>();
                if (orig == null)
                    orig = list;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final StudentData g : orig) {
                            if (g.getRegId().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    System.out.println("*******************************************");
                    for(StudentData i:results)
                        System.out.println(i.getRegId());
                    count = results.size();
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                list = (ArrayList<StudentData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_student_approve_list_row,parent,false);
        TextView t1,t2;
        t1 = v.findViewById(R.id.free_facult_id);
        t2 = v.findViewById(R.id.free_faculty_name);

        if (position<list.size()) {
            StudentData f = list.get(position);
            t1.setText(f.getRegId());
            t2.setText(f.getName());
        }
        return v;
    }
}
