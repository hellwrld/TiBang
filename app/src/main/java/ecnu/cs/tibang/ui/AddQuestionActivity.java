package ecnu.cs.tibang.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import ecnu.cs.tibang.R;
import ecnu.cs.tibang.bean.Question;

public class AddQuestionActivity extends BaseActivity implements View.OnClickListener {

    Button btn_add_picture,btn_submit_question;
    EditText edit_title, edit_photo, edit_describe;
    private static final int CHOOSE_PHOTO = 2;
    private String url;
    private String bmobUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        init();
    }

    private void init(){
        initTopBarForLeft("提问");

        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_photo = (EditText) findViewById(R.id.edit_photo);
        edit_describe = (EditText) findViewById(R.id.edit_describe);

        btn_submit_question = (Button) findViewById(R.id.btn_submit_question);
        btn_add_picture = (Button) findViewById(R.id.btn_add_picture);

        btn_submit_question.setOnClickListener(this);
        btn_add_picture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_add_picture) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            } else {
                SelectPicture();
            }
        }
        else
            AddQuestion();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    SelectPicture();
                } else {
                    Toast.makeText(AddQuestionActivity.this,"权限申请失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void SelectPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    if (Build.VERSION.SDK_INT >= 19){
                        url=handleImageOnKitKat(data);
                    }else{
                        url=handleImageBeforeKitKat(data);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            insertPic(url);
                        }
                    }).start();
                }
                break;
        }
    }

    private void insertPic(String url){
        final BmobFile bmobFile = new BmobFile(new File(url));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    bmobUrl = bmobFile.getFileUrl();
                }
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }


    @TargetApi(19)
    private String handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        return  imagePath;
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void AddQuestion(){
        String title = edit_title.getText().toString();
        String photo = edit_photo.getText().toString();
        String describe = edit_describe.getText().toString();

        final ProgressDialog progress = new ProgressDialog(AddQuestionActivity.this);
        progress.setMessage("正在发布...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        final Question question = new Question();
        question.setTitle(title);
        question.setPhone(photo);
        question.setDescribe(describe);

        question.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    progress.dismiss();
                    Toast.makeText(AddQuestionActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    // 启动主页
                    Intent intent = new Intent(AddQuestionActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progress.dismiss();
                    Toast.makeText(AddQuestionActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
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
                    Bitmap bitmap = BitmapFactory.decodeFile(url);
                    ImageSpan imageSpan = new ImageSpan(bitmap);
                    String tempUrl = "<img src=\"" + bmobUrl + "\" />";
                    SpannableString spannableString = new SpannableString(tempUrl);
                    spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    int index = edit_describe.getSelectionStart();
                    Editable edit_text = edit_describe.getEditableText();
                    if (index < 0 || index >= edit_text.length()){
                        edit_text.append(spannableString);
                    } else {
                        edit_text.insert(index, spannableString);
                    }
                    break;
            }
        }
    };
}
