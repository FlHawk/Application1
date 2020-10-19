package com.swufe.application1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void jumptemperature(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Temperature.class);
        startActivity(intent);
    }
    public void jumpcalculator(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Calculator.class);
        startActivity(intent);
    }


    public void jumpexchange(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,exchange.class);
        startActivity(intent);
    }
}