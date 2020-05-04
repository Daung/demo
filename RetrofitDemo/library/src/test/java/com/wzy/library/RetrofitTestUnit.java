package com.wzy.library;

import org.junit.Test;


public class RetrofitTestUnit {

    @Test
    public void retrofitTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.test.com")
                .build();
        IService service = retrofit.create(IService.class);
        String result = service.test("1111", "wzy");
        System.out.println(result);
    }
}
