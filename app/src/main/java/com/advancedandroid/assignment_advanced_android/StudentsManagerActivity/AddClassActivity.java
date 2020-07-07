package com.advancedandroid.assignment_advanced_android.StudentsManagerActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.advancedandroid.assignment_advanced_android.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.assignment_advanced_android.R;

public class AddClassActivity extends AppCompatActivity {

    EditText edtClassName, edtClassCode;
    Button btnCancel, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        initView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtClassCode.getText().toString();
                String name = edtClassName.getText().toString();

                if (!name.isEmpty() && !code.isEmpty()){
                    DatabaseDAO db = new DatabaseDAO(AddClassActivity.this);
                    boolean result = db.addClass(code, name);

                    if (result == false){
                        Toast.makeText(AddClassActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddClassActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    clear();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddClassActivity.this, ClassAndStudentsManager.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initView(){
        edtClassName = findViewById(R.id.addClass_edt2);
        edtClassCode = findViewById(R.id.addClass_edt1);
        btnAdd = findViewById(R.id.addClass_btn2);
        btnCancel = findViewById(R.id.addClass_btn1);
    }

    private void clear(){
        edtClassCode.getText().clear();
        edtClassName.getText().clear();
    }

}
