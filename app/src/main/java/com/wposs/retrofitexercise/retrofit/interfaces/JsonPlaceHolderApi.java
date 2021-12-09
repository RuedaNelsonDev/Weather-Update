package com.wposs.retrofitexercise.retrofit.interfaces;


import com.wposs.retrofitexercise.retrofit.model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {


    @GET("posts")
    Call<List<Posts>> getPosts();
}
