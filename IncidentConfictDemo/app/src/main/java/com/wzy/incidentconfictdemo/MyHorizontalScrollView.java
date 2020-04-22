package com.wzy.incidentconfictdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MyHorizontalScrollView extends ViewGroup {


    private int screenWidth;
    private int screenHeight;
    private static final int count = 3;

    private int lastX;
    private int lastY;


    public MyHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        screenWidth = getScreenWidth();
        screenHeight = getScreenHeight();
    }


    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int offset;
        for (int i = 0; i < count; i++) {
            offset = i * screenWidth;
            int left = offset;
            int top = 0;
            int right = left + screenWidth;
            int bottom = top + screenHeight;
            View childView = getChildAt(i);
            childView.layout(left, top, right, bottom);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < count; i++) {
            MyRecyclerView recyclerView = new MyRecyclerView(getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(screenWidth, screenHeight);
            recyclerView.setLayoutParams(layoutParams);
            addView(recyclerView);
            switch (i) {
                case 0:
                    recyclerView.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    recyclerView.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    recyclerView.setBackgroundColor(Color.BLUE);
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int makeWidth = MeasureSpec.makeMeasureSpec(count * screenWidth, MeasureSpec.EXACTLY);
        int makeHeight = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(makeWidth, makeHeight);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            int deltaX = (int) Math.abs(ev.getX() - lastX);
            int deltaY = (int) Math.abs(ev.getY() - lastY);
            if (deltaX > deltaY) {
                intercept = true;
            }
        }

        lastX = (int) ev.getX();
        lastY = (int) ev.getY();
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaX = event.getX() - lastX;
            float deltaY = event.getY() - lastY;
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                scrollBy((int) -deltaX, 0);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            int scrollX = getScrollX();
            int halfWidth = (int) (screenWidth * 0.5f);
            int position = scrollX / screenWidth;
            int offsetX = scrollX % screenWidth;
            if (Math.abs(offsetX) >= halfWidth) {
                if (offsetX > 0) {
                    position++;
                    position = Math.min(position, count);
                }
            } else {
                if (offsetX < 0) {
                    position--;
                    position = Math.max(0, position);
                }
            }
            scrollTo(position * screenWidth, 0);

        }

        lastX = (int) event.getX();
        lastY = (int) event.getY();
        return super.onTouchEvent(event);
    }
}
