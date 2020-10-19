package com.swufe.application1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class exchange extends AppCompatActivity implements Runnable{

    private static final String TAG = "exchange";
    TextView inp;
    TextView tv;
    float dollarRate,wonRate,euroRate;
    Handler handler;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==5001){
                    String str =(String) msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg="+str);
                }

                super.handleMessage(msg);
            }
        };
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask(){
//            @Override
//            public void run(){
//
//
//            }
//        },3000);






//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg){
//                if(msg.what==7){
//                    List<String> list2 = (List<String>)msg.obj;
//                    ListAdapter adapter = new ArrayAdapter<String>(RateList.this,
//                            android.R.layout.simple_list_item_1,list2);
//                    setListAdapter(adapter);
//                }
//                super.handleMessage(msg);
//            }
//        };


    }


    public void exchange(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_rate_key",dollarRate);
        euroRate = sharedPreferences.getFloat("euro_rate_key",euroRate);
        wonRate = sharedPreferences.getFloat("won_rate_key",wonRate);

        inp = (EditText)findViewById(R.id.editTextTextPersonName2);
        tv = findViewById(R.id.textView8);
        if(inp.getText().toString()==null){
            Toast.makeText(this, "please input money", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId()==R.id.button11){
            tv.setText(String.valueOf(Float.parseFloat(inp.getText().toString())*dollarRate)+"＄");
        }
        else if(view.getId()==R.id.button12){
            tv.setText(String.valueOf(Float.parseFloat(inp.getText().toString())*euroRate)+"€");
        }
        else if(view.getId()==R.id.button13){
            tv.setText(String.valueOf(Float.parseFloat(inp.getText().toString())*wonRate)+"韩元");
        }
    }

    public void open(View view) {
        Intent config = new Intent(this,Config.class);
        startActivity(config);
//        Bundle bdl = new Bundle();
//        bdl.putFloat("dollar_rate_key",dollarRate);
//        bdl.putFloat("euro_rate_key",euroRate);
//        bdl.putFloat("won_rate_key",wonRate);
//
//        config.putExtras(bdl);

//        config.putExtra("dollar_rate_key",dollarRate);
//        config.putExtra("euro_rate_key",euroRate);
//        config.putExtra("won_rate_key",wonRate);
//
//        Log.i(TAG,"openOne:dollarRate="+dollarRate);
//        Log.i(TAG,"openOne:euroRate="+euroRate);
//        Log.i(TAG,"openOne:wonRate="+wonRate);


//        startActivityForResult(config,1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==1 && resultCode==2){
//            Bundle bundle = data.getExtras();
//            dollarRate = bundle.getFloat("dollar_rate_key",0.1f);
//            euroRate = bundle.getFloat("euro_rate_key",0.1f);
//            wonRate = bundle.getFloat("won_rate_key",0.1f);
//            Log.i(TAG,"openOne:dollarRate="+dollarRate);
//            Log.i(TAG,"openOne:euroRate="+euroRate);
//            Log.i(TAG,"openOne:wonRate="+wonRate);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private String inputSteam2String(InputStream inputStream) throws IOException{
//        final int bufferSize =1024;
//        final char[] buffer = new char[bufferSize];
//        final StringBuilder out = new StringBuilder();
//        Reader in = new InputStreamReader(inputStream,"gb2312");
//        while (true){
//            int rsz = in.read(buffer,0,buffer.length);
//            if(rsz<0)
//                break;
//            out.append(buffer,0,rsz);
//        }
//        return out.toString();
//    }

        public void run(){
        Log.i(TAG,"run:run()......");
        Message msg = handler.obtainMessage(5001);
        msg.obj = "hello from run:";
        handler.sendMessage(msg);

        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String url = "http://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"run:"+doc.title());
        Elements tables = doc.getElementsByTag("table");
        Element table = tables.get(0);

        Elements tds = table.getElementsByTag("td");

        for(int i=0;i<tds.size();i+=6){
            Element td1 = tds.get(i);
            Element td2 = tds.get(i+5);

            String str1 = td1.text();
            String val = td2.text();



            Log.i(TAG,"run:"+str1+"==>"+val);
            float v = 100f / Float.parseFloat(val);
            switch (str1)
            {
                case "美元":
                    editor.putFloat("dollar_rate_key",v);
                    break;
                case "欧元":
                    editor.putFloat("euro_rate_key",v);
                    break;
                case "韩元":
                    editor.putFloat("won_rate_key",v);
            }
        }

        editor.apply();


//        URL url = null;
//        try{
//            url = new URL("http://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http =(HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputSteam2String(in);
//            Log.i(TAG,"run:html="+html);
//
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }
}