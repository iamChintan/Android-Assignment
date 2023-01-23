package com.chintan.assignment2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chintan.assignment2.DatabaseHandler;
import com.chintan.assignment2.FormModel;
import com.chintan.assignment2.MainActivity;
import com.chintan.assignment2.R;


public class GradeEntryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // list of available course
    String[] users = {"PROG 8480", "PROG 8470", "PROG 8460", "PROG 8450"};
    // declaration of all widgets
    private RadioGroup subGroup;
    private RadioButton subCredit;
    private Button btnDisplay;
    EditText fName, lName, marks;
    private DatabaseHandler db;
    String Course = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gradeentry, container, false);

        // databse initialization
        db = new DatabaseHandler(getActivity());


        // finding widgets using findViewById method
        subGroup = (RadioGroup) rootView.findViewById(R.id.subGroup);
        btnDisplay = (Button) rootView.findViewById(R.id.submit);
        fName = (EditText) rootView.findViewById(R.id.fName);
        lName = (EditText) rootView.findViewById(R.id.lName);
        marks = (EditText) rootView.findViewById(R.id.marks);


        Spinner spin = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        // click of submit button
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // datavalidation to prevent error
                if (fName.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lName.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (marks.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Marks", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedId = subGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                subCredit = (RadioButton) rootView.findViewById(selectedId);

                FormModel model = new FormModel();
                model.setFirstName(fName.getText().toString().trim());
                model.setLastName(lName.getText().toString().trim());
                model.setCourse(Course);
                model.setCredit(Integer.parseInt(subCredit.getText().toString()));
                model.setMarks(Double.parseDouble(marks.getText().toString().trim().toString()));

                boolean x = db.addGrade(model);
//                db.addGrade(new FormModel(01,"a","v","c",2,3));
                if (x){
                    Toast.makeText(getActivity(), "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    ClearAllField();
                }else {
                    Toast.makeText(getActivity(), "Oops..! Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    // clear all the fields after successfully saving data
    private void ClearAllField() {
        fName.setText("");
        lName.setText("");
        marks.setText("");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Course = users[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
