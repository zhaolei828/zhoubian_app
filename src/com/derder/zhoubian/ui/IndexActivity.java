package com.derder.zhoubian.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.derder.zhoubian.R;
import com.derder.zhoubian.widget.PullScrollView;

/**
 * author: zhaolei
 * date: 2014-07-23
 */
public class IndexActivity extends Activity implements PullScrollView.OnTurnListener {
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        initView();

        Button mCenterBtn = (Button)findViewById(R.id.my_center_btn);
        mCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IndexActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ershouLout = (LinearLayout)findViewById(R.id.ershou_icon);
        ershouLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IndexActivity.this, EsListActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        final ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.myactionbar0);
        View customView = mActionBar.getCustomView();
        TextView tv = (TextView)customView.findViewById(R.id.mactionbar0_title_tv);
        tv.setText("坪山比亚迪");

        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    @Override
    public void onTurn() {}
}