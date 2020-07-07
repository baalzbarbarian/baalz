package com.advancedandroid.assignment_advanced_android.StudentsManagerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.advancedandroid.assignment_advanced_android.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.assignment_advanced_android.MyAdapterSimple.ClassAdapter;
import com.advancedandroid.assignment_advanced_android.R;
import com.advancedandroid.assignment_advanced_android.mModel.mClass;

import java.util.List;

public class ClassAndStudentsManager extends AppCompatActivity {

    ListView listView;
    List<mClass> mClassList;
    mClass m;
    ClassAdapter adapter;
    DatabaseDAO db;
    private String TAG = "ClassAndStudentsManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_dialog);
        initView();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    private void initView(){
        listView = findViewById(R.id.listViewStudentManager);
        db = new DatabaseDAO(ClassAndStudentsManager.this);
        mClassList = db.getAllClass();
        adapter = new ClassAdapter(ClassAndStudentsManager.this, mClassList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ClassAndStudentsManager.this, StudentsManager.class);

                m = mClassList.get(position);
                String code = m.getClassCode();
                String name = m.getSpeciality();

                Bundle bundle = new Bundle();
                bundle.putString("code", code);
                bundle.putString("name", name);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClassAndStudentsManager.this);
                builder.setTitle("Xóa hóa đơn")
                        .setMessage("Bạn chắc chắn muốn xóa Lớp này?")
                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(db.deleteClass(mClassList.get(position).getId()) > 0){
                                    if (db.deleteStudentWithClassCode(mClassList.get(position).getClassCode()) > 0){
                                        Toast.makeText(ClassAndStudentsManager.this, "Xóa lớp và sinh viên của lớp thành công", Toast.LENGTH_SHORT).show();
                                    }
                                    onResume();
                                }else {
                                    Toast.makeText(ClassAndStudentsManager.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_setting_seach:
                //
                return true;
            case R.id.action_setting_add:
                i = new Intent(this, AddClassActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onResume() {
        super.onResume();
        mClassList.clear();
        mClassList = db.getAllClass();
        adapter.updateData(mClassList);
    }

}
