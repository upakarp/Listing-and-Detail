package com.example.android.listingdetail.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ajay on 11/29/17.
 */

public interface ApiInterface {
    @GET("/posts")
    Call<List<Test>> getTests();
}
