package ecnu.cs.tibang.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bmob.v3.BmobUser;
import ecnu.cs.tibang.R;
import ecnu.cs.tibang.bean.User;
import ecnu.cs.tibang.ui.FragmentBase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecretFragment extends FragmentBase {

    TextView Test;
    public SecretFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_secret, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initView();
        Test = (TextView) findViewById(R.id.test);
        Test.append(BmobUser.getCurrentUser(User.class).getUsername());
    }
    private void initView(){
        initTopBarForOnlyTitle("秘笈");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "将会进入发帖界面", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
