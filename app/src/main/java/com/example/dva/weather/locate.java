package com.example.dva.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MyLocationData;
import com.example.dva.TestFragment.BAIDUMAP;
import com.example.dva.app.Myapplication;
import com.example.dva.bean.City;

import java.util.List;

public class locate extends AppCompatActivity {
    private LocationClient locationClient;
    private MyLocationClickListener myLocationClickListener;
    private Button locateBtn;

    private String mLocCityCode;
//    private BAIDUMAP baidumap;
    private List<City> Citylist;
    private Myapplication myapplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.locate);
        locateBtn = (Button) findViewById(R.id.bdmap_cityName);

        locationClient = new LocationClient(this);
        myLocationClickListener = new MyLocationClickListener(locateBtn);
        locationClient.registerLocationListener(myLocationClickListener);
        initLocation();
        locationClient.start();

        final Intent intent = new Intent();
        locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myapplication = (Myapplication) getApplication();
                Citylist = myapplication.getCityList();
                for (City city:Citylist){
                    String locateCityName = locateBtn.getText().toString();
                    if (city.getCity().equals(locateCityName.substring(0,locateCityName.length()-1))){
                        mLocCityCode  = city.getNumber();
                        Log.d("locate",locateCityName.substring(0,locateCityName.length()-1));
                    }
                }

                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode",mLocCityCode);
                editor.commit();
                intent.putExtra("citycode",mLocCityCode);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
    void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
    }


    class MyLocationClickListener implements BDLocationListener{
        Button locateBtn;
        MyLocationClickListener(Button button){
            this.locateBtn = button;
        }
        String cityName;



        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            cityName = bdLocation.getCity();
            Log.d("Locate",cityName);
            locateBtn.setText(cityName);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
