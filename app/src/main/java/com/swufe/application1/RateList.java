package com.swufe.application1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateList extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    private static final String TAG = "RateList";
    Handler handler;
    String date;
    String rates;
    SharedPreferences sp;
    ArrayList listItems;
    ListView listView;
    int position;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

        listView = findViewById(R.id.mylist);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setEmptyView(findViewById(R.id.nodata));

        sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        date = sp.getString("date", "");
        Date now = new Date();                                        //读取当前时间，方便下一步进行比较
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//规范时间格式，进行比较
        String nowDate = sdf.format(now);
        ListView listView = (ListView) findViewById(R.id.mylist);

        if (date.equals(nowDate)) {                           //进行判断，如果时间相同，就从文件中读取数据，如果时间不同，在从网络上读取数据的同时，也会将数据与时间写入文件中
            rates = sp.getString("rates", "");
            String[] rateArray = rates.split(",");   //对字符串进行分割
//            List<HashMap<String, String>> list=new ArrayList<>();

            listItems = new ArrayList<HashMap<String, String>>();
            for (String i :rateArray) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle",i.split(":")[0]);
                map.put("ItemDetail",i.split(":")[1]);
                listItems.add(map);
            }
            adapter = new MyAdapter(this,
                    android.R.layout.simple_list_item_1, listItems);
            listView.setAdapter(adapter);
        } else {
            getData();

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("date", nowDate);
            editor.apply();
        }

    }

    private void getData() {         //从网络上读取数据
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 3) {
                    ListView listView = (ListView) findViewById(R.id.mylist);
                    String rates = "";
                    String myrate = (String) msg.obj;
                    String[] rateArray = myrate.split(",");
                    listItems = new ArrayList<HashMap<String, String>>();
                    for (String i :rateArray) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        rates +=i;
                        map.put("ItemTitle",i.split(":")[0]);
                        map.put("ItemDetail",i.split(":")[1]);
                        listItems.add(map);
                    }
                    MyAdapter adapter = new MyAdapter(RateList.this,
                            android.R.layout.simple_list_item_1, listItems);
                    listView.setAdapter(adapter);

                    sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("rates", rates);
                    editor.apply();

                }
                super.handleMessage(msg);
            }
        };
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Object itemAtPosition = listView.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);

        sp = getSharedPreferences("currency",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currency",title2);
        editor.putString("rate",detail2);
        editor.commit();
        Toast.makeText(this,"update finish",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setClass(this,RateExchange.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
        position = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick: 对话框事件处理");
                        //删除数据项
                        listItems.remove(position);
                        //更新适配器
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("否", null);
        builder.create().show();

        return true;
    }


    public void run() {
        Message msg = handler.obtainMessage(3);

        String url = "http://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run:" + doc.title());
        Elements tables = doc.getElementsByTag("table");
        Element table = tables.get(0);
        List<String> list = new ArrayList<String>();
        Elements tds = table.getElementsByTag("td");
        for (int i = 0; i < tds.size(); i += 6) {
            Element td1 = tds.get(i);
            Element td2 = tds.get(i + 5);

            String str1 = td1.text();
            String val = td2.text();

            Log.i(TAG, "run:" + str1 + val + ",");
            list.add(str1 + ":" + val+",");

            float v = 100f / Float.parseFloat(val);
        }
        msg.obj = list;
        handler.sendMessage(msg);
    }
}