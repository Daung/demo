package com.wzy.library;

public interface IService {

    @GET("/api/test")
    public String test(@Query("id") String id, @Query("name") String name);
}
