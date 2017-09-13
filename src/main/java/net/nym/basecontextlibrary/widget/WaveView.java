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

package net.nym.basecontextlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 描绘函数y = Asin(wx+d)+offset
 * @author niyueming
 * @date 2017-09-12
 * @time 16:44
 */

public class WaveView extends View {
    private static final int X_STEP = 1;
    private static final int PEROID = 100;// 绘制周期
    private int mHeight;
    private int mWidth;
    private float waveHeight; //振幅
    private float moveWave = 0;   //初相
    private float heightOffset = 0; //影响y的位置
    private float omega;    //影响周期
    private float moveSpeed = 2;// 波形的移动速度
    private float mAlpha = 0.5f;
    private ArrayList<Path> mPaths;

    private ArrayList<Paint> mWavePaints= new ArrayList<>();
    private DrawFilter mDrawFilter;
    @ColorInt
    private int mWavePaintColor = Color.WHITE;
    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mPaths = new ArrayList<>();
        mPaths.add(new Path());
        mPaths.add(new Path());
        mPaths.add(new Path());

        mWavePaints = new ArrayList<>();
        mWavePaints.add(getPaint());
        mWavePaints.add(getPaint());
        mWavePaints.add(getPaint());
    }


    private Paint getPaint(){

        // 初始绘制波纹的画笔
        Paint mWavePaint = new Paint();
        // 去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        // 设置风格为实线
        mWavePaint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
        mWavePaint.setColor(mWavePaintColor);
        return mWavePaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        initWaveParam();
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void initWaveParam() {
        this.mWidth = getWidth();
        this.mHeight = getHeight();
        waveHeight = mHeight/4f ;
        omega = (float) (2f * Math.PI / mWidth);
        moveSpeed = 0.15f;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        float location = 1;
        for (int i = 0;i < mPaths.size(); i++,location += 0.3){
            Paint paint = mWavePaints.get(i);
            paint.setAlpha((int) (255 * mAlpha) );
            canvas.drawPath(getWavePath(mPaths.get(i),location),paint);

        }

        moveWave += moveSpeed;
        postInvalidateDelayed(PEROID);
    }

    private Path getWavePath(@NonNull Path path,float location) {
        // 绘制区域的路径
        path.reset();
        path.moveTo(0, mHeight);// 移动到左下角的点
        for (float x = 0; x <= mWidth; x += X_STEP) {
            float y = (float) (waveHeight * Math.sin(omega * x + moveWave * location) + waveHeight) + heightOffset;
            path.lineTo(x, y);
        }
        path.lineTo(mWidth, 0);
        path.lineTo(mWidth, mHeight);
        return path;
    }

    public void setWaveCount(int count){
        if (count > mPaths.size()){
            for (int i = mPaths.size();i < count;i ++){
                mPaths.add(new Path());
                mWavePaints.add(getPaint());
            }
        }else if(count < mPaths.size()) {
            for (int i = count;i < mPaths.size();i ++){
                mPaths.remove(i);
                mWavePaints.remove(i);
                i--;
            }
        }
    }

    public void setAlpha(float alpha){
        if (alpha >= 0 && alpha <= 1){
            mAlpha = alpha;
        }
    }

    public void setWavePaintColor(@ColorInt int color) {
        this.mWavePaintColor = color;
        for (Paint paint: mWavePaints){
            paint.setColor(mWavePaintColor);
        }
    }

    /**
     * 设置omega
     *
     * @param omega
     */
    public void setOmega(float omega) {
        this.omega = omega;
    }

    /**
     * 设置波形的高度
     *
     * @param waveHeight
     */
    public void setWaveHeight(float waveHeight) {
        this.waveHeight = waveHeight;
    }

    /**
     * 设置波形的移动速度
     *
     * @param moveSpeed
     */
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
