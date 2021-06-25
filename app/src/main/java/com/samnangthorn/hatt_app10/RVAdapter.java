package com.samnangthorn.hatt_app10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    ArrayList<String> exerciseName;
    Context context;

    public RVAdapter (Context context, ArrayList<String> Name){
        this.context = context;
        this.exerciseName = Name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_1.setText(exerciseName.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_1;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            txt_1 = itemView.findViewById(R.id.txt_exerciseName);
        }
    }
}
