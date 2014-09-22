package com.derder.zhoubian.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.derder.zhoubian.R;
import com.derder.zhoubian.adapter.EsListItemAdapter;
import com.derder.zhoubian.bean.EsItem;
import com.derder.zhoubian.bean.UrlConstant;
import com.derder.zhoubian.util.InteractServer;
import com.derder.zhoubian.util.ParseServerData;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * author: zhaolei
 * date: 2014-07-25
 */
public class EsListActivity extends Activity {
    private String BUTTON_STATUS_NO_PRESS = "0";
    private String BUTTON_STATUS_PRESSED = "1";
    private int PULL_UP = 0;
    private int PULL_DOWN = 1;

    EsListItemAdapter adapter = null;
    LinkedList<EsItem> eslist;
    ParseServerData parseServerData = null;
    ImageLoader imageLoader = null;
    PullToRefreshListView pullToRefreshListView;

    LayoutInflater inflater;
    PopupWindow fullScreenPopupWindow;

    Button eszrAddBtn;
    Button esqgAddBtn;
    RelativeLayout esZrqgLayout;

    View ppwinAddLayoutView;
    PopupWindow addBtnPopupWindow;
    LinearLayout addMoreLayout;
    ImageView addMoreImageView;
    ImageView closeAddMoreImageView;

    //private ProgressDialog dialog;

    int estype = 1;

    long upestId = 0;
    long downestId = 0;

    int upDown = PULL_UP;

    ProgressBar progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eslist);
        inflater  = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        initView();
        //dialog = new ProgressDialog(this);
        //dialog.setMessage("请稍候...");
        //dialog.setCancelable(false);

        //获取数据
        InteractServer interactServer = new InteractServer();
        parseServerData = new ParseServerData(interactServer);
        eslist = new LinkedList<EsItem>();
        new GetDataTask().execute(UrlConstant.ERSHOU_ZHUANRANG_API_URL,null);

        //装载数据
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(EsListActivity.this));
        adapter = new EsListItemAdapter(eslist,this,imageLoader);


        //显示数据
        pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.list_layout);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                upDown = PULL_DOWN;
                if (null != eslist && eslist.size() > 0) {
                    upestId = eslist.get(0).getId();
                }
                if(estype == 1){
                    new GetDataTask().execute(UrlConstant.ERSHOU_ZHUANRANG_API_URL,upestId);
                }else {
                    new GetDataTask().execute(UrlConstant.ERSHOU_QIUGOU_API_URL,upestId);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                upDown = PULL_UP;
                if (null != eslist && eslist.size() > 0) {
                    downestId = eslist.get(eslist.size()-1).getId();
                }

                if(estype == 1){
                    new GetDataTask().execute(UrlConstant.ERSHOU_ZHUANRANG_API_URL,-downestId);
                }else {
                    new GetDataTask().execute(UrlConstant.ERSHOU_QIUGOU_API_URL,-downestId);
                }
            }
        });
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EsListActivity.this, "点击条目", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        initActionBar();
        initPopwin();
    }

    private void initActionBar(){
        final ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.myactionbar2);
        View customView = mActionBar.getCustomView();
        final Button eszrBtn = (Button)customView.findViewById(R.id.es_zr_btn);
        final Button esqgBtn = (Button)customView.findViewById(R.id.es_qg_btn);
        eszrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eszrBtn.getTag().equals(BUTTON_STATUS_NO_PRESS)){
                    estype = 1;

                    eszrBtn.setBackgroundColor(getResources().getColor(R.color.btn_pres_blue));
                    eszrBtn.setTag(BUTTON_STATUS_PRESSED);
                    esqgBtn.setBackgroundColor(getResources().getColor(R.color.btn_no_pres_blue));
                    esqgBtn.setTag(BUTTON_STATUS_NO_PRESS);
                    if(null != eslist) {
                        eslist.clear();
                    }
                    adapter.notifyDataSetChanged();
                    upDown = PULL_UP;
                    new GetDataTask().execute(UrlConstant.ERSHOU_ZHUANRANG_API_URL,null);
                }
            }
        });
        esqgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(esqgBtn.getTag().equals(BUTTON_STATUS_NO_PRESS)){
                    estype = 2;

                    esqgBtn.setBackgroundColor(getResources().getColor(R.color.btn_pres_blue));
                    esqgBtn.setTag(BUTTON_STATUS_PRESSED);
                    eszrBtn.setBackgroundColor(getResources().getColor(R.color.btn_no_pres_blue));
                    eszrBtn.setTag(BUTTON_STATUS_NO_PRESS);
                    if(null != eslist) {
                        eslist.clear();
                    }
                    adapter.notifyDataSetChanged();
                    upDown = PULL_UP;
                    new GetDataTask().execute(UrlConstant.ERSHOU_QIUGOU_API_URL,null);
                }
            }
        });

        RelativeLayout relativeLayout = (RelativeLayout)customView.findViewById(R.id.actionbar_back_up_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EsListActivity.this.finish();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.widget110);
    }

    private void initPopwin(){
        //初始化  点击点击点击点击  加号 按钮后的popwindow
        View fullScreenPpwinLayout = inflater.inflate(R.layout.ppwin_add_ershou,null);
        fullScreenPpwinLayout.setFocusable(true);
        fullScreenPpwinLayout.setFocusableInTouchMode(true);
        fullScreenPopupWindow = new PopupWindow(fullScreenPpwinLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);
        fullScreenPpwinLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Animation animation1 = AnimationUtils.loadAnimation(EsListActivity.this, R.anim.btn1_close);
                closeAddMoreImageView.startAnimation(animation1);

                Animation animation0 = AnimationUtils.loadAnimation(EsListActivity.this, R.anim.btn0_close);
                animation0.setFillAfter(true);
                animation0.setStartOffset(100);
                animation0.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fullScreenPopupWindow.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                esZrqgLayout.startAnimation(animation0);
                return true;
            }
        });
        fullScreenPpwinLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Animation animation1 = AnimationUtils.loadAnimation(EsListActivity.this, R.anim.btn1_close);
                    closeAddMoreImageView.startAnimation(animation1);

                    Animation animation0 = AnimationUtils.loadAnimation(EsListActivity.this, R.anim.btn0_close);
                    animation0.setFillAfter(true);
                    animation0.setStartOffset(100);
                    animation0.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            fullScreenPopupWindow.dismiss();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    esZrqgLayout.startAnimation(animation0);
                    return true;
                }
                return false;
            }
        });
        eszrAddBtn = (Button)fullScreenPpwinLayout.findViewById(R.id.add_btn_eszr);
        eszrAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EsListActivity.this, EszrAddActivity.class);
                startActivity(intent);
                finish();
            }
        });
        esqgAddBtn = (Button)fullScreenPpwinLayout.findViewById(R.id.add_btn_esqg);
        esZrqgLayout = (RelativeLayout)fullScreenPpwinLayout.findViewById(R.id.add_btn_zrqg);
        closeAddMoreImageView = (ImageView)fullScreenPpwinLayout.findViewById(R.id.close_add_more_es_image);
        LinearLayout closeAddMoreLayout = (LinearLayout)fullScreenPpwinLayout.findViewById(R.id.close_add_more_es);
        closeAddMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(EsListActivity.this,R.anim.btn1_close);
                closeAddMoreImageView.startAnimation(animation1);

                Animation animation0 = AnimationUtils.loadAnimation(EsListActivity.this,R.anim.btn0_close);
                animation0.setFillAfter(true);
                animation0.setStartOffset(100);
                animation0.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fullScreenPopupWindow.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                esZrqgLayout.startAnimation(animation0);
            }
        });



        //左侧悬浮的“+”号按钮
        ppwinAddLayoutView = inflater.inflate(R.layout.ppwin_add_btn,null);
        addMoreLayout = (LinearLayout)ppwinAddLayoutView.findViewById(R.id.ppwin_add_more_btn);
        addMoreImageView = (ImageView)addMoreLayout.findViewById(R.id.ppwin_add_more_image);
        addBtnPopupWindow = new PopupWindow(ppwinAddLayoutView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Runnable mRunnable = new Runnable() {
            public void run() {
                addBtnPopupWindow.showAtLocation(findViewById(R.id.es_main),Gravity.CENTER|Gravity.LEFT,0,0);
            }
        };
        Handler mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 500);
        addMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(EsListActivity.this,R.anim.btn1_show);
                addMoreImageView.startAnimation(animation1);

                int addMoreHeight = addMoreLayout.getHeight();
                eszrAddBtn.setHeight(addMoreHeight);
                esqgAddBtn.setHeight(addMoreHeight);
                esZrqgLayout.setMinimumHeight(addMoreHeight);
                fullScreenPopupWindow.showAtLocation(findViewById(R.id.es_main),Gravity.TOP|Gravity.LEFT,0,0);

                Animation animation0 = AnimationUtils.loadAnimation(EsListActivity.this,R.anim.btn0_show);
                animation0.setFillAfter(true);
                animation0.setStartOffset(100);
                esZrqgLayout.startAnimation(animation0);
            }
        });
    }

    private class GetDataTask extends AsyncTask<Object, Void, List<EsItem>> {

        @Override
        protected void onPreExecute() {
            //dialog.show();
            progressBar.setVisibility(ProgressBar. VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected List<EsItem> doInBackground(Object... params) {
            List<EsItem> temeslist = null;
            try {
                temeslist = parseServerData.esList(params[0].toString(),(Long)params[1],5);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return temeslist;
        }

        @Override
        protected void onPostExecute(List<EsItem> result) {
            if(null != result && result.size() > 0){
                for ( EsItem esItem : result) {
                    if(upDown == PULL_UP){
                        eslist.addLast(esItem);
                    }
                    if(upDown == PULL_DOWN){
                        eslist.addFirst(esItem);
                    }

                }
            }
            adapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
            progressBar.setVisibility(ProgressBar. INVISIBLE );
            //dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if(null != addBtnPopupWindow){
            addBtnPopupWindow.dismiss();
        }
        super.onDestroy();
    }
}