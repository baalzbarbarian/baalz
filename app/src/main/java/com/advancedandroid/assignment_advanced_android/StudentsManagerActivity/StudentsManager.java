package com.advancedandroid.assignment_advanced_android.StudentsManagerActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.advancedandroid.assignment_advanced_android.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.assignment_advanced_android.MyAdapterSimple.StudentsAdapter;
import com.advancedandroid.assignment_advanced_android.R;
import com.advancedandroid.assignment_advanced_android.mModel.mStudent;

import java.util.List;

public class StudentsManager extends AppCompatActivity {

    String code;
    String name;
    ListView listView;
    StudentsAdapter adapter;
    List<mStudent> mStudentList;
    DatabaseDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_manager2);

        Bundle bundle = getIntent().getExtras();
        code = bundle.getString("code");
        name = bundle.getString("name");

        TextView txtmaLop = findViewById(R.id.txtmaLop);
        TextView txtNganh = findViewById(R.id.txtNganh);

        txtmaLop.setText("@"+code);
        txtNganh.setText(name);

        initListView();

        findViewById(R.id.btnAddStudents)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        db = new DatabaseDAO(StudentsManager.this);
        mStudentList = db.getStudentsByCode2(code);
        adapter = new StudentsAdapter(StudentsManager.this, mStudentList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentsManager.this);
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View viewUpdate = inflater.inflate(R.layout.dialog_update_student, null);
                builder.setView(viewUpdate);
                final Dialog dialog = builder.create();
                dialog.show();

                final EditText edtClassCode = dialog.findViewById(R.id.updateStudents_classCode);
                final EditText edtStuCode = dialog.findViewById(R.id.updateStudents_studentCode);
                final EditText edtStuName = dialog.findViewById(R.id.updateStudents_studenName);
                final EditText edtStuBorn = dialog.findViewById(R.id.updateStudents_born);

                //Gán dữ liệu cũ lên dialog
                edtClassCode.setText(mStudentList.get(position).getClassCode());
                edtStuCode.setText(mStudentList.get(position).getStudentCode());
                edtStuName.setText(mStudentList.get(position).getName());
                edtStuBorn.setText(mStudentList.get(position).getBorn());
                
                dialog.findViewById(R.id.updateStudents_btn2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db = new DatabaseDAO(StudentsManager.this);
                        mStudent m = new mStudent(
                                mStudentList.get(position).getId(),
                                edtClassCode.getText().toString(),
                                edtStuCode.getText().toString(),
                                edtStuName.getText().toString(),
                                edtStuBorn.getText().toString()
                        );
                        if (db.updateInfoStudent(m) > 0){
                            Toast.makeText(StudentsManager.this, "Successfully", Toast.LENGTH_SHORT).show();
                            onResume();
                        }else {
                            Toast.makeText(StudentsManager.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.findViewById(R.id.updateStudents_btn1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StudentsManager.this);
                        builder.setTitle("Xóa hóa đơn")
                                .setMessage("Bạn chắc chắn muốn xóa sinh viên này?")
                                .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (db.deleteStudent(mStudentList.get(position).getId()) > 0){
                                            Toast.makeText(StudentsManager.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            onResume();

                                        }else {
                                            Toast.makeText(StudentsManager.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                    }
                });

                dialog.findViewById(R.id.updateStudents_btn3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(StudentsManager.this, "ABC", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentsManager.this);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_custom_add_students, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();

        final EditText edtCode = dialog.findViewById(R.id.addStudents_classCode);
        final EditText edtName = dialog.findViewById(R.id.addStudents_studenName);
        final EditText edtBorn = dialog.findViewById(R.id.addStudents_born);
        final EditText edtStuCode = dialog.findViewById(R.id.addStudents_studentCode);

        edtCode.setText(code);

        dialog.findViewById(R.id.addStudents_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudentList = db.getStudentsByCode2(code);
                mStudent m = new mStudent(
                        0,
                        code,
                        edtStuCode.getText().toString(),
                        edtName.getText().toString(),
                        edtBorn.getText().toString()
                );

                DatabaseDAO db = new DatabaseDAO(StudentsManager.this);
                if (db.addStudent(m) > 0){
                    adapter = new StudentsAdapter(StudentsManager.this, mStudentList);
                    listView.setAdapter(adapter);
                    onResume();
                    Toast.makeText(getApplicationContext(), "Add Student Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        dialog.findViewById(R.id.addStudents_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void onResume() {
        super.onResume();
        mStudentList.clear();
        mStudentList = db.getStudentsByCode2(code);
        adapter.updateData(mStudentList);
    }

}
