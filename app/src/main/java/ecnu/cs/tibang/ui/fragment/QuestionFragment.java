package ecnu.cs.tibang.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ecnu.cs.tibang.R;
import ecnu.cs.tibang.adapter.QuestionAdapter;
import ecnu.cs.tibang.bean.Question;
import ecnu.cs.tibang.ui.AddQuestionActivity;
import ecnu.cs.tibang.ui.FragmentBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends FragmentBase {

    private List<Question> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView(){
        initTopBarForOnlyTitle("悬赏榜");

        Query();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.question_srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Query();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }, 1000);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddQuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Query(){
        list.clear();
        BmobQuery<Question> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {
                if (e == null) {
                    for (Question question : object) {
                        list.add(question);
                    }
                    mHandler.sendEmptyMessage(1);
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_question);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    QuestionAdapter adapter = new QuestionAdapter(list);
                    recyclerView.setAdapter(adapter);
            }
        }
    };
}
