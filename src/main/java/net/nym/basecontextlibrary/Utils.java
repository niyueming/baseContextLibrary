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

package net.nym.basecontextlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author niyueming
 * @date 2017-03-14
 * @time 09:30
 */

public class Utils {
    private Utils() {
    }

    /********************** activity跳转 **********************************/
    /**
     * @param context             {@link Activity} or {@link Fragment} or {@link android.app.Fragment}
     * @param targetActivityClass
     */
    public static void openActivity(@NonNull Object context, Class<?> targetActivityClass) {
        openActivity(context, targetActivityClass, null, null);
    }

    public static void openActivity(@NonNull Object context, Class<?> targetActivityClass, int requestCode) {
        openActivity(context, targetActivityClass, null, requestCode, null);
    }

    public static void openActivity(@NonNull Object context, Class<?> targetActivityClass, Bundle bundle, Bundle options) {
        if (Activity.class.isInstance(context)) {
            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, targetActivityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            activity.startActivity(intent, options);
        } else if (Fragment.class.isInstance(context)) {
            Fragment fragment = (Fragment) context;
            Intent intent = new Intent(fragment.getActivity(), targetActivityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            fragment.startActivity(intent, options);
        } else if (android.app.Fragment.class.isInstance(context)) {
            android.app.Fragment fragment = (android.app.Fragment) context;
            Intent intent = new Intent(fragment.getActivity(), targetActivityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            fragment.startActivity(intent, options);
        }

    }

    public static void openActivity(@NonNull Object context, Class<?> targetActivityClass, Bundle bundle, int requestCode, Bundle options) {
        if (Activity.class.isInstance(context)) {
            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, targetActivityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            activity.startActivityForResult(intent, requestCode, options);
        } else if (Fragment.class.isInstance(context)) {
            Fragment fragment = (Fragment) context;
            Intent intent = new Intent(fragment.getActivity(), targetActivityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            fragment.startActivityForResult(intent, requestCode, options);
        } else if (android.app.Fragment.class.isInstance(context)) {
            android.app.Fragment fragment = (android.app.Fragment) context;
            Intent intent = new Intent(fragment.getActivity(), targetActivityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            fragment.startActivityForResult(intent, requestCode, options);
        }

    }

    public static void openActivityAndCloseThis(@NonNull Object context, Class<?> targetActivityClass) {
        openActivity(context, targetActivityClass);
        finish(context);
    }

    public static void openActivityAndCloseThis(@NonNull Object context, Class<?> targetActivityClass, Bundle bundle) {
        openActivity(context, targetActivityClass, bundle, null);
        finish(context);
    }

    public static void openActivityWithTransition(Activity activity,Class<?> targetActivityClass,Bundle bundle,Pair<View,String>... pair){
        Intent intent = new Intent(activity,targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity
                ,pair
        );
        ActivityCompat.startActivity(activity
                ,intent
                ,optionsCompat.toBundle());
    }


    public static void openActivityWithTransition(Activity activity,Class<?> targetActivityClass,Bundle bundle,int requestCode,Pair<View,String>... pair){
        Intent intent = new Intent(activity,targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity
                ,pair
        );
        ActivityCompat.startActivityForResult(activity
                ,intent
                ,requestCode
                ,optionsCompat.toBundle());
    }

    @SafeVarargs
    public static void openActivityWithTransitionAndCloseThis(Activity activity, Class<?> targetActivityClass, Bundle bundle, Pair<View,String>... pair){
        openActivityWithTransition(activity,targetActivityClass,bundle,pair);
        finish(activity);
    }

 @SafeVarargs
 public static void openActivityWithTransitionAndCloseThis(Activity activity, Class<?> targetActivityClass, Bundle bundle, int requestCode, Pair<View,String>... pair){
        openActivityWithTransition(activity,targetActivityClass,bundle,requestCode,pair);
        finish(activity);
    }

    private static void finish(@NonNull Object context) {
        if (Activity.class.isInstance(context)) {
            Activity activity = (Activity) context;
//            activity.finish();
            ActivityCompat.finishAfterTransition(activity);
        } else if (Fragment.class.isInstance(context)) {
            Fragment fragment = (Fragment) context;
//            fragment.getActivity().finish();
            ActivityCompat.finishAfterTransition(fragment.getActivity());
        } else if (android.app.Fragment.class.isInstance(context)) {
            android.app.Fragment fragment = (android.app.Fragment) context;
//            fragment.getActivity().finish();
            ActivityCompat.finishAfterTransition(fragment.getActivity());
        }
    }

    /***************************************************************/

    public static void closeInputMethod(@NonNull Object context) {
        Activity activity = getActivity(context);
        if (activity != null){
            View view = activity.getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Nullable
    private static Activity getActivity(@NonNull Object context) {
        Activity activity = null;
        if(Activity.class.isInstance(context)){
            activity = (Activity) context;
        }else if(Fragment.class.isInstance(context)){
            Fragment fragment = (Fragment) context;
            activity = fragment.getActivity();
        }else if(android.app.Fragment.class.isInstance(context)){
            android.app.Fragment fragment = (android.app.Fragment) context;
            activity = fragment.getActivity();
        }
        return activity;
    }
}
