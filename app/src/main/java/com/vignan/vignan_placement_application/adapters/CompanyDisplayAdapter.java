package com.vignan.vignan_placement_application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.super_admin.Company;

import java.util.ArrayList;

public class CompanyDisplayAdapter extends RecyclerView.Adapter<CompanyDisplayAdapter.CompanyViewHolder> {

    ArrayList<Company> companyArrayList;

    public CompanyDisplayAdapter(ArrayList<Company> companyArrayList) {
        this.companyArrayList = companyArrayList;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compay_views,parent,false);
        CompanyDisplayAdapter.CompanyViewHolder viewHolder = new CompanyDisplayAdapter.CompanyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {

        Company company = companyArrayList.get(position);
        holder.companyName.setText(company.getCompanyName());
        holder.companyStatus.setText(company.getStatus());
        holder.companyCTC.setText(company.getCtc());
    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }


    static class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView companyName,companyStatus,companyCTC;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.superAdmin_comapanyName_display);
            companyStatus = itemView.findViewById(R.id.superAdmin_companyStatus_edit);
            companyCTC = itemView.findViewById(R.id.superAdmin_companyctc_edit);

        }
    }


}