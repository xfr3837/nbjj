package com.xfr.nbjj;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.xfr.nbjj.students.Student;
import com.xfr.nbjj.students.StudentAdapter;


import java.util.List;

/**
 * 用来做一些测试
 *
 */
public class SearchStudentsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_students);

        new GetData().execute();
    }

    class GetData extends AsyncTask<String, Integer, List<Student>> {
        @Override
        protected List<Student> doInBackground(String... strings) {
            List<Student> studentList = Searcher.getOnePageStudentsInfo(SearchStudentsActivity.this,
                    Searcher.getStudentsURL("", "", "",
                            "", "", "",
                            ""));
            return studentList;
        }

        @Override
        protected void onPostExecute(List<Student> studentList) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(SearchStudentsActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            StudentAdapter adapter = new StudentAdapter(studentList);
            recyclerView.setAdapter(adapter);
        }
    }
}
