package com.derder.zhoubian.util;

import android.util.Log;
import com.derder.zhoubian.bean.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-5
 * Time: 下午5:30
 * 解析服务器返回的数据
 */
public class ParseServerData {

    public List<EsItem> esList(String url,Long pullOffset,Integer limit) throws IOException, JSONException {
        List<EsItem> list = null;
        String where = "";
        if(null != pullOffset){
            where+="/pull_offset/"+pullOffset;
        }
        String res = InteractServer.getDataFromServer(url+"/limit/"+limit+where);
        Log.d("esList","res==>"+res);
        if ( null != res && !"".equals( res ) ) {
            list = new ArrayList<EsItem>();
            JSONArray jsonArray = new JSONArray(res);
            for (int i=0; i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                List<Attach> attaches = new ArrayList<Attach>();
                JSONArray jsonArrayAttach = jsonObject.getJSONArray("attach_list");
                for (int j=0; j<jsonArrayAttach.length();j++) {
                    JSONObject jsonObjectAttach = jsonArrayAttach.getJSONObject(j);
                    Attach  attach = new Attach(jsonObjectAttach.getLong("id"),
                            jsonObjectAttach.getString("name"),
                            jsonObjectAttach.getString("path"),
                            jsonObjectAttach.getString("url"),
                            jsonObjectAttach.getInt("size"));
                    attaches.add(attach);
                }
                EsItem esItem = new EsItem(
                        jsonObject.getLong("id"),
                        jsonObject.getString("title"),
                        jsonObject.getString("description"),
                        attaches,
                        new Float(jsonObject.getDouble("now_price")).floatValue(),
                        new Float(jsonObject.getDouble("old_price")).floatValue(),
                        new GoodsType(jsonObject.getInt("goods_type"),jsonObject.getString("goods_type_name"),jsonObject.getString("goods_type_icon")),
                        new RecencyInfo(jsonObject.getInt("recency_info"),jsonObject.getString("recency_info_name")),
                        jsonObject.getInt("change_flag"),
                        new User(new JuzhuDi(1,"宝龙比亚迪")),
                        new Date(jsonObject.getLong("fb_time")*1000),
                        jsonObject.getInt("es_type")
                );
                list.add(esItem);
            }
        }
        return  list;
    }
}
