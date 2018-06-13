package com.example.android.listingdetail.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay on 11/29/17.
 */

public class Test {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    public Test(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getUserId(){
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getBody() {
        return body;
    }
}
