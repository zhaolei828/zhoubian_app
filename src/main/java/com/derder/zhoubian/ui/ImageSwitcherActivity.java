package com.derder.zhoubian.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.derder.zhoubian.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgswitcher);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Bundle bundle = this.getIntent().getExtras();
        imgPaths = bundle.getStringArray("images");
        //将图片装载到数组中
        mImageViews = new ImageView[imgPaths.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imgPaths[i]), dm.widthPixels, dm.heightPixels);
            imageView.setImageBitmap(bitmap);
            mImageViews[i] = imageView;
        }
        viewPager.setAdapter(new MyAdapter());
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
        viewPager.setCurrentItem((mImageViews.length) * 100);
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
            container.removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }
    }
    private void setImageBitmap(int selectItems){

    }
}
