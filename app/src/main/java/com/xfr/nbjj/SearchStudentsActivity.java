package com.xfr.nbjj;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.LinearLayout;


import com.xfr.nbjj.students.Student;
import com.xfr.nbjj.students.StudentAdapter;


import java.util.List;

/**
 * 搜索学生
 * Created by xfr on 2018/2/3
 */
public class SearchStudentsActivity extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private StudentAdapter mAdapter;
    private List<Student> students;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_students);

        initView();
        setListener();

        new GetData().execute(Searcher.getStudentsURL("", "",
                "", "", "", "",
                ""));
    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.search_students_linearLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));
    }


    private void setListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (lastVisibleItem + 5 >= mLayoutManager.getItemCount()) {
                    new GetData().execute(Searcher.getStudentsURL((++ page) + "",
                            "", "", "", "",
                            "", ""));
                }
            }
        });
    }

    class GetData extends AsyncTask<String, Integer, List<Student>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Student> doInBackground(String... strings) {
            return Searcher.getOnePageStudentsInfo(SearchStudentsActivity.this, strings[0]);
        }

        @Override
        protected void onPostExecute(List<Student> studentList) {

            if (students == null || students.size() == 0) {
                students = studentList;

            } else {
                List<Student> more = studentList;
                students.addAll(studentList);
            }

            if (mAdapter == null) {
                recyclerView.setAdapter(mAdapter = new StudentAdapter(students));
            } else {
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
