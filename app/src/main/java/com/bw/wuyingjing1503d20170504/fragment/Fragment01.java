package com.bw.wuyingjing1503d20170504.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.wuyingjing1503d20170504.Bean;
import com.bw.wuyingjing1503d20170504.MyApplication;
import com.bw.wuyingjing1503d20170504.R;
import com.bw.wuyingjing1503d20170504.Utils;
import com.bw.xlistview.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.bw.wuyingjing1503d20170504.MyApplication.initImageLoader;

/**
 * Created by 2016 on 2017/5/4.
 */

public class Fragment01 extends Fragment implements XListView.IXListViewListener{
    private AlertDialog.Builder dialog;

    private ViewPager vp1;
    private List<Bean.RecommendsBean> list;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0 :
                List<Bean.RecommendsBean> list1 = (List<Bean.RecommendsBean>) msg.obj;
                list.addAll(list1);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    };
    private MyAdapter adapter;
    private XListView lv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01,container,false);
        lv = (XListView) view.findViewById(R.id.lv1);
        vp1 = (ViewPager) view.findViewById(R.id.vp1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Context context = getActivity().getApplicationContext();
        initImageLoader(context);

        list = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                httpget();
                if (Utils.isWifi(getActivity())) {
                    Toast.makeText(getActivity(), "网络连接成功", Toast.LENGTH_SHORT).show();

                }else {
                    Looper.prepare();
                        //弹出dialog对话框
                        View view = View.inflate(getActivity(), R.layout.dialog_layout, null);
                        dialog = new AlertDialog.Builder(getActivity());
                        dialog.setView(view);
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(),"正在通过移动数据访问",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(), "当前不在联网状态", Toast.LENGTH_SHORT).show();
                            }
                        });




                    }
                dialog.show();
                Looper.loop();
                }


        }).start();
        vp1.setCurrentItem(10000);
        vp1.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.mipmap.ic_launcher);
                /*ImageLoader.getInstance().displayImage(list.get(position).getImage().getUrl(),imageView);*/
                container.addView(imageView);
                return imageView;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });



        //设置开启两个接口
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);


        adapter = new MyAdapter();


        lv.setAdapter(adapter);



    }

    private void httpget(){



            try {
                URL url = new URL("http://baobab.kaiyanapp.com/api/v4/discovery/hot?start=2&num=20&udid=f4cbbcd2e9444b09a73bf9f3de46c0ec6392c2ba&vc=183&vn=3.5.1&deviceModel=Redmi%20Note%204&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23");


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                connection.setConnectTimeout(20000);

                connection.setConnectTimeout(20000);

                if (connection.getResponseCode() == 200) {

                    String str = Utils.inputstreamToString(connection.getInputStream());

                    Gson gson = new Gson();

                    Bean bean = gson.fromJson(str, Bean.class);

                    List<Bean.RecommendsBean> list1 = bean.getRecommends();

                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = list1;

                    handler.sendMessage(msg);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    //刷新
    @Override
    public void onRefresh() {
        list.clear();

    }
    //加载更多
    @Override
    public void onLoadMore() {

    }


    //自定义 适配器
        class MyAdapter extends BaseAdapter{


            @Override
            public int getCount() {
                return list != null ? list.size() : 0;
            }

            @Override
            public Object getItem(int i) {
                return list.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ViewHolder vh;
                if (view == null){
                    view = View.inflate(getActivity(),R.layout.lv_item,null);
                    vh = new ViewHolder();
                    vh.iv = (ImageView) view.findViewById(R.id.lv_iv);
                    vh.tv1 = (TextView) view.findViewById(R.id.lv_tv1);
                    vh.tv2 = (TextView) view.findViewById(R.id.lv_tv2);
                    view.setTag(vh);
                }else {
                    vh = (ViewHolder) view.getTag();
                }

                ImageLoader.getInstance().displayImage(list.get(i).getImage().getUrl(),vh.iv);
//                MyApplication.initImageLoader(getActivity());
                vh.tv1.setText(list.get(i).getTitle());
                vh.tv2.setText(list.get(i).getArticle().getDispdesc());

                return view;
            }
        }
    //优化类
    class ViewHolder{
        ImageView iv;
        TextView tv1;
        TextView tv2;

    }

}
