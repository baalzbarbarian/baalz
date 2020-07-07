package com.advancedandroid.assignment_advanced_android.DatabaseDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.advancedandroid.assignment_advanced_android.mModel.mClass;
import com.advancedandroid.assignment_advanced_android.mModel.mStudent;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private static final String DATABASE_NAME = "fpolyManager";
    private static final String CLASSTABLE = "classfpoly";
    private static final String STUDENTSTABLE = "students";
    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String STUDENTCODE = "studentcode";
    private static final String CLASSNAME = "clname";
    private static final String STNAME = "stname";
    private static final String BORN = "born";
    private static final String TAG = "Database";

    private static final String classTable = "CREATE TABLE " +CLASSTABLE+ "(" +
            ID+ " integer PRIMARY KEY AUTOINCREMENT, " +
            CODE+ " TEXT, " +
            CLASSNAME+ " TEXT);";

    private static final String stTable = "CREATE TABLE if not exists " +STUDENTSTABLE+ " (" +
            ID+ " integer PRIMARY KEY AUTOINCREMENT, " +
            CODE+ " TEXT not null, " +
            STUDENTCODE+ " TEXT not null, " +
            STNAME+ " TEXT, " +
            BORN+ " TEXT, " +
            "FOREIGN KEY ( "+CODE+" ) REFERENCES " +CLASSTABLE + " ( "+CODE+" ));";

    public DatabaseDAO(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(classTable);
        db.execSQL(stTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +CLASSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " +STUDENTSTABLE);
        onCreate(db);
    }

    public boolean addClass (String code, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, code);
        values.put(CLASSNAME, name);

        long result = db.insert(CLASSTABLE, null, values);
        Log.e(null, "addClass: "+result);
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public List<mClass> getAllClass() {
        db = this.getWritableDatabase();
        List<mClass> listClass = new ArrayList<>();
        String sql = "SELECT * FROM " +CLASSTABLE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mClass m = new mClass();
            m.setId(Integer.parseInt(cursor.getString(0)));
            m.setClassCode(cursor.getString(1));
            m.setSpeciality(cursor.getString(2));
            listClass.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return listClass;
    }

    public int addStudent (mStudent mStudents) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, mStudents.getClassCode());
        values.put(STUDENTCODE, mStudents.getStudentCode());
        values.put(STNAME, mStudents.getName());
        values.put(BORN, mStudents.getBorn());
        long result = db.insert(STUDENTSTABLE, null, values);
        Log.e("Data", "addStudent: "+ result);
        try {
            if( result < 0) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;

    }

    public List<mStudent> getStudentsByCode2(String code) {
        db = this.getReadableDatabase();
        List<mStudent> st = new ArrayList<>();
        String sql = "SELECT * FROM "+STUDENTSTABLE+" WHERE "+CODE+ " LIKE "+"'"+code+"'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mStudent mStudents = new mStudent();
                mStudents.setId(cursor.getInt(0));
                mStudents.setClassCode(cursor.getString(1));
                mStudents.setStudentCode(cursor.getString(2));
                mStudents.setName(cursor.getString(3));
                mStudents.setBorn(cursor.getString(4));
                st.add(mStudents);
            }while (cursor.moveToNext());
        }

        cursor.close();
        Log.e(TAG, "getStudentsByCode2: "+st.size());
        return st;
    }

    public int updateInfoStudent(mStudent mStudent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STUDENTCODE, mStudent.getStudentCode());
        values.put(STNAME, mStudent.getName());
        values.put(BORN, mStudent.getBorn());

        int result=  db.update(STUDENTSTABLE, values,
                ID+ " =?",
                new String[]{String.valueOf(mStudent.getId())});
        db.close();
        Log.e(TAG, "updateInfoStudent: "+result);
        return result;
    }

    public int deleteStudent(int id){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.assignment_advanced_android/databases/fpolyManager", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(STUDENTSTABLE, ID+ "=?",
                new String[]{String.valueOf(id)});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int deleteStudentWithClassCode(String classCode){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.assignment_advanced_android/databases/fpolyManager", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(STUDENTSTABLE, CODE+ "=?",
                new String[]{classCode});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int deleteClass(int id){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.assignment_advanced_android/databases/fpolyManager", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(CLASSTABLE, ID+ "=?",
                new String[]{String.valueOf(id)});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

}
