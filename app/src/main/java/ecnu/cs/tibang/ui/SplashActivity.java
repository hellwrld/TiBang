package ecnu.cs.tibang.ui;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import ecnu.cs.tibang.R;
import ecnu.cs.tibang.bean.User;
import ecnu.cs.tibang.config.Config;

/**
 * 引导页
 *
 * @ClassName: SplashActivity
 * @Description: TODO
 */
public class SplashActivity extends BaseActivity {

    private static final int GO_HOME = 100;
    private static final int GO_LOGIN = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //BmobIM SDK初始化--只需要这一段代码即可完成初始化
        Bmob.initialize(this,Config.applicationId);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (BmobUser.getCurrentUser(User.class)!=null){
                mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        }else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    startAnimActivity(MainActivity.class);
                    finish();
                    break;
                case GO_LOGIN:
                    startAnimActivity(LoginActivity.class);
                    finish();
                    break;
            }
        }
    };

}

