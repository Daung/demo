package com.wzy.retrofitdemo;

import org.junit.Test;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public class RetrofitTestUnit {

    @Test
    public void retrofitTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .build();

    }
}
