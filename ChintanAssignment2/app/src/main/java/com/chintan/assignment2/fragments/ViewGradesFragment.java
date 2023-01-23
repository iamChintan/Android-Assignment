package com.chintan.assignment2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chintan.assignment2.DatabaseHandler;
import com.chintan.assignment2.FormModel;
import com.chintan.assignment2.R;
import com.chintan.assignment2.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewGradesFragment extends Fragment {

    // declaration of recyclerview
    private RecyclerView recyclerView;
    private DatabaseHandler db;
    List<FormModel> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewgrades, container, false);
        list = new ArrayList<>();
        // database initialization
        db = new DatabaseHandler(getActivity());
        list = db.getAllGrades();
        // for recyclerview
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if (list.size() > 0){
            recyclerView.setAdapter(new ItemAdapter(list, getActivity()));
        }else {
            Toast.makeText(getContext(), "No record found..!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
