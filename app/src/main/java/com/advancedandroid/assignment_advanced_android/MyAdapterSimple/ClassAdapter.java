package com.advancedandroid.assignment_advanced_android.MyAdapterSimple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.advancedandroid.assignment_advanced_android.R;
import com.advancedandroid.assignment_advanced_android.mModel.mClass;
import com.advancedandroid.assignment_advanced_android.mModel.mStudent;

import java.util.List;

public class ClassAdapter extends BaseAdapter {
    private Context context;
    private List<mClass> ClassList;

    public ClassAdapter(Context context, List<mClass> objects) {
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
            viewHolder.tv_clId = convertView.findViewById(R.id.txt1);
            viewHolder.tv_clName = convertView.findViewById(R.id.txt2);
            viewHolder.tv_clCode = convertView.findViewById(R.id.txt3);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mClass mClass = ClassList.get(position);
        viewHolder.tv_clId.setText(String.valueOf(position+1));
        viewHolder.tv_clName.setText(mClass.getClassCode());
        viewHolder.tv_clCode.setText(mClass.getSpeciality());

        return convertView;
    }


    public class ViewHolder{
        private TextView tv_clId;
        private TextView tv_clName;
        private TextView tv_clCode;
    }

    public void updateData(List<mClass> mUsers) {
        if (mUsers == null) {
            return;
        }
        ClassList.clear();
        ClassList.addAll(mUsers);
        notifyDataSetChanged();
    }
}
