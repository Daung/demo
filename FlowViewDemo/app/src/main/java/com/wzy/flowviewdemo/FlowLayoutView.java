package com.wzy.flowviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutView extends ViewGroup {
    private static final String TAG = "FlowLayoutView";


    private List<Integer> lineHeights = new ArrayList<>();
    private List<List<View>> children = new ArrayList<>();

    private int horizontalSpace = 20;

    private int verticalSpace = 20;

    public FlowLayoutView(Context context) {
        super(context);
    }

    public FlowLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int needheight = 0;
        for (int i = 0; i < children.size(); i++) {
            List<View> views = children.get(i);
            int height = lineHeights.get(i);

            for (View view : views) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                int leftMargin = layoutParams.leftMargin;
                int topMargin = layoutParams.topMargin;
                int left = paddingLeft + leftMargin;
                int top = paddingTop + topMargin + needheight;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                paddingLeft = right + horizontalSpace + layoutParams.rightMargin;
            }
            paddingLeft = getPaddingLeft();
            needheight += height + verticalSpace;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        lineHeights.clear();
        children.clear();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        int useWidth = 0;
        int lineHeight = 0;
        int needWidth = 0;
        int needHeight = 0;
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            LayoutParams marginLayoutParams =
                    (LayoutParams) childView.getLayoutParams();
            int horizontalMargin = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int verticalMargin = marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            lineHeight = Math.max(lineHeight, childHeight + verticalMargin);
            useWidth += childWidth + horizontalMargin + horizontalSpace;
            Log.d(TAG, "onMeasure: useWidth = " + useWidth);
            if ((useWidth > width && lineViews.size() > 0)) {
                //开始换行
                lineHeights.add(lineHeight);
                needHeight += lineHeight + verticalSpace;
                children.add(lineViews);
                lineHeight = 0;
                useWidth = childWidth + horizontalMargin + horizontalSpace;
                lineViews = new ArrayList<>();

            }
            lineViews.add(childView);
            if (i == count - 1) {
                lineHeight = Math.max(lineHeight, childHeight + verticalMargin);
                lineHeights.add(lineHeight);
                needHeight += lineHeight;
                children.add(lineViews);
            }
        }
        int realWidth = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY ? width : needWidth;
        int realHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY ? height : needHeight;
        setMeasuredDimension(realWidth, realHeight);
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) lp);
        } else if (lp instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) lp);
        }
        return new LayoutParams(lp);
    }


    /**
     * Per-child layout information associated with ViewLinearLayout.
     *
     * @attr ref android.R.styleable#LinearLayout_Layout_layout_weight
     * @attr ref android.R.styleable#LinearLayout_Layout_layout_gravity
     */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public float weight;


        public int gravity = -1;

        /**
         * {@inheritDoc}
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int width, int height) {
            super(width, height);
            weight = 0;
        }

        /**
         * Creates a new set of layout parameters with the specified width, height
         * and weight.
         *
         * @param width  the width, either {@link #MATCH_PARENT},
         *               {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param height the height, either {@link #MATCH_PARENT},
         *               {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param weight the weight
         */
        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.weight = weight;
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        /**
         * Copy constructor. Clones the width, height, margin values, weight,
         * and gravity of the source.
         *
         * @param source The layout params to copy from.
         */
        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);

            this.weight = source.weight;
            this.gravity = source.gravity;
        }

    }
}
