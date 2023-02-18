package com.vignan.vignan_placement_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vignan.vignan_placement_application.dept_cordinator.Coordinator;

import java.util.ArrayList;

public class CoordinatorDisplayAdapter extends RecyclerView.Adapter<CoordinatorDisplayAdapter.CoordinatorViewHolder> {

    ArrayList<Coordinator> coordinatorList = new ArrayList<>();
    Context context;

    CoordinatorOnClick coordinatorOnClick;
    public CoordinatorDisplayAdapter(ArrayList<Coordinator> coordinatorList,Context context,CoordinatorOnClick coordinatorOnClick) {
        this.coordinatorList = coordinatorList;
        this.context = context;
        this.coordinatorOnClick = coordinatorOnClick;
    }
    public void filteredCoordinators(ArrayList<Coordinator> filteredCoordinators) {
        this.coordinatorList = filteredCoordinators;
        notifyDataSetChanged();
    }

    static class CoordinatorViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name,branch;

        CardView cardView;

        public CoordinatorViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.coordinator_image);
            name = itemView.findViewById(R.id.coordinator_name);
            branch = itemView.findViewById(R.id.coordinator_branch);
            cardView = itemView.findViewById(R.id.coordinator_card);
        }
    }



    @NonNull
    @Override
    public CoordinatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_coordinator_display,parent,false);
        CoordinatorViewHolder viewHolder = new CoordinatorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinatorViewHolder holder, int position) {
        CoordinatorsDisplayHelperClass coordinatorsDisplayHelperClass;
        Coordinator coordinator = coordinatorList.get(position);
        if(coordinator.getGender().equalsIgnoreCase("male"))
            coordinatorsDisplayHelperClass = new CoordinatorsDisplayHelperClass(coordinator.getName(),coordinator.getBranch(),R.drawable.male);
        else
            coordinatorsDisplayHelperClass = new CoordinatorsDisplayHelperClass(coordinator.getName(),coordinator.getBranch(),R.drawable.female);
        holder.image.setImageResource(coordinatorsDisplayHelperClass.getImage());
        holder.name.setText(coordinatorsDisplayHelperClass.getName());
        holder.branch.setText(coordinatorsDisplayHelperClass.getBranch());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.companies_scrolling_animation));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatorOnClick.onClickListener(coordinator);
            }
        });

    }

    @Override
    public int getItemCount() {
        return coordinatorList.size();
    }

    public interface CoordinatorOnClick{
        void onClickListener(Coordinator coordinator);
    }

}
