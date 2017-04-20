package com.example.dva.weather;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.dva.app.Myapplication;
import com.example.dva.bean.City;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class SelectCity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private List<City> mCityList;
    private Myapplication myapplication;
    private ArrayList<String> mArrayList;
    private RelativeLayout relativeLayout;
    private ImageView Back_btn,Serach_btn;
    private SearchView searchView;
    ArrayAdapter<String> arrayAdapter;
    private  String updateCityCode ;
    private String mid_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);



        Back_btn = (ImageView) findViewById(R.id.title_back);
        Back_btn.setOnClickListener(this);
        Serach_btn = (ImageView) findViewById(R.id.search_button);
        Serach_btn.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.select_city_title);
        searchView = (SearchView) findViewById(R.id.search_view);



        myapplication = (Myapplication) getApplication();
        mCityList = myapplication.getCityList();
        mArrayList = new ArrayList<String>();
        for (int i=0;i<mCityList.size();i++){
            String city = mCityList.get(i).getCity();
            mArrayList.add(city);
        }
        listView = (ListView) findViewById(R.id.select_city_lv);
        arrayAdapter = new ArrayAdapter<String>(SelectCity.this,R.layout.support_simple_spinner_dropdown_item,mArrayList);
        listView.setAdapter(arrayAdapter);
        //list点击事件
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            String number;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                if (TextUtils.isEmpty(mid_city)) {
                    updateCityCode = mCityList.get(position).getNumber();
                    Log.d("updateCityCode", updateCityCode);
                    Intent intent = new Intent();
                    intent.putExtra("citycode", updateCityCode);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    mCityList = myapplication.getCityList();
                    for (int i = 0 ;i<mCityList.size();i++){
                        if (mCityList.get(i).getCity().equals(mid_city)){
                            number = mCityList.get(i).getNumber();
                        }
                    }
                    updateCityCode = number;
                    Log.d("updateCityCode",updateCityCode);
                    Intent intent =new Intent();
                    intent.putExtra("citycode",updateCityCode);
                    setResult(RESULT_OK,intent);
                    finish();

                }

            }
        };
        listView.setOnItemClickListener(itemClickListener);
        listView.setTextFilterEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    listView.setFilterText(s);
                    ArrayList<String> msearchlist = new ArrayList<String>();
                    for (int i = 0; i < mCityList.size(); i++) {
                        String city = mCityList.get(i).getCity();

                        if (city.equals(s)) {
                            msearchlist.add(city);
                            Log.d("saasdad", city);
                            mid_city =city;
                        }
                        arrayAdapter = new ArrayAdapter<String>(SelectCity.this, R.layout.support_simple_spinner_dropdown_item, msearchlist);
                        listView.setAdapter(arrayAdapter);
                        /*
                        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                            String number;
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                                mCityList = myapplication.getCityList();
                                for (int i = 0 ;i<mCityList.size();i++){
                                    if (mCityList.get(i).getCity().equals(mid_city)){
                                        number = mCityList.get(i).getNumber();
                                    }
                                }
                                updateCityCode = number;
                                Log.d("updateCityCode",updateCityCode);
                                Intent intent =new Intent();
                                intent.putExtra("citycode",updateCityCode);
                                setResult(RESULT_OK,intent);
                                finish();

                            }

                        };
//                        arrayAdapter.notifyDataSetChanged();*/
                    }
                }
                else {
                    listView.clearTextFilter();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.title_back:
            Intent intent =new Intent();
            intent.putExtra("citycode",updateCityCode);
            setResult(RESULT_OK,intent);
            finish();
            break;
            case R.id.search_button:
                relativeLayout.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);



        default:
            break;
        }

    }
}
