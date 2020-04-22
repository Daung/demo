package com.wzy.incidentconfictdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(new Button(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setData(position);
        }


        @Override
        public int getItemCount() {
            return 100;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private final Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button = (Button) itemView;
            button.setOnClickListener(this);
        }

        public void setData(int position) {
            button.setText(String.valueOf(position));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), button.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
