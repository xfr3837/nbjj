package com.xfr.nbjj;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xfr.nbjj.students.StudentDetail;

/**
 * 学生详情页面
 * Create by xfr on 2018/2/7
 */

public class StudentDetailActivity extends AppCompatActivity {

    private String studentNumber;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        Intent intent = getIntent();
        studentNumber = intent.getStringExtra("student_number");

        initView();
        new GetData().execute(studentNumber);
    }

    private void initView() {
        textView = findViewById(R.id.student_detail_number);
    }

    // 参考 《第一行代码》第二版 10.2.4
    class GetData extends AsyncTask<String, Integer, StudentDetail> {

        // 加载数据之前调用，可以在此处显示进度条
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 加载数据，此方法必须重写
        @Override
        protected StudentDetail doInBackground(String... strings) {
            return MyOkHttp.getStudentDetailInfo(StudentDetailActivity.this, strings[0]);
        }

        // 加载数据结束之后调用，可以在这里隐藏进度条
        @Override
        protected void onPostExecute(StudentDetail studentDetail) {
            textView.setText(studentDetail.toString());
        }
    }
}
