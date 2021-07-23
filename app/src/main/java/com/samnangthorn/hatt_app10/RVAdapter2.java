package com.samnangthorn.hatt_app10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter2 extends RecyclerView.Adapter<RVAdapter2.MyViewHolder2> {

    ArrayList<String> exerciseName, mT, sT, des;
    Context context;
    onExeClickListener2 onExeClickListener2;

    public RVAdapter2(Context context, ArrayList<String> Name, onExeClickListener2 onExeClickListener2){
        this.context = context;
        this.exerciseName = Name;
        this.onExeClickListener2 = onExeClickListener2;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_layout2, parent, false);

        return new MyViewHolder2(view, onExeClickListener2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.txt_1.setText(exerciseName.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseName.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_1;
        onExeClickListener2 onExeClickListener2;

        public MyViewHolder2(@NonNull View itemView, onExeClickListener2 onExeClickListener2) {

            super(itemView);
            txt_1 = itemView.findViewById(R.id.txt_exerciseName);
            this.onExeClickListener2 = onExeClickListener2;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onExeClickListener2.onExeClick2(getAdapterPosition(), exerciseName);
        }
    }
    public interface onExeClickListener2{
        void onExeClick2(int position, ArrayList<String> nameList);
    }
}
