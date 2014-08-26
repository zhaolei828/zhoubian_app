package com.derder.zhoubian.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.derder.zhoubian.R;
import com.derder.zhoubian.bean.UrlConstant;
import com.derder.zhoubian.util.BitmapUtils;
import com.derder.zhoubian.util.InteractServer;
import com.derder.zhoubian.widget.AutoNewlineViewGroup;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-13
 * Time: 下午2:56
 */
public class EszrAddActivity extends Activity {
    private int RESULT_LOAD_IMAGE = 0;//打开图库
    private int RESULT_LOAD_CAMERA = 1;//打开照相机
    int tokenPhotoIconWidth = 0;
    int tokenPhotoIconHeight = 0;
    ImageButton tokenPhotoButton;
    LayoutInflater layoutInflater;
    AutoNewlineViewGroup autoNewlineViewGroup;
    ProgressDialog dialog;

    List<String> imgPathList;

    //转让信息表单
    EditText titleText;
    EditText contentText;
    EditText nowpriceText;
    EditText oldpriceText;
    Switch changeFlagSwitch;

    InteractServer interactServer;
    List<NameValuePair> textNameValuePairList;
    List<NameValuePair> fileNameValuePairList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eszradd);
        imgPathList = new ArrayList<String>();
        layoutInflater = LayoutInflater.from(this);
        interactServer = new InteractServer();
        textNameValuePairList = new ArrayList<NameValuePair>();
        fileNameValuePairList = new ArrayList<NameValuePair>();

        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍候...");
        dialog.setCancelable(false);

        initView();

        autoNewlineViewGroup = (AutoNewlineViewGroup)findViewById(R.id.add_image_layout);
        tokenPhotoButton = (ImageButton) findViewById(R.id.eszr_token_photo_btn);
        Runnable mRunnable = new Runnable() {
            public void run() {
                tokenPhotoIconWidth = tokenPhotoButton.getMeasuredWidth();
                tokenPhotoIconHeight = tokenPhotoButton.getMeasuredHeight();
                autoNewlineViewGroup.setmCellWidth(tokenPhotoIconWidth);
                autoNewlineViewGroup.setmCellHeight(tokenPhotoIconHeight);
            }
        };
        Handler mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 500);
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

        titleText = (EditText)findViewById(R.id.es_zr_title);
        contentText = (EditText)findViewById(R.id.es_zr_content);
        nowpriceText = (EditText)findViewById(R.id.es_zr_nowprice);
        oldpriceText = (EditText)findViewById(R.id.es_zr_oldprice);
        changeFlagSwitch = (Switch)findViewById(R.id.zr_wwjh_switch);
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

        Button submitButton = (Button)customView.findViewById(R.id.es_zr_submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNameValuePairList.clear();
                fileNameValuePairList.clear();

                NameValuePair esTypeNameValuePair = new BasicNameValuePair("es_type","1");
                textNameValuePairList.add(esTypeNameValuePair);
                //标题
                String title = titleText.getText().toString();
                NameValuePair titleNameValuePair = new BasicNameValuePair("title",title);
                textNameValuePairList.add(titleNameValuePair);

                String content = contentText.getText().toString();
                NameValuePair contentNameValuePair = new BasicNameValuePair("description",content);
                textNameValuePairList.add(contentNameValuePair);

                String nowPrice = nowpriceText.getText().toString();
                NameValuePair nowPriceNameValuePair = new BasicNameValuePair("now_price",nowPrice);
                textNameValuePairList.add(nowPriceNameValuePair);

                String oldPrice = oldpriceText.getText().toString();
                NameValuePair oldPriceNameValuePair = new BasicNameValuePair("old_price",oldPrice);
                textNameValuePairList.add(oldPriceNameValuePair);

                boolean checked = changeFlagSwitch.isChecked();
                String changeFlag = "";
                if(checked){
                    changeFlag = "1";
                }else {
                    changeFlag = "0";
                }
                NameValuePair changeFlagNameValuePair = new BasicNameValuePair("change_flag",changeFlag);
                textNameValuePairList.add(changeFlagNameValuePair);

                NameValuePair goodsTypeNameValuePair = new BasicNameValuePair("goods_type","1");
                textNameValuePairList.add(goodsTypeNameValuePair);
                NameValuePair recencyInfoNameValuePair = new BasicNameValuePair("recency_info","1");
                textNameValuePairList.add(recencyInfoNameValuePair);

                for( int i=0 ; i<imgPathList.size(); i++) {
                    NameValuePair filepathNameValuePair = new BasicNameValuePair("userfile"+i,imgPathList.get(i));
                    fileNameValuePairList.add(filepathNameValuePair);
                }
                new SubmitTask().execute(textNameValuePairList,fileNameValuePairList);
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

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imgPathList.add(picturePath);
            ImageView imageView = new ImageView(this);
            //利用Bitmap对象创建缩略图
            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(picturePath), tokenPhotoIconWidth, tokenPhotoIconHeight);
            try {
                int degree = BitmapUtils.getBitmapDegree(picturePath);
                bitmap = BitmapUtils.rotateBitmapByDegree(bitmap, degree);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //imageView 显示缩略图的ImageView
            imageView.setImageBitmap(bitmap);
            imageView.setTag(imgPathList.size());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("images", imgPathList.toArray(new String[imgPathList.size()]));
                    bundle.putInt("position", (Integer) view.getTag());
                    intent.putExtras(bundle);
                    intent.setClass(EszrAddActivity.this, ImageSwitcherActivity.class);
                    startActivity(intent);
                }
            });
            addView(autoNewlineViewGroup, imageView);
        }
    }

    private void  addView(ViewGroup viewGrop,View childView){
        int count = viewGrop.getChildCount();
        if(count > 0){
            final View lastChild = viewGrop.getChildAt( count-1 );
            viewGrop.removeViewAt( count-1 );
            viewGrop.addView(childView);
            viewGrop.addView(lastChild);
        }
    }

    private class SubmitTask extends AsyncTask<List<NameValuePair>, Void, Map<String,String>> {
        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Map<String,String> doInBackground(List<NameValuePair>... params) {
            Map<String,String> result = null;
            try {
                result = interactServer.postDataToServer(UrlConstant.ERSHOU_ADD_API_URL,params[0],params[1]);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("dddd","dddd",e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String,String> result) {
            if(null != result){
                String resultCode = result.get("code");
                if((HttpStatus.SC_OK+"").equals(resultCode)){
                    Intent intent = new Intent();
                    intent.setClass(EszrAddActivity.this,EsListActivity.class);
                    startActivity(intent);
                }
            }
            super.onPostExecute(result);
            dialog.dismiss();
        }
    }
}