package com.example.dva.weather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dva.util.NetUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by D.va on 2017/4/17.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView UpdateBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.weather_info);
        UpdateBtn = (ImageView) findViewById(R.id.city_update_button);
        UpdateBtn.setOnClickListener(this);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.i("Weather","网络已连接");
            Toast.makeText(MainActivity.this,"网络已连接",Toast.LENGTH_SHORT).show();
        }else
        {
            Log.i("Weather","没网了！！！");
            Toast.makeText(MainActivity.this,"没。。。网。。。了",Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.city_update_button){
            SharedPreferences sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
            String citycode = sharedPreferences.getString("main_city_code","101010100");
            Log.d("Weather",citycode);


        if (NetUtil.getNetworkState(this)!=NetUtil.NETWORN_NONE){
            Log.d("Weather","网络已连接");
            queryWeatherCode(citycode);
        }else {
            Log.d("Weather","没网。。。");
            Toast.makeText(MainActivity.this,"没网~(≧▽≦)/~啦啦啦",Toast.LENGTH_SHORT);

        }

        }

    }
    /**
     * @param  citycode
     */
    private void queryWeatherCode(String citycode){
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey="+citycode;
        Log.d("Weather",address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection =null;
                try {
                    URL url =new URL(address);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str=reader.readLine())!=null){
                        response.append(str);
                        Log.d("Weather",str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (httpURLConnection!=null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();

    }
    //XML解析
    private void parseXML(String XMLdata){
        try{
            //解析工厂
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            //解析器
            XmlPullParser xmlPullPraser = fac.newPullParser();
            //xml数据
            xmlPullPraser.setInput(new StringReader(XMLdata));
            //事件类型
            int eventType = xmlPullPraser.getEventType();
            Log.d("Weather","ParseXML");
            while (eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT://文档头
                        break;
                    case XmlPullParser.START_TAG://标签头
                        if (xmlPullPraser.getName().equals("city")){
                            eventType = xmlPullPraser.next();
                            Log.d("Weather","city:    "+xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("updatetime")){
                            eventType = xmlPullPraser.next();
                            Log.d("Weather","updatetime:    "+xmlPullPraser.getText());
                        }
                        break;
                }
                eventType = xmlPullPraser.next();
            }

        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
