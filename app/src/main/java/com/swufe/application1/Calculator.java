package com.swufe.application1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    private static final String TAG ="Calculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void addpoints1(View view) {
        TextView tv =findViewById(R.id.textView6);//获取分数文本
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+Integer.parseInt(view.getTag().toString())));//将文本内容转为Int类型并相加
    }
    public void addpoints2(View view) {
        TextView tv =findViewById(R.id.textView7);//获取分数文本
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+Integer.parseInt(view.getTag().toString())));//将文本内容转为Int类型并相加
    }
    //add something
    public void clear(View view) {
        TextView tv1 = findViewById(R.id.textView6);//获取A队分数文本
        TextView tv2 = findViewById(R.id.textView7);//获取B队分数文本
        tv1.setText("0");//将A队分数文本设置为0
        tv2.setText("0");//将B队分数文本设置为0
    }
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String scorea = ((TextView)findViewById(R.id.textView6)).getText().toString();
        String scoreb = ((TextView)findViewById(R.id.textView7)).getText().toString();

        Log.i(TAG,"onSaveInstanceState:");
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }
    protected void OnRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");
        
        Log.i(TAG,"onRestoreInstanceState:");
        ((TextView)findViewById(R.id.textView6)).setText(scorea);
        ((TextView)findViewById(R.id.textView7)).setText(scoreb);
    }
}