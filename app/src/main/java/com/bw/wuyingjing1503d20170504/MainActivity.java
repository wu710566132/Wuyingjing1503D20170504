package com.bw.wuyingjing1503d20170504;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.wuyingjing1503d20170504.fragment.Fragment01;
import com.bw.wuyingjing1503d20170504.fragment.Fragment02;
import com.bw.wuyingjing1503d20170504.fragment.Fragment03;
import com.bw.wuyingjing1503d20170504.fragment.Fragment04;

/**
 * 主页面
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager vp;

    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //发现控件
        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb3);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            Fragment fragment = null;
            @Override
            public Fragment getItem(int position) {


                switch (position){

                    case 0 :
                        fragment = new Fragment01();
                        break;

                    case 1 :
                        fragment = new Fragment02();
                        break;

                    case 2:
                        fragment = new Fragment03();
                        break;

                    case 3:
                        fragment = new Fragment04();
                        break;
                }


                return fragment;
            }

            @Override
            public int getCount() {

                return 4;
            }
        });

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rg.check(rg.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                for (i=0;i<3;i++) {
                    if (rg.getChildAt(i).getId() == i) {
                        vp.setCurrentItem(i, false);
                    }
                }
                }
        });
    }


    @Override
    public void onClick(View view) {




    }
}
