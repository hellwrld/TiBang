package ecnu.cs.tibang.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ecnu.cs.tibang.R;
import ecnu.cs.tibang.ui.fragment.MessageFragment;
import ecnu.cs.tibang.ui.fragment.QuestionFragment;
import ecnu.cs.tibang.ui.fragment.SecretFragment;
import ecnu.cs.tibang.ui.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {
    private Button[] mTabs;
    private SecretFragment secretFragment;
    private QuestionFragment questionFragment;
    private MessageFragment messageFragment;
    private SettingFragment settingFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
    }

    private void initView(){
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_secret);
        mTabs[1] = (Button) findViewById(R.id.btn_question);
        mTabs[2] = (Button) findViewById(R.id.btn_message);
        mTabs[3] = (Button) findViewById(R.id.btn_set);
        //把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }

    private void initTab(){
        secretFragment = new SecretFragment();
        questionFragment = new QuestionFragment();
        messageFragment = new MessageFragment();
        settingFragment = new SettingFragment();
        fragments = new Fragment[] {secretFragment, questionFragment, messageFragment, settingFragment };
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, secretFragment).show(secretFragment).commit();
    }

    /**
     * button点击事件
     * @param view
     */
    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.btn_secret:
                index = 0;
                break;
            case R.id.btn_question:
                index = 1;
                break;
            case R.id.btn_message:
                index = 2;
                break;
            case R.id.btn_set:
                index = 3;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        //把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    private static long firstTime;
    /**
     * 连续按两次返回键就退出
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
        }
        firstTime = System.currentTimeMillis();
    }
}
