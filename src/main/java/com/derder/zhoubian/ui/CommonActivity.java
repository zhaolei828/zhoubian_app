package com.derder.zhoubian.ui;

import android.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * author: zhaolei
 * date: 2014-07-23
 */
public class CommonActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView() {
        final ActionBar mActionBar = getActionBar();
        //mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home :
                this.finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}