package com.wzy.iocdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder<T>> {


    private List<T> datas;

    public BaseRecyclerViewAdapter(List<T> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        holder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public  T getData(int position) {
        return datas.get(position);
    }

    public static class ViewHolder<T> extends RecyclerView.ViewHolder {
        private T data;

        private TextView name;
        private TextView age;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            age = itemView.findViewById(R.id.tv_age);
        }

        public void setData(T data) {
            this.data = data;
            if (data instanceof Person) {
                Person person = (Person) data;
                name.setText(person.getName());
                age.setText(String.valueOf(person.getAge()));
            }
        }

    }
}
