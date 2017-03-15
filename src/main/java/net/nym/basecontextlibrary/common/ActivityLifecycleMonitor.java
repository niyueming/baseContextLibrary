/*
 * Copyright (c) 2017  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.basecontextlibrary.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author niyueming
 * @date 2017-03-15
 * @time 18:22
 */

public class ActivityLifecycleMonitor implements Application.ActivityLifecycleCallbacks {

    private static AtomicInteger activityCount = new AtomicInteger(0);
    private OnForegroundListener mOnForegroundListener;

    public ActivityLifecycleMonitor(){

    }

    public ActivityLifecycleMonitor(OnForegroundListener onForegroundListener){
        this.mOnForegroundListener = onForegroundListener;
    }

    public static boolean isAppForeground(){
        return activityCount.get() > 0;
    }


    public void setOnForegroundListener(OnForegroundListener onForegroundListener) {
        this.mOnForegroundListener = onForegroundListener;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activityCount.get() == 0){
            //切到前台
            if (mOnForegroundListener != null){
                mOnForegroundListener.onForeground();
            }
        }
        activityCount.incrementAndGet();
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount.decrementAndGet();
        if (activityCount.get() == 0){
            //切到后台
            if (mOnForegroundListener != null){
                mOnForegroundListener.onBackground();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    interface OnForegroundListener{
        void onForeground();
        void onBackground();
    }
}