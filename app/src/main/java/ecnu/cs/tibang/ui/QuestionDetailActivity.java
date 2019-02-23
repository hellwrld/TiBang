package ecnu.cs.tibang.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.style.ImageSpan;
import android.widget.TextView;
import java.util.ArrayList;

import ecnu.cs.tibang.R;
import ecnu.cs.tibang.util.LinkMovementMethodExt;
import ecnu.cs.tibang.util.MImageGetter;
import ecnu.cs.tibang.util.MessageSpan;


public class QuestionDetailActivity extends BaseActivity {

    TextView detail_title;
    TextView detail_photo;
    TextView detail_describe;

    private String describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        init();
    }

    private void init() {
        initTopBarForLeft("详情");

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String photo = intent.getStringExtra("photo");
        describe = intent.getStringExtra("describe");

        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_photo = (TextView) findViewById(R.id.detail_photo);
        detail_describe = (TextView) findViewById(R.id.detail_describe);

        detail_title.setText(title);
        detail_photo.setText(photo);

        loadPic();
    }

    private void loadPic(){
        detail_describe.setText(Html.fromHtml(describe, new MImageGetter(detail_describe, getApplicationContext()), null));
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 200) {
                    MessageSpan ms = (MessageSpan) msg.obj;
                    Object[] spans = (Object[]) ms.getObj();
                    final ArrayList<String> list = new ArrayList<>();
                    for (Object span : spans) {
                        if (span instanceof ImageSpan) {
                            list.add(((ImageSpan) span).getSource());
                            Intent intent = new Intent(getApplicationContext(), ImageGalleryActivity.class);
                            intent.putStringArrayListExtra("images", list);
                            startActivity(intent);
                        }
                    }
                }
            }
        };
        detail_describe.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
    }
}
