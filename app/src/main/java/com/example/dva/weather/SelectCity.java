package com.example.dva.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectCity extends AppCompatActivity implements View.OnClickListener {

    private ImageView Back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        Back_btn = (ImageView) findViewById(R.id.title_back);
        Back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.title_back:
            Intent intent =new Intent();
            intent.putExtra("citycode","101160101");
            setResult(RESULT_OK,intent);
            finish();
            break;
        default:
            break;
        }

    }
}
