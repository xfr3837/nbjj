package com.xfr.nbjj.students;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfr.nbjj.R;

import java.util.List;

/**
 * Created by xfr on 2018/1/30.
 */

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> mStudentList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentInfo;

        public ViewHolder(View view) {
            super(view);
            studentInfo = (TextView) view.findViewById(R.id.test_txt);
        }
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.studentInfo.setText(student.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public StudentAdapter(List<Student> testList) {
        mStudentList = testList;
    }
}
