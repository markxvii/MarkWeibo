package com.marc.markweibo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.marc.markweibo.Gson.WelComeImage;
import com.marc.markweibo.Util.HttpUtil;
import com.marc.markweibo.packge.LoginActivity;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WelComeActivity extends AppCompatActivity {
    private static final int toLogin=1;
    private static final int toMain=2;
    private static final long Delay=3000;
    private Oauth2AccessToken token;
    private ImageView welcomeImage;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case toLogin:
                    startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
                    finish();
                    break;
                case toMain:
                    startActivity(new Intent(WelComeActivity.this,MainActivity.class));
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_wel_come);
        initView();
        token= AccessTokenKeeper.readAccessToken(this);
        if (token.isSessionValid()){
            handler.sendEmptyMessageDelayed(toMain,Delay);
        }else {
            handler.sendEmptyMessageDelayed(toLogin,Delay);
        }
        HttpUtil.SendOkHttpRequest("http://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1920", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(WelComeActivity.this, "即将跳转到登录界面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                Gson gson=new Gson();
                final WelComeImage wi=gson.fromJson(responseText,WelComeImage.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WelComeActivity.this).load(wi.getCreatives().get(0).getUrl()).into(welcomeImage);
                    }
                });
            }
        });
    }

    private void initView() {
        welcomeImage = (ImageView) findViewById(R.id.welcome_image);
    }
}
