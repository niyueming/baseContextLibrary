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

import android.support.annotation.IdRes;
import android.support.v13.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;

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

public class NBaseActivity extends NPermissionActivity {


    public <T extends View> T findView(@IdRes int id) {
        View view = (T)super.findViewById(id);
        return view == null ? null : (T)view;
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
}
