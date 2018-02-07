package com.xfr.nbjj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StudentDetailActivity extends AppCompatActivity {

    private String studentNumber;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        Intent intent = getIntent();
        studentNumber = intent.getStringExtra("student_number");

        textView = findViewById(R.id.student_detail_number);
        textView.setText(studentNumber);
    }
}
