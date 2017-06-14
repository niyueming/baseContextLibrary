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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v13.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import net.nym.basecontextlibrary.Utils;
import net.nym.permissionlibrary.activity.NPermissionActivity;

/**
 * 转场动画起点
 * ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this
                        ,new Pair<View, String>(view.findViewById(R.id.text),"test")
                    );
     ActivityCompat.startActivity(Context
     ,intent
     ,optionsCompat.toBundle());
 * @author niyueming
 * @date 2017-03-15
 * @time 15:36
 */

public abstract class NBaseActivity extends NPermissionActivity {
    protected final static String TAG = NBaseActivity.class.getSimpleName();

    private final Handler mHandler = new Handler();

    public <T extends View> T findView(@IdRes int id) {
        View view = (T)super.findViewById(id);
        return view == null ? null : (T)view;
    }

    public abstract void toast(@NonNull String text);
    public abstract void toast(@StringRes int stringId);
    public abstract void showIndicator();
    public abstract void dismissIndicator();

    public final void runOnUiThreadDelayed(Runnable action,long delayMillis) {
        mHandler.postDelayed(action,delayMillis);

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /********************** activity跳转 **********************************/
    public void openActivity(Class<?> targetActivityClass) {
        Utils.openActivity(this,targetActivityClass);
    }

    public void openActivity(Class<?> targetActivityClass,int requestCode) {
        Utils.openActivity(this,targetActivityClass,requestCode);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle, Bundle options) {
        Utils.openActivity(this,targetActivityClass,bundle,options);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle,int requestCode,Bundle options) {
        Utils.openActivity(this,targetActivityClass,bundle,requestCode,options);
    }

    @SafeVarargs
    public final void openActivityWithTransition(Class<?> targetActivityClass, Bundle bundle, Pair<View, String>... pair){
        Utils.openActivityWithTransition(this,targetActivityClass,bundle,pair);
    }

    @SafeVarargs
    public final void openActivityWithTransition(Class<?> targetActivityClass, Bundle bundle, int requestCode, Pair<View, String>... pair){
        Utils.openActivityWithTransition(this,targetActivityClass,bundle,requestCode,pair);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        Utils.openActivityAndCloseThis(this,targetActivityClass);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        Utils.openActivityAndCloseThis(this,targetActivityClass,bundle);
    }

    @SafeVarargs
    public final void openActivityWithTransitionAndCloseThis(Class<?> targetActivityClass, Bundle bundle, Pair<View, String>... pair){
        Utils.openActivityWithTransitionAndCloseThis(this,targetActivityClass,bundle,pair);
    }

    @SafeVarargs
    public final void openActivityWithTransitionAndCloseThis(Class<?> targetActivityClass, Bundle bundle, int requestCode, Pair<View, String>... pair){
        Utils.openActivityWithTransitionAndCloseThis(this,targetActivityClass,bundle,requestCode,pair);
    }

    /***************************************************************/

    public void closeInputMethod(){
        // 收起键盘
        Utils.closeInputMethod(this);
    }
}
