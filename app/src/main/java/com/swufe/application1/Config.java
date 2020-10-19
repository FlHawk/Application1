package com.swufe.application1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Config extends AppCompatActivity {

    private static final String TAG ="Config" ;
    EditText dollarrate,eurorate,wonrate;
    Float newDollar,newWon,newEuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
//        Intent intent= getIntent();
//        Bundle bundle = intent.getExtras();
//
//        float dollar2 = bundle.getFloat("dollar_rate_key",0.1f);
//        float euro2 = bundle.getFloat("euro_rate_key",0.1f);
//        float won2 = bundle.getFloat("won_rate_key",0.1f);
//        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
//        float euro2 = intent.getFloatExtra("euro_rate_key",0.0f);
//        float won2 = intent.getFloatExtra("won_rate_key",0.0f);

//        Log.i(TAG, "onCreate: dollar2" + dollar2);
//        Log.i(TAG, "onCreate: euro2" + euro2);
//        Log.i(TAG, "onCreate: won2" + won2);

        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        float dollar2 = sharedPreferences.getFloat("dollar_rate_key",0.0f);
        float euro2 = sharedPreferences.getFloat("euro_rate_key",0.0f);
        float won2 = sharedPreferences.getFloat("won_rate_key",0.0f);

        dollarrate  =(EditText)findViewById(R.id.editTextTextPersonName3);
        eurorate =(EditText)findViewById(R.id.editTextTextPersonName4);
        wonrate  =(EditText)findViewById(R.id.editTextTextPersonName5);

        dollarrate.setText(Float.toString(dollar2));
        eurorate.setText(Float.toString(euro2));
        wonrate.setText(Float.toString(won2));
    }


    public void save(View view) {
//        Intent intent = getIntent();
//        Bundle bdl = new Bundle();

        dollarrate  =(EditText)findViewById(R.id.editTextTextPersonName3);
        eurorate =(EditText)findViewById(R.id.editTextTextPersonName4);
        wonrate  =(EditText)findViewById(R.id.editTextTextPersonName5);

        newDollar = Float.parseFloat(dollarrate.getText().toString());
        newEuro = Float.parseFloat(eurorate.getText().toString());
        newWon = Float.parseFloat(wonrate.getText().toString());

        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate_key",newDollar);
        editor.putFloat("euro_rate_key",newEuro);
        editor.putFloat("won_rate_key",newWon);
        editor.apply();

//        bdl.putFloat("dollar_rate_key",newDollar);
//        bdl.putFloat("euro_rate_key",newEuro);
//        bdl.putFloat("won_rate_key",newWon);

//        intent.putExtras(bdl);
//        setResult(2,intent);
//
       finish();

    }
}