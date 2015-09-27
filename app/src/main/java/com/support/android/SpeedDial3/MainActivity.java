package com.support.android.SpeedDial3;

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package com.support.android.SpeedDial3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

//import com.bumptech.glide.Glide;


import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    public String setting_name = "SETTING_NAME";
    public PreferenceSetting my_setting;
    public List<PhoneCallUnit> myList;
    public MyRecycleViewAdapter myRecAdapter;
    public RecyclerView phone_list_view;
    public FloatingActionButton myFloatBtn, tempFB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Speed Dial");

        //loadBackdrop();


        initData();

        initGUI();


    }

    protected void initData() {
        //管理配置信息

        //这一步非常重要，首先要把配置文件setting_name创建起来，否则根本无法传递给PreferenceSetting这个类的实例，会出现Null Point错误
        SharedPreferences sharedPreferences = this.getSharedPreferences(setting_name, this.MODE_PRIVATE);
        //建立一个PreferenceSetting 实例，把setting_name和Context都传过去
        if (null == my_setting) {
            my_setting = new PreferenceSetting(setting_name, this);
        }

        my_setting.getRecordNumber();

        myList = my_setting.getRecordList();
    }

    private void initGUI() {

        phone_list_view = (RecyclerView) findViewById(R.id.phoneListView);
        myRecAdapter = new MyRecycleViewAdapter(this, R.layout.phone_item, myList);
        phone_list_view.setAdapter(myRecAdapter);
        RecyclerView.LayoutManager myLM = new LinearLayoutManager(this);
        phone_list_view.setLayoutManager(myLM);
        phone_list_view.setItemAnimator(new DefaultItemAnimator());


        MyOnClickListener phoneCallButton_listener;
        phoneCallButton_listener = new MyOnClickListener(this);

        myFloatBtn = (FloatingActionButton) findViewById(R.id.addPhoneNumFab);
        myFloatBtn.setOnClickListener(phoneCallButton_listener);
//
//        tempFB= (FloatingActionButton) findViewById(R.id.appbar);
//        tempFB.setOnClickListener(phoneCallButton_listener);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void loadBackdrop() {
//        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
//        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        //Log.d("AAAAArequest code:   ", String.valueOf(requestCode));
        //Log.d("AAAAAAresult code:   ", String.valueOf(resultCode));

        switch (requestCode) {
            case 1:

                if (resultCode == Activity.RESULT_OK) {
                    Bundle _bundle = data.getExtras();
                    Base64en_decode _myendecoder = new Base64en_decode();
                    PhoneCallUnit mPCU = new PhoneCallUnit();
                    try {

                        mPCU = _myendecoder.readObject(_bundle.getString("OBJECT"));
                        int _index = _bundle.getInt("INDEX");
//                        myList.set(_index, mPCU);
//                        myAdapter.notifyDataSetChanged();
                        myRecAdapter.setItem(_index,mPCU);
                        my_setting.putRecordList(myList);


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                } else {
                    if (resultCode == 5) {
                        Bundle _bundle = data.getExtras();
                        int _index = _bundle.getInt("INDEX");
//                        myList.remove(_index);
//                        myAdapter.notifyDataSetChanged();
                        myRecAdapter.deleteItem(_index);
                        my_setting.putRecordList(myList);
                    }
                }

                break;

            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle _bundle = data.getExtras();
                    Base64en_decode _myendecoder = new Base64en_decode();
                    PhoneCallUnit mPCU = new PhoneCallUnit();
                    try {

                        mPCU = _myendecoder.readObject(_bundle.getString("OBJECT"));
                        myRecAdapter.addItem(mPCU);
//                        myList.add(mPCU);
                        //这里一定要重新设定一下动态的listView显示高度，不然看不到新加的那行
//                        myAdapter.notifyDataSetChanged();
                        my_setting.putRecordList(myList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }
}
