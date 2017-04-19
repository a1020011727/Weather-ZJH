package com.example.dva.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.example.dva.bean.City;
import com.example.dva.db.CityDB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by D.va on 2017/4/19.
 */

public class Myapplication extends Application {
    private static final String TAG = "MyAPP";
    private static Myapplication myapplication;
    private CityDB mCityDB;
    private List<City> mCityList;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MyApplication->Oncreate");
        myapplication = this;
        mCityDB = openCityDB();
        initCityList();

    }
    public static Myapplication getInstance(){
        return myapplication;
    }
    private CityDB openCityDB(){
        String path ="/data"+
                Environment.getDataDirectory().getAbsolutePath()+
                File.separator+getPackageName()+
                File.separator+"database1"+
                File.separator+
                CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
        if (!db.exists()){
            String pathfolder = "/data"+
                    Environment.getDataDirectory().getAbsolutePath()+
                    File.separator+getPackageName()+
                    File.separator+"database1"+
                    File.separator;
            File dirFirstFolder = new File(pathfolder);
            if (!dirFirstFolder.exists()){
                dirFirstFolder.mkdirs();
                Log.i(TAG,"mkdirs");
            }
            Log.i("MyApp","db is not exists");
            try {
                InputStream inputStream =getAssets().open("city.db");
                FileOutputStream fileOutputStream = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len=inputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                inputStream.close();


            }catch (Exception e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this,path);
    }

    private void initCityList(){
        mCityList =new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }

    private boolean prepareCityList(){
        mCityList = CityDB.getAllCity();
        int i=0;
        for (City city:mCityList){
            i++;
            String CityName = city.getCity();
            String citycode = city.getNumber();
            Log.d(TAG,citycode+":"+CityName);
        }
        Log.d(TAG,"i="+i);
        return  true;
    }
    public List<City> getCityList(){
        return mCityList;
    }
}
