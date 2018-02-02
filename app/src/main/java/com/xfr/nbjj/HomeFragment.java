package com.xfr.nbjj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xfr.nbjj.tips.Tips;

/**
 * Created by xfr on 2018/1/25.
 */

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 首页的四个 Button
        Button findTeachersButton = (Button) view.findViewById(R.id.find_teachers);
        Button findStudentsButton = (Button) view.findViewById(R.id.find_students);
        Button callButton = (Button) view.findViewById(R.id.call);
        Button tipsButton = (Button) view.findViewById(R.id.tips);

        findTeachersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        findStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.xfr.nbjj.students.FindStudentsActivity.class);
                startActivity(intent);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:15088483270"));
                startActivity(intent);
            }
        });

        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Tips.class);
                startActivity(intent);
            }
        });

    }
}
