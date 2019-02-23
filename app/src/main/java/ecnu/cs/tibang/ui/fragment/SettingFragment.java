package ecnu.cs.tibang.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ecnu.cs.tibang.R;
import ecnu.cs.tibang.ui.FragmentBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends FragmentBase {


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView(){
        initTopBarForOnlyTitle("个人信息");
    }
}
