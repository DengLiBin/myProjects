package com.denglibin.secret.atys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.*;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.denglibin.secret.Config;
import com.denglibin.secret.R;
import com.denglibin.secret.domain.MsgComment;
import com.denglibin.secret.ld.MyContacts;
import com.denglibin.secret.net.GetComment;
import com.denglibin.secret.net.UploadContacts;
import com.denglibin.secret.utils.MD5Tool;

import java.util.List;

/**
 * Created by DengLibin on 2016/4/7 0007.
 */
public class AtyMessage extends BaseAty {
    private TextView tvMessage,tv_comment;
    private EditText etComment;
    private String phone_md5,msgId,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init(R.layout.activity_message, R.id.toolbar_message);
        tv_comment= (TextView) findViewById(R.id.tv_coment);
        Intent data = getIntent();
        phone_md5 = data.getStringExtra(Config.PHONE_MD5);
        msgId = data.getStringExtra(Config.MSG_ID);
        token = data.getStringExtra(Config.KEY_TOKEN);

        final ProgressDialog pd=ProgressDialog.show(this,getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));

        new GetComment(phone_md5, token, 1, 20,msgId,
                new GetComment.SuccessCallback() {
                    @Override
                    public void onSuccess(int page, int perpage, String msgId, List<MsgComment> list) {
                        tv_comment.setText("获取论数据成功,这里加载的是评论列表");
                        pd.dismiss();
                    }
                },
                new GetComment.FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        pd.dismiss();
                        if (errorCode==Config.TOKEN_INVALID) {

                            startActivity(new Intent(AtyMessage.this, AtyLogin.class));
                            finish();
                        }else{
                            Toast.makeText(AtyMessage.this, R.string.fail_to_pub_comment, Toast.LENGTH_LONG).show();
                        }
                    }
        });
    }

}
