package com.wzy.iocdemo;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewOnItemClickListener<T> implements RecyclerView.OnItemTouchListener {

    private OnItemLongClickListener<T> mLongListener;
    private OnItemClickListener<T> mListener;
    private RecyclerView mRecyclerView;

    public RecyclerViewOnItemClickListener(RecyclerView mRecyclerView, OnItemClickListener<T> listener) {
        this.mRecyclerView = mRecyclerView;
        this.mListener = listener;
        initGestureListener();
    }

    public RecyclerViewOnItemClickListener(RecyclerView mRecyclerView, OnItemLongClickListener<T> onLongListener) {
        this.mRecyclerView = mRecyclerView;
        this.mLongListener = onLongListener;
        initGestureListener();
    }

    private void initGestureListener() {
        GestureDetector.OnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                initListener(e);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                initLongListener(e);

            }
        };
        mGestureDetector = new GestureDetector(mRecyclerView.getContext(), listener);
    }

    private void initListener(MotionEvent e) {
        View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null) {
            int position = mRecyclerView.getChildAdapterPosition(childView);
            BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) mRecyclerView.getAdapter();
            if (adapter != null) {
                Object data = adapter.getData(position);
                mListener.onItemClick(childView, (T) data, position);
            }
        }
    }

    private void initLongListener(MotionEvent e) {
        View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mLongListener != null) {
            int position = mRecyclerView.getChildAdapterPosition(childView);
            BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) mRecyclerView.getAdapter();
            if (adapter != null) {
                Object data = adapter.getData(position);
                mLongListener.onLongItemClick(childView, (T) data, position);
            }
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onLongItemClick(View view, T data, int position);
    }


    private GestureDetector mGestureDetector;


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
