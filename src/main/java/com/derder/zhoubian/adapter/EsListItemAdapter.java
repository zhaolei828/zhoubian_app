package com.derder.zhoubian.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.derder.zhoubian.R;
import com.derder.zhoubian.bean.EsItem;
import com.derder.zhoubian.bean.UrlConstant;
import com.derder.zhoubian.util.DateUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.DecimalFormat;
import java.util.List;

/**
 * author: zhaolei
 * date: 2014-07-28
 */
public class EsListItemAdapter extends BaseAdapter{
    List<EsItem> list;
    private static LayoutInflater inflater=null;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    DecimalFormat df = null;

    public EsListItemAdapter(List<EsItem> list,Context context,ImageLoader imageLoader) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_empty)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EsItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        df = new DecimalFormat("##,###,###,###,##0.00");
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.esitem, null);
            viewHolder.attachImageView = (ImageView)convertView.findViewById(R.id.es_attach_imgview);
            viewHolder.titleView = (TextView)convertView.findViewById(R.id.es_title_view);
            viewHolder.dateView = (TextView)convertView.findViewById(R.id.es_date_view);
            viewHolder.recencyView = (TextView)convertView.findViewById(R.id.es_recency_view);
            viewHolder.juzhuView = (TextView)convertView.findViewById(R.id.es_juzhu_view);
            viewHolder.oldPriceView = (TextView)convertView.findViewById(R.id.es_old_price_view);
            viewHolder.oldPriceView.getPaint().setAntiAlias(true);
            viewHolder.oldPriceView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            viewHolder.nowPriceView = (TextView)convertView.findViewById(R.id.es_now_price_view);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EsItem esItem = list.get(position);
        if(null != esItem && null != esItem.getAttachList() && esItem.getAttachList().size()>0){
            imageLoader.displayImage(UrlConstant.SERVER_ROOT + esItem.getAttachList().get(0).getUrl(), viewHolder.attachImageView,options);
        }else {
            viewHolder.attachImageView.setImageResource(R.drawable.ic_empty);
        }
        viewHolder.titleView.setText(esItem.getTitle());
        viewHolder.dateView.setText(DateUtils.fromToday(esItem.getFbDate()));
        viewHolder.recencyView.setText(esItem.getRecencyInfo().getRecency());
        viewHolder.juzhuView.setText(esItem.getFbUser().getJzd().getDiName());
        if(esItem.getOldPrice() > 0){
            viewHolder.oldPriceView.setText("￥"+df.format(esItem.getOldPrice()));
        } else {
            viewHolder.oldPriceView.setText("");
        }
        if(esItem.getNowPrice() > 0){
            viewHolder.nowPriceView.setText("￥"+df.format(esItem.getNowPrice()));
        }else {
            viewHolder.nowPriceView.setText("面议");
        }
        return convertView;
    }

    final static class ViewHolder {
        ImageView attachImageView;
        TextView titleView;
        TextView dateView;
        TextView recencyView;
        TextView juzhuView;
        TextView oldPriceView;
        TextView nowPriceView;
    }
}
