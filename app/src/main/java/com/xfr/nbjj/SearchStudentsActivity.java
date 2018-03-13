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
 * 参考了 https://github.com/zhaochenpu/RecyclerViewDemo
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

        // 初始化 View
        initView();
        // 设置监听器，以便在合适的情况下加载下一页
        setListener();

        // 打开页面时加载第一页数据
        new GetData().execute(MyOkHttp.getStudentsURL("", "",
                "", "", "", "",
                ""));
    }

    // 参考自 https://github.com/zhaochenpu/RecyclerViewDemo
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

    // 参考自 https://github.com/zhaochenpu/RecyclerViewDemo
    private void setListener() {

        // RecyclerView 监听器
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 滑动结束时调用
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

            //滑动状态改变时调用
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 当最后一个可见 Item，也就是屏幕中最后一个 Item 距离内存中最后一个 Item 不足五个的时候
                // 自动加载下一页
                if (lastVisibleItem + 5 >= mLayoutManager.getItemCount()) {
                    new GetData().execute(MyOkHttp.getStudentsURL((++ page) + "",
                            "", "", "", "",
                            "", ""));
                }
            }
        });
    }

    // 参考 《第一行代码》第二版 10.2.4
    class GetData extends AsyncTask<String, Integer, List<Student>> {

        // 加载数据之前调用，可以在此处显示进度条
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 加载数据，此方法必须重写
        @Override
        protected List<Student> doInBackground(String... strings) {
            return MyOkHttp.getOnePageStudentsInfo(SearchStudentsActivity.this, strings[0]);
        }

        // 加载数据结束之后调用，可以在这里隐藏进度条
        @Override
        protected void onPostExecute(List<Student> studentList) {

            if (students == null || students.size() == 0) {
                // 第一次打开这个 Activity 时 students 是空的，直接赋值加载的数据给 students
                students = studentList;

            } else {
                // 当 students 不为空，就需要将加载的数据添加进去
                List<Student> more = studentList;
                students.addAll(more);
            }

            if (mAdapter == null) {
                // 第一次打开这个 Activity 时，RecyclerView 需要设置 Adapter
                recyclerView.setAdapter(mAdapter = new StudentAdapter(students, SearchStudentsActivity.this));
            } else {
                // 之后不需要设置 Adapter，但是需要刷新数据
                // 这里改为另外一个局部刷新的方法可以提高性能，待优化
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
