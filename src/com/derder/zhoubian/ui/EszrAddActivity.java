package com.derder.zhoubian.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.derder.zhoubian.R;
import com.derder.zhoubian.bean.UrlConstant;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-13
 * Time: 下午2:56
 */
public class EszrAddActivity extends Activity {
    LayoutInflater layoutInflater;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eszradd);
        layoutInflater = LayoutInflater.from(this);
        initView();

        ImageButton tokenPhotoButton = (ImageButton) findViewById(R.id.eszr_token_photo_btn);
        tokenPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] choices={"打开本地图库","打开照相机"};
                AlertDialog dialog = new AlertDialog.Builder(EszrAddActivity.this)
                        .setIcon(android.R.drawable.btn_star)
                        .setTitle("您要如何添加图片？")
                        .setItems(
                                choices,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                Toast.makeText(EszrAddActivity.this, "打开本地图库", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                Toast.makeText(EszrAddActivity.this, "打开照相机", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                })
                        .create();
                dialog.show();
            }
        });
    }

    private void initView() {
        initActionBar();
    }

    private void initActionBar(){
        final ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.myactionbar1);
        View customView = mActionBar.getCustomView();

        TextView actionbarTitle = (TextView) customView.findViewById(R.id.actionbar1_title);
        actionbarTitle.setText(getText(R.string.es_zr_actionbar_title));

        RelativeLayout relativeLayout = (RelativeLayout)customView.findViewById(R.id.actionbar1_back_up_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EszrAddActivity.this.finish();
            }
        });
    }

    DialogInterface.OnClickListener onselect = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}