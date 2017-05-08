package com.bw.wuyingjing1503d20170504;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by 2016 on 2017/5/4.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

    /*initImageLoader();*/

        initImageLoader(getApplicationContext());

    }

    public static void initImageLoader(Context context) {
        File file = new File(Environment.getExternalStorageDirectory(),"aaa.png");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.FIFO.LIFO)
                .writeDebugLogs()
                /*.diskCache(new UnlimitedDiskCache(file))*/
                .build();

        ImageLoader.getInstance().init(config);
    }



}
