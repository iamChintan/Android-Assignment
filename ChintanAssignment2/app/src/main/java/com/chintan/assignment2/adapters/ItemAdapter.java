package com.chintan.assignment2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chintan.assignment2.FormModel;
import com.chintan.assignment2.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    // declarion of listiew
    List<FormModel> itemList1;
    private Context context;

    // constructor
    public ItemAdapter(List<FormModel> itemList, Context context) {

        this.itemList1 = itemList;
        this.context = context;

    }

    // viewholder
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_raw, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //binding data
    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, final int position) {

        holder.fname.setText(itemList1.get(position).getFirstName());
        holder.lname.setText(itemList1.get(position).getLastName());
        holder.course.setText(itemList1.get(position).getCourse());
        holder.credit.setText(String.valueOf(itemList1.get(position).getCredit()));
        holder.marks.setText(String.valueOf(itemList1.get(position).getMarks()));


    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // declaation of widget
        TextView fname, lname, course, credit, marks;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);
            course = itemView.findViewById(R.id.course);
            credit = itemView.findViewById(R.id.credit);
            marks = itemView.findViewById(R.id.marks);

        }
    }
}