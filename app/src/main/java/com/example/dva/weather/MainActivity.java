package com.example.dva.weather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by D.va on 2017/4/17.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.weather_info);
    }
}
