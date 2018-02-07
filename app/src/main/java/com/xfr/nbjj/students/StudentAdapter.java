package com.xfr.nbjj.students;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfr.nbjj.R;
import com.xfr.nbjj.StudentDetailActivity;

import java.util.List;

/**
 * Created by xfr on 2018/1/30.
 */

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> mStudentList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // 为了添加点击事件
        private View studentView;

        private TextView studentNumber;
        private TextView studentTop;
        private TextView studentStatus;
        private TextView studentTime;
        private TextView studentInfo;

        public ViewHolder(View view) {
            super(view);
            studentView = view;
            studentNumber = (TextView) view.findViewById(R.id.student_number);
            studentTop = (TextView) view.findViewById(R.id.student_top);
            studentStatus = (TextView) view.findViewById(R.id.student_status);
            studentTime = (TextView) view.findViewById(R.id.student_time);
            studentInfo = (TextView) view.findViewById(R.id.student_info);
        }
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.studentNumber.setText(student.getNumber());
        holder.studentTop.setText(student.getTop());
        holder.studentStatus.setText(student.getStatus());
        holder.studentTime.setText(student.getTime());
        holder.studentInfo.setText(student.getInfo());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.studentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Student student = mStudentList.get(position);
                Intent intent = new Intent(mContext, StudentDetailActivity.class);
                intent.putExtra("student_number", student.getNumber());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    public StudentAdapter(List<Student> studentListList, Context context) {
        mStudentList = studentListList;
        mContext = context;
    }
}
