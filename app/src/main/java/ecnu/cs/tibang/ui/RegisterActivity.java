package ecnu.cs.tibang.ui;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.SaveListener;
import ecnu.cs.tibang.R;
import ecnu.cs.tibang.bean.User;


public class RegisterActivity extends BaseActivity {

    Button btn_register;
    EditText et_username, et_password, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initTopBarForLeft("注册");

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                register();
            }
        });
    }

    private void register(){
        String name = et_username.getText().toString();
        String password = et_password.getText().toString();
        String pwd_again = et_email.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this,R.string.toast_error_username_null,Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this,R.string.toast_error_password_null,Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd_again.equals(password)) {
            Toast.makeText(RegisterActivity.this,R.string.toast_error_confirm_password,Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
        progress.setMessage("正在注册...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        final User bu = new User();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.setMoney(0);
        bu.signUp(new SaveListener<User>() {

            @Override
            public void done(User User, BmobException e) {
                if(e==null){
                    progress.dismiss();
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    // 启动主页
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progress.dismiss();
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
