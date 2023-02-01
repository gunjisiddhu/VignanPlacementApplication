package com.vignan.vignan_placement_application.super_admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vignan.vignan_placement_application.R;


import java.util.ArrayList;

public class CompanyAddingListAdapter extends ArrayAdapter<String> {


    ArrayList<String> branches;
    Context context;


    public CompanyAddingListAdapter(@NonNull Context context, int resource,ArrayList<String> branches) {
        super(context, R.layout.items_companylist_row,branches);
        this.context = context;
        this.branches = branches;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_companylist_row,parent,false);
        TextView branch = view.findViewById(R.id.branch_name);
        ImageView deleteImage = view.findViewById(R.id.delete_item_from_list);

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cartDataList.remove(position);
                //CheckOutAdapter.this.notifyDataSetChanged();
                if (context instanceof RemoveItemsFromBranchList) {
                    ((CompanyInsertion) context).removeItemFromList(position);
                }
            }
        });

        branch.setText(branches.get(position));
        return view;
    }
}
