package com.cy.cylnxuexijia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.tools.AIDLGetUserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        new AIDLGetUserInfo(SplashActivity.this); //AIDL获取用户信息
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
//        Intent intent=new Intent(SplashActivity.this,CyLnXueXiJiaMainActivity.class);
        Intent intent=new Intent(SplashActivity.this,PlayActivity.class);
        startActivity(intent);
    }
}
