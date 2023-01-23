package com.chintan.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gradingapp";

    // Grades table name
    private static final String TABLE_CONTENT = "Grading";

    // Grades Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_COURSE = "course";
    private static final String KEY_CREDIT = "credit";
    private static final String KEY_MARKS = "marks";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GradeS_TABLE = "CREATE TABLE "
                + TABLE_CONTENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT,"
                + KEY_COURSE + " TEXT,"
                + KEY_CREDIT + " INTEGER,"
                + KEY_MARKS + " DOUBLE"
                + ")";
        db.execSQL(CREATE_GradeS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */


    // adding data
    public boolean addGrade(FormModel grade){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, grade.firstName);
        values.put(KEY_LASTNAME, grade.lastName);
        values.put(KEY_COURSE, grade.course);
        values.put(KEY_CREDIT, grade.credit);
        values.put(KEY_MARKS, grade.marks);

        long result = db.insert(TABLE_CONTENT,null,values);

        if(result == -1) {
            return false;
        }else{
            //  Log.e(TAG,"value inserted");
            return true;
        }

    }

    // Getting single grade
    public FormModel getGrade(int id, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTENT, new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_COURSE, KEY_CREDIT, KEY_MARKS}, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }else {
            Toast.makeText(context, "ID not found, Enter Valid ID", Toast.LENGTH_SHORT).show();
        }

        FormModel grade = new FormModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));
//        FormModel grade = new FormModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
        // return grade
        return grade;
    }

    // Getting single grade
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Grading where id="+id+"", null );
        return res;
    }

    // Getting list of data based on the subject code
    public Cursor getAllGradesWithProjectCode(String code) {

        SQLiteDatabase db = this.getWritableDatabase();
        List<FormModel> gradeList = new ArrayList<FormModel>();

        String query = "SELECT * FROM Grading WHERE course = '"+code+"'";
        Log.e("query", "getAllGradesWithProjectCode: " + query);

        Cursor  cursor = db.rawQuery(query,null);

        return cursor;

    }



    // Getting All Grades
    public List<FormModel> getAllGrades() {
        List<FormModel> gradeList = new ArrayList<FormModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FormModel grade = new FormModel();
                grade.firstName = cursor.getString(1);
                grade.lastName = cursor.getString(2);
                grade.course = cursor.getString(3);
                grade.credit = cursor.getInt(4);
                grade.marks = cursor.getInt(5);


                // Adding grade to list
                gradeList.add(grade);
            } while (cursor.moveToNext());
        }

        // return grade list
        return gradeList;
    }

    
    // Getting grades Count
    public int getGradesCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        cursor.moveToFirst();
        // return count
        return cursor.getCount();
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.


        }
        return checkDB != null;
    }



}