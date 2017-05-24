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

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import net.nym.basecontextlibrary.Utils;
import net.nym.permissionlibrary.activity.NPermissionFragment;

/**
 * @author niyueming
 * @date 2017-03-15
 * @time 15:38
 */

public abstract class NBaseFragment extends NPermissionFragment {
    private final Handler mHandler = new Handler();
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isAdded()){
            if(getUserVisibleHint()) {
                onVisible();
            } else {
                onInvisible();
            }

        }
    }

    public final void runOnUiThreadDelayed(Runnable action,long delayMillis) {
        mHandler.postDelayed(action,delayMillis);
    }

    public final void runOnUiThread(Runnable action) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }


    public <T extends View> T findViewById(@IdRes int id){
        if (getView() == null){
            return null;
        }
        View view = getView().findViewById(id);
        return view == null ? null : (T)view;
    }

    public abstract void toast(@NonNull String text);
    public abstract void toast(@StringRes int stringId);

    public void showIndicator(){
        if (NBaseActivity.class.isInstance(getActivity())){
            ((NBaseActivity)getActivity()).showIndicator();
        }
    }

    public void dismissIndicator(){
        if (NBaseActivity.class.isInstance(getActivity())){
            ((NBaseActivity)getActivity()).dismissIndicator();
        }
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

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        Utils.openActivityAndCloseThis(this,targetActivityClass);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        Utils.openActivityAndCloseThis(this,targetActivityClass,bundle);
    }
    /***************************************************************/

    public void closeInputMethod(){
        // 收起键盘
        Utils.closeInputMethod(this);
    }
    /**
     * 可见
     */
    protected void onVisible() {
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }
}
