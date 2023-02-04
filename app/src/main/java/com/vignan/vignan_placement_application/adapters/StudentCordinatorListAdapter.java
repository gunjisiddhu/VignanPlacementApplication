package com.vignan.vignan_placement_application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vignan.vignan_placement_application.R;

import java.util.ArrayList;

public class StudentCordinatorListAdapter extends RecyclerView.Adapter<StudentCordinatorListAdapter.StudentCordinatorViewHolder> {

    ArrayList<StudentCordinatorHelperClass> coordinatorsList = new ArrayList<>();

    public StudentCordinatorListAdapter(ArrayList<StudentCordinatorHelperClass> coordinatorsList) {
        this.coordinatorsList = coordinatorsList;
    }

    @NonNull
    @Override
    public StudentCordinatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_coordinator_list,parent,false);
        StudentCordinatorViewHolder viewHolder = new StudentCordinatorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCordinatorViewHolder holder, int position) {
        StudentCordinatorHelperClass cordinatorHelperClass = coordinatorsList.get(position);
        holder.name.setText(cordinatorHelperClass.getName());
        holder.mail.setText(cordinatorHelperClass.getMail());
        holder.mobileNumber.setText(cordinatorHelperClass.getMobileNumber());
        holder.branch.setText(cordinatorHelperClass.getBranch());

    }

    @Override
    public int getItemCount() {
        return coordinatorsList.size();
    }


    public static  class StudentCordinatorViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView mail;
        TextView mobileNumber;
        TextView branch;
        public StudentCordinatorViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recycler_username);
            mail = itemView.findViewById(R.id.recycler_email);
            mobileNumber = itemView.findViewById(R.id.recycler_mobile_number);
            branch = itemView.findViewById(R.id.recycler_branch);

        }
    }


}

