package com.derder.zhoubian.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.derder.zhoubian.R;
import com.derder.zhoubian.util.BitmapUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-19
 * Time: 下午3:59
 */
public class ImageSwitcherActivity extends Activity {
    private ViewPager viewPager;
    private String[] imgPaths ;
    private ImageView[] mImageViews;
    private ProgressDialog dialog;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgswitcher);

        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍候...");
        dialog.setCancelable(false);

        dm = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Bundle bundle = this.getIntent().getExtras();
        int position = bundle.getInt("position");
        imgPaths = bundle.getStringArray("images");
        //将图片装载到数组中
        mImageViews = new ImageView[imgPaths.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
        }
        viewPager.setAdapter(new MyAdapter());
        viewPager.setCurrentItem(position - 1);
        new GetDataTask().execute();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
        private class GetDataTask extends AsyncTask<Integer, Void, Bitmap[]> {

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Bitmap[] doInBackground(Integer... params) {
            Bitmap[] bitmaps = new Bitmap[imgPaths.length];
            for(int i=0; i<imgPaths.length; i++){
                Bitmap bitmap = null;
                try {
                    byte[] bitmapByteArray = BitmapUtils.decodeBitmap(imgPaths[i],dm.widthPixels,dm.heightPixels);
                    bitmap = BitmapFactory.decodeByteArray(bitmapByteArray,0,bitmapByteArray.length);
                    int degree = BitmapUtils.getBitmapDegree(imgPaths[i]);
                    bitmap = BitmapUtils.rotateBitmapByDegree(bitmap, degree);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmaps[i] = bitmap;
            }
            return bitmaps;
        }

        @Override
        protected void onPostExecute(Bitmap[] result) {
            if(null != result && result.length>0){
                for (int i = 0; i<result.length; i++) {
                    Bitmap bitmap = result[i];
                    mImageViews[i].setImageBitmap(bitmap);
                }
            }
            super.onPostExecute(result);
            dialog.dismiss();
        }
    }
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews[position], 0);
            return mImageViews[position];
        }
    }
}
