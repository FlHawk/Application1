package com.swufe.application1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class RateExchange extends AppCompatActivity {
    TextView textView1,textView2;
    EditText editText;
    String name;
    double rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_exchange);

        textView1 = findViewById(R.id.rate_name);
        textView2 = findViewById(R.id.rate_out);
        editText = findViewById(R.id.rate_in);

        SharedPreferences sharedPreferences = getSharedPreferences("currency", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("currency","");
        rate = Double.parseDouble(sharedPreferences.getString("rate",""));

        //清空currency.xml中的数据
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        rate = 1 /(rate/100);
        textView1.setText(name);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double num = 0.0;

                try{
                    num = Double.parseDouble(s.toString());
                }catch (Exception e){
                    textView2.setText("请输入正确的金额");
                    return;
                }
                textView2.setText(num * rate + "");
            }

        });

    }
}

