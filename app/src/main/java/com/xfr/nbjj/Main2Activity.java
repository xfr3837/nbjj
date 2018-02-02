package com.xfr.nbjj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;


import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.xfr.nbjj.students.Student;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * 用来做一些测试
 *
 */
public class Main2Activity extends AppCompatActivity {

    private List<Student> studentList = new ArrayList<>();
    private Button button = (Button) findViewById(R.id.button2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initStudents();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //final StudentAdapter adapter = new StudentAdapter(studentList);
        //recyclerView.setAdapter(adapter);
    }



    private void initStudents() {

        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 据说这样能自动处理 cookies ，未验证
                    ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(),
                                    new SharedPrefsCookiePersistor(Main2Activity.this));
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();



            } catch (Exception e) {
                    Toast.makeText(Main2Activity.this, "error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                }
        }).start();
    }
}
