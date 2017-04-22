package com.example.dva.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class guide extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private Button StartBtn;
    private List<View> view;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private EdgeEffectCompat leftEdge,rightEdge;
    private ImageView[] dots;
    private int[] id ={R.id.point_img1,R.id.point_img2,R.id.point_img3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        SharedPreferences sharedPreferences = getSharedPreferences("viewpagerdate", Activity.MODE_PRIVATE);
        if (sharedPreferences.getInt("time",0)==1){
            Intent i = new Intent(guide.this,MainActivity.class);
            startActivity(i);
            finish();
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("time",1);
            editor.commit();
        }

        init();
        initDots();
        StartBtn = (Button) view.get(2).findViewById(R.id.StartActivity_btn);
        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(guide.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void init(){
        LayoutInflater ly = LayoutInflater.from(this);
        view = new ArrayList<View>();
        view.add(ly.inflate(R.layout.guide_page1,null));
        view.add(ly.inflate(R.layout.guide_page2,null));
        view.add(ly.inflate(R.layout.guide_page3,null));

        viewPagerAdapter = new ViewPagerAdapter(view,this);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        try {
            Field leftEdgeField = viewPager.getClass().getDeclaredField("mleftEdge");
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mrightEdge");
            if (leftEdgeField!=null&& rightEdgeField!=null){
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat)leftEdgeField.get(viewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private  void initDots(){
        dots = new ImageView[view.size()];
        for (int i =0; i<view.size();i++){
            dots[i]= (ImageView) findViewById(id[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i=0;i<id.length;i++){
            Log.d("Select page id",Integer.toString(i));
            if (i==position){
                dots[i].setImageResource(R.drawable.dot_fouce);
            }else {
                dots[i].setImageResource(R.drawable.dot_unfouce);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
            if (rightEdge!=null&&!rightEdge.isFinished()){
                startActivity(new Intent(guide.this,MainActivity.class));
                guide.this.finish();
            }
    }
}
