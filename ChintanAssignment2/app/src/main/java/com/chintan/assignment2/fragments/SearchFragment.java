package com.chintan.assignment2.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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


public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // declarion of all the widgets
    private RadioGroup searchGroup;
    private RadioButton id, code;
    String[] users = {"PROG 8480", "PROG 8470", "PROG 8460", "PROG 8450"};
    String Course = "";
    String selected = "";
    private Button btnDisplay;
    LinearLayout idLayout, codelayout;
    ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private DatabaseHandler db;
    List<FormModel> list;
    EditText gradeID;
    FormModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // finding widgets using findViewById method
        searchGroup = (RadioGroup) rootView.findViewById(R.id.searchGroup);
        btnDisplay = (Button) rootView.findViewById(R.id.search);
        idLayout = (LinearLayout) rootView.findViewById(R.id.idLayout);
        codelayout = (LinearLayout) rootView.findViewById(R.id.codelayout);
        gradeID = (EditText) rootView.findViewById(R.id.gradeID);

        model = new FormModel();
        Toast.makeText(getActivity(), ""+model.getFirstName(), Toast.LENGTH_SHORT).show();

        // for dropdown menu
        Spinner spin = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        selected = "id";

        list = new ArrayList<>();
        db = new DatabaseHandler(getActivity());
        // for recyclerview
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        itemAdapter = new ItemAdapter(list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(itemAdapter);

        if (list.size() > 0){
            itemAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(), "No record found..!", Toast.LENGTH_SHORT).show();
        }

        // find the radiobutton by returned id
        id = (RadioButton) rootView.findViewById(R.id.ids);
        code = (RadioButton) rootView.findViewById(R.id.code);

        if(id.isChecked())
        {
            idLayout.setVisibility(View.VISIBLE);
            codelayout.setVisibility(View.GONE);
        }
        else
        {
            idLayout.setVisibility(View.GONE);
            codelayout.setVisibility(View.VISIBLE);
        }

        // radio button checked
        id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selected = "id";
                    idLayout.setVisibility(View.VISIBLE);
                    codelayout.setVisibility(View.GONE);

                    if (list.size() > 0){
                        list.clear();
                        itemAdapter.notifyDataSetChanged();
                    }

                }

            }
        });

        code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selected = "code";
                    idLayout.setVisibility(View.GONE);
                    codelayout.setVisibility(View.VISIBLE);

                    if (list.size() > 0){
                        list.clear();
                        itemAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // click of search button
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected.equals("id")){
                    if (gradeID.getText().toString().trim().length() > 0){

                        Cursor c = db.getData(Integer.parseInt(gradeID.getText().toString().trim()));

                        if (c.getCount() == 0){
                            Toast.makeText(getActivity(), "Please Enter valid ID", Toast.LENGTH_SHORT).show();
                            list.clear();
                            itemAdapter.notifyDataSetChanged();
                            return;
                        }

                        if (c != null){
                            c.moveToFirst();
                        }

                        FormModel grade = new FormModel(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), Integer.parseInt(c.getString(4)), Double.parseDouble(c.getString(5)));
                        list.clear();
                        list.add(grade);
                        itemAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getActivity(), "Please Enter valid ID", Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Cursor c = db.getAllGradesWithProjectCode(Course);

                    if (c.getCount() == 0){
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        list.clear();
                        itemAdapter.notifyDataSetChanged();
                        return;
                    }

                    if (c.moveToFirst()) {
                        do {

                             FormModel grade = new FormModel(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), Integer.parseInt(c.getString(4)), Double.parseDouble(c.getString(5)));

                            // Adding grade to list
                            list.add(grade);
                            Log.e("list", "onClick: " + list);
                        } while (c.moveToNext());
                    }

                    if (list.size() == 0){
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        list.clear();
                        itemAdapter.notifyDataSetChanged();
                        return;
                    }

                    itemAdapter.notifyDataSetChanged();
                }


            }
        });



        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Course = users[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
