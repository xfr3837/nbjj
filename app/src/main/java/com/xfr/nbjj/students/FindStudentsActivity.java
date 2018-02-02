package com.xfr.nbjj.students;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.xfr.nbjj.Main2Activity;
import com.xfr.nbjj.R;
import com.xfr.nbjj.Searcher;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


/**
 * 妄图使用网络数据来加载 recyclerView ，失败
 */
public class FindStudentsActivity extends AppCompatActivity {

    public static final int UPDATE_TEXT = 1;
    private List<Student> downloadStudentList = new ArrayList<>();
    private List<Student> mStudentList = new ArrayList<>();


    private Handler handler = new Handler() {

        public void handlerMessage(Message message) {
            switch (message.what) {
                case UPDATE_TEXT:
                    //更新 UI
                    mStudentList = downloadStudentList;
                    //adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_students);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.test_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final StudentAdapter adapter = new StudentAdapter(mStudentList);
        recyclerView.setAdapter(adapter);
/**
        //new DownloadTask(this).execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(),
                                    new SharedPrefsCookiePersistor(FindStudentsActivity.this));
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    Searcher searcher = new Searcher(client, "", "",
                            "", "", "",
                            "", "");
                    downloadStudentList = searcher.getOnePageStudentsInfo();

                } catch (Exception e) {
                    Toast.makeText(FindStudentsActivity.this, "error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });*/
    }

}

/**
class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

    private List<Student> studentList = new ArrayList<>();
    private Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.test_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        final StudentAdapter adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(),
                            new SharedPrefsCookiePersistor(context));
            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();

            Searcher searcher = new Searcher(client, "", "",
                    "", "", "",
                    "", "");
            studentList = searcher.getOnePageStudentsInfo();

        } catch (Exception e) {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            // 更新 ui

        }
        else {
            // 失败

        }
    }
}*/
