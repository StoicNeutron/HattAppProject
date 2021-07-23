package com.samnangthorn.hatt_app10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    ArrayList<String> exerciseName, mT, sT, des;
    Context context;
    onExeClickListener onExeClickListener;

    public RVAdapter (Context context, ArrayList<String> Name,onExeClickListener onExeClickListener){
        this.context = context;
        this.exerciseName = Name;
        this.onExeClickListener = onExeClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_layout, parent, false);

        return new MyViewHolder(view, onExeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_1.setText(exerciseName.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_1;
        onExeClickListener onExeClickListener;

        public MyViewHolder(@NonNull View itemView, onExeClickListener onExeClickListener) {

            super(itemView);
            txt_1 = itemView.findViewById(R.id.txt_exerciseName);
            this.onExeClickListener = onExeClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onExeClickListener.onExeClick(getAdapterPosition(), exerciseName);
        }
    }
    public interface onExeClickListener{
        void onExeClick(int position, ArrayList<String> nameList );
    }
}
