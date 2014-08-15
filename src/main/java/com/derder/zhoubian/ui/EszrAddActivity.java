package com.derder.zhoubian.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.derder.zhoubian.R;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-13
 * Time: 下午2:56
 */
public class EszrAddActivity extends Activity {
    private int RESULT_LOAD_IMAGE = 0;//打开图库
    private int RESULT_LOAD_CAMERA = 1;//打开照相机

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
                                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                intent.setType("image/*");
//                                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                                startActivityForResult(Intent.createChooser(intent, "Pick any photo"), RESULT_LOAD_IMAGE);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            ImageView imageView = (ImageView) findViewById(R.id.imgView);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }
    }
}