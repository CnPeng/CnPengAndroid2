package com.cnpeng;

import android.app.Application;

/**
 * 作者：CnPeng
 * 时间：2018/8/3
 * 功用：
 * 其他：
 */
public class CnPengApplication extends Application {
    private Application mApplication;

    public Application getApplication() {
        return mApplication;
    }

    public void setApplication(Application application) {
        mApplication = application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }


}
