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
import android.content.res.Resources;

/**
 * @author niyueming
 * @date 2017-03-15
 * @time 15:25
 */

public class NBaseApplication extends Application implements ActivityLifecycleMonitor.OnForegroundListener {
    public static NBaseApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleMonitor(this));
    }

    public Resources getAppResources(){
        return app.getResources();
    }

    @Override
    public void onForeground(Activity activity) {
        System.out.println("前台");
    }

    @Override
    public void onBackground(Activity activity) {
        System.out.println("后台");

    }
}
