package com.example.dva.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dva.bean.TodayWeather;
import com.example.dva.util.NetUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by D.va on 2017/4/17.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int UPDATE_TODAY_WEATHER = 1;
//    private static final int UPDATE_TODAY_WEATHER_IMG=2;

    private ImageView CitySelect;
    private ImageView UpdateBtn;
    private TextView city_nameTV,cityTV,timeTV,humidityTV,pm2_5,pm2_5_dataTV,pm2_5_qualityTV,week_todayTV,temperatureTV,climateTV,windTV;
    private ImageView weatherImg,pmImg;

    private static final int[] images = new int[]{
            R.drawable.biz_plugin_weather_0_50,
            R.drawable.biz_plugin_weather_51_100,
            R.drawable.biz_plugin_weather_101_150,
            R.drawable.biz_plugin_weather_151_200,
            R.drawable.biz_plugin_weather_201_300,
            R.drawable.biz_plugin_weather_greater_300,
    };

    private Handler mhandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather)msg.obj);
                default:
                        break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.weather_info);
        UpdateBtn = (ImageView) findViewById(R.id.city_update_button);
        UpdateBtn.setOnClickListener(this);
        CitySelect = (ImageView) findViewById(R.id.title_city_manager);
        CitySelect.setOnClickListener(this);


        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.i("Weather","网络已连接");
            Toast.makeText(MainActivity.this,"网络已连接",Toast.LENGTH_SHORT).show();
        }else
        {
            Log.i("Weather","没网了！！！");
            Toast.makeText(MainActivity.this,"没。。。网。。。了",Toast.LENGTH_SHORT).show();
        }

        initview();


    }

    /**
     *
     * initview
     */

    void initview(){
        city_nameTV = (TextView) findViewById(R.id.city_name);
        cityTV = (TextView) findViewById(R.id.city);
        timeTV = (TextView) findViewById(R.id.time);
        humidityTV = (TextView) findViewById(R.id.humidity);
        pm2_5 = (TextView) findViewById(R.id.pm2_5);
        pm2_5_dataTV = (TextView) findViewById(R.id.pm2_5_data);
        pm2_5_qualityTV = (TextView) findViewById(R.id.pm2_5_quality);
        week_todayTV = (TextView) findViewById(R.id.week_today);
        temperatureTV = (TextView) findViewById(R.id.temperature);
        climateTV = (TextView) findViewById(R.id.climate);
        windTV = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);


        city_nameTV.setText("N/A");
        cityTV.setText("N/A");
        timeTV.setText("N/A");
        humidityTV.setText("N/A");
        pm2_5.setText("N/A");
        pm2_5_dataTV.setText("N/A");
        pm2_5_qualityTV.setText("N/A");
        week_todayTV.setText("N/A");
        temperatureTV.setText("N/A");
        climateTV.setText("N/A");
        windTV.setText("N/A");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode ==1 && resultCode ==RESULT_OK){
           String newCityCode = data.getStringExtra("citycode");
           Log.d("Weather","选择的城市代码："+newCityCode);

           if (NetUtil.getNetworkState(this)!=NetUtil.NETWORN_NONE){
               Log.d("Weather","网络已连接");
               queryWeatherCode(newCityCode);
           }else {
               Log.d("Weather","没网。。。");
               Toast.makeText(MainActivity.this,"没网~(≧▽≦)/~啦啦啦",Toast.LENGTH_LONG);
           }
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
            Toast.makeText(MainActivity.this,"没网~(≧▽≦)/~啦啦啦",Toast.LENGTH_LONG);
            }
        }
        if (view.getId() == R.id.title_city_manager){
            Intent intent = new Intent(this,SelectCity.class);
//            startActivity(intent);
            startActivityForResult(intent,1);
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
                TodayWeather todayWeather = null;
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
                    String responseStr = response.toString();
                    Log.d("Weather",responseStr);
                    todayWeather = parseXML(responseStr);
                    if (todayWeather!=null){
                        Log.d("Weather",todayWeather.toString());
                        Message message = new Message();
                        message.what = UPDATE_TODAY_WEATHER;
                        message.obj = todayWeather;
                        mhandler.sendMessage(message);
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
    private TodayWeather parseXML(String XMLdata){
        TodayWeather todayWeather =null;
        int fengxiangCount = 0;
        int fengliCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;

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
                        if (xmlPullPraser.getName().equals("resp")){
                            todayWeather = new TodayWeather();
                        }
                        if (xmlPullPraser.getName().equals("city")){
                            eventType = xmlPullPraser.next();
                            todayWeather.setCity(xmlPullPraser.getText());
                            //Log.d("Weather","city:    "+xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("updatetime")){
                            eventType = xmlPullPraser.next();
                            //Log.d("Weather","updatetime:    "+xmlPullPraser.getText());
                            todayWeather.setUpdatetime(xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("shidu")){
                            eventType = xmlPullPraser.next();
                            todayWeather.setShidu(xmlPullPraser.getText());
                            //Log.d("Weather","shidu:   "+xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("wendu")){
                            eventType = xmlPullPraser.next();
                            todayWeather.setWendu(xmlPullPraser.getText());
                            //Log.d("Weather","wendu:   "+xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("pm25")){
                            eventType = xmlPullPraser.next();
                            todayWeather.setPm2_5(xmlPullPraser.getText());
                            //Log.d("Weather","pm2.5:   "+xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("quality")){
                            eventType = xmlPullPraser.next();
                            todayWeather.setQuality(xmlPullPraser.getText());
                            //Log.d("Weather","kongqizhiliang:   "+xmlPullPraser.getText());
                        }else if (xmlPullPraser.getName().equals("fengxiang")&&fengxiangCount == 0){
                            eventType = xmlPullPraser.next();
                            todayWeather.setFengxiang(xmlPullPraser.getText());
                            //Log.d("Weather","fengxiang:    "+xmlPullPraser.getText());
                            fengxiangCount++;
                        }else if (xmlPullPraser.getName().equals("fengli")&&fengliCount == 0){
                            eventType = xmlPullPraser.next();
                            todayWeather.setFengli(xmlPullPraser.getText());
                            //Log.d("Weather","fengli:    "+xmlPullPraser.getText());
                            fengliCount++;
                        }else if (xmlPullPraser.getName().equals("date")&& dateCount == 0){
                            eventType = xmlPullPraser.next();
                            todayWeather.setDate(xmlPullPraser.getText());
                            //Log.d("Weather","data:   "+xmlPullPraser.getText());
                            dateCount++;
                        }else if (xmlPullPraser.getName().equals("high")&& highCount == 0){
                            eventType = xmlPullPraser.next();
                            todayWeather.setHigh(xmlPullPraser.getText().substring(2).trim());
                            //Log.d("Weather","high:    "+xmlPullPraser.getText());
                            highCount++;
                        }else if (xmlPullPraser.getName().equals("low")&& lowCount == 0){
                            eventType = xmlPullPraser.next();
                            todayWeather.setLow(xmlPullPraser.getText().substring(2).trim());
                            //Log.d("Weather","low:    "+xmlPullPraser.getText());
                            lowCount++;
                        }else if (xmlPullPraser.getName().equals("type")&& typeCount == 0){
                            eventType = xmlPullPraser.next();
                            todayWeather.setType(xmlPullPraser.getText());
                            //Log.d("Weather","type:   "+xmlPullPraser.getText());
                            typeCount++;
                        }
                        break;
                        //判断标签结束
                    case XmlPullParser.END_TAG:
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
        return todayWeather;
    }

    void  updateTodayWeather(TodayWeather todayWeather){
        city_nameTV.setText(todayWeather.getCity()+"天气");
        cityTV.setText(todayWeather.getCity());
        timeTV.setText(todayWeather.getUpdatetime()+"发布");
        humidityTV.setText("湿度："+todayWeather.getShidu());
        pm2_5.setText("PM2.5");
        pm2_5_dataTV.setText(todayWeather.getPm2_5());
        pm2_5_qualityTV.setText(todayWeather.getQuality());
        week_todayTV.setText(todayWeather.getDate());
        temperatureTV.setText(todayWeather.getLow()+"~"+todayWeather.getHigh());
        climateTV.setText(todayWeather.getType());
        windTV.setText("风力："+todayWeather.getFengli());

        /*
        pm2.5图片
         */
        int a=0;
        a=Integer.parseInt(todayWeather.getPm2_5().toString());
        if (a<=50){
            pmImg.setImageResource(images[0]);
        }else if (a<=100){
            pmImg.setImageResource(images[1]);
        }else if (a<=150){
            pmImg.setImageResource(images[2]);
        }else if (a<=200){
            pmImg.setImageResource(images[3]);
        }else if (a<=300){
            pmImg.setImageResource(images[4]);
        }else {
            pmImg.setImageResource(images[5]);
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (climateTV.getText().toString()){
                    case "暴雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                        break;
                    case "暴雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                        break;
                    case "大暴雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                        break;
                    case "大雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
                        break;
                    case "多云":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                        break;
                    case "雷阵雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                        break;
                    case "雷阵雨冰雹":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                        break;
                    case "晴":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
                        break;
                    case "沙尘暴":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                        break;
                    case "特大暴雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                        break;
                    case "雾":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
                        break;
                    case "小雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                        break;
                    case "小雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                        break;
                    case "阴":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
                        break;
                    case "雨夹雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                        break;
                    case "阵雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                        break;
                    case "阵雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                        break;
                    case "中雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                        break;
                    case "中雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                        break;
                    default:
                        break;
                }

            }
        });


        Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
    }
}
