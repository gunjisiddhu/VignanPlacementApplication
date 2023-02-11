package com.vignan.vignan_placement_application.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.super_admin.Company;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompanyDisplayAdapter extends RecyclerView.Adapter<CompanyDisplayAdapter.CompanyViewHolder> {

    ArrayList<Company> companyArrayList;
    Context context;
    CompanyOnClick companyOnClick;
    CardView cardView;

    public void filteredCompanies(ArrayList<Company> filteredCompanies) {
        this.companyArrayList = filteredCompanies;
        notifyDataSetChanged();
    }
    public CompanyDisplayAdapter(ArrayList<Company> companyArrayList,Context context,CompanyOnClick company) {
        this.companyArrayList = companyArrayList;
        this.context = context;
        this.companyOnClick = company;
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

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.companies_scrolling_animation));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyOnClick.onClickListener(company);
            }
        });




    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }


    static class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView companyName,companyStatus,companyCTC;
        CardView cardView;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.superAdmin_comapanyName_display);
            companyStatus = itemView.findViewById(R.id.superAdmin_companyStatus_edit);
            companyCTC = itemView.findViewById(R.id.superAdmin_companyctc_edit);
            cardView = itemView.findViewById(R.id.company_card_view);
        }
    }


    public interface CompanyOnClick{
        void onClickListener(Company company);
    }



}

