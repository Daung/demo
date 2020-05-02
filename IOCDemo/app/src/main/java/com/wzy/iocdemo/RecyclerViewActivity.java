package com.wzy.iocdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_recycler_view)
public class RecyclerViewActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private List<Person> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            Person person = new Person("name is " + i, i);
            list.add(person);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new BaseRecyclerViewAdapter<>(list));
    }

    @OnItemClickListener(R.id.recycler_view)
    public void onItemClickListener(View view, Person person, int position) {
        Toast.makeText(this, person.getName(), Toast.LENGTH_SHORT).show();
    }


    @OnItemLongClickListener(R.id.recycler_view)
    public void onItemLongClickListener(View view, Person person, int position) {
        Toast.makeText(this, person.getName() + " position = " + position, Toast.LENGTH_SHORT).show();
    }
}
