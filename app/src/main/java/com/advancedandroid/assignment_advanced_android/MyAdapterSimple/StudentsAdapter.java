package com.advancedandroid.assignment_advanced_android.MyAdapterSimple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.advancedandroid.assignment_advanced_android.R;
import com.advancedandroid.assignment_advanced_android.mModel.mStudent;

import java.util.List;

public class StudentsAdapter extends BaseAdapter {
    private Context context;
    private List<mStudent> ClassList;

    public StudentsAdapter(Context context, List<mStudent> objects) {
        super();
        this.context = context;
        this.ClassList = objects;
    }

    @Override
    public int getCount() {
        return ClassList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.students_manager_custom_listview,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.maSv = convertView.findViewById(R.id.txt1);
            viewHolder.tenSv = convertView.findViewById(R.id.txt2);
            viewHolder.tenNganh = convertView.findViewById(R.id.txt3);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mStudent mClass = ClassList.get(position);
        viewHolder.maSv.setText(mClass.getStudentCode());
        viewHolder.tenSv.setText(mClass.getName());
        viewHolder.tenNganh.setText(mClass.getBorn());

        return convertView;
    }


    public class ViewHolder{
        private TextView maSv;
        private TextView tenSv;
        private TextView tenNganh;
    }

    public void updateData(List<mStudent> mUsers) {
        if (mUsers == null) {
            return;
        }
        ClassList.clear();
        ClassList.addAll(mUsers);
        notifyDataSetChanged();
    }
}
