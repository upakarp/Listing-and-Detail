package com.example.android.listingdetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.listingdetail.Retrofit.ApiClient;
import com.example.android.listingdetail.Retrofit.ApiInterface;
import com.example.android.listingdetail.Retrofit.Test;
import com.example.android.listingdetail.Retrofit.RecyclerAdapter;
import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter, adapter_cache;
    private List<Test> tests;
    private ApiInterface apiInterface;
    Response<List<Test>> response;
    int cacheSize = 10*1024*1024;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String KEY ="Prefs_Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Test>> call = apiInterface.getTests();

        call.enqueue(new Callback<List<Test>>() {

            @Override
            public void onResponse(Call<List<Test>> call,  Response<List<Test>> response) {
                tests = response.body();
                adapter = new RecyclerAdapter(tests);
                recyclerView.setAdapter(adapter);

                storeData(response);

            }

            @Override
            public void onFailure(Call<List<Test>> call, Throwable t) {
                call.cancel();
            }
        });

        if(!isNetworkAvailable()){
            String data  = getData();

            //if no data is cached
            if (data == "Value") {
                Toast.makeText(getApplicationContext(), "No cached data yet", Toast.LENGTH_SHORT).show();
            }
            //if data is already cached
            else {
                JsonParser parser = new JsonParser();
                JsonObject rootObject = parser.parse(data).getAsJsonObject();
                JsonElement rootElement = rootObject.get("body");

                Gson gson = new Gson();
                List<Test> testList = new ArrayList<>();

                if (rootElement.isJsonObject()) {
                    //The returned list has only 1 element
                    Test test = gson.fromJson(rootElement, Test.class);
                    testList.add(test);
                } else if (rootElement.isJsonArray()) {
                    //The returned list has >1 elements
                    Type testListType = new TypeToken<List<Test>>() {
                    }.getType();
                    testList = gson.fromJson(rootElement, testListType);
                }

                adapter_cache = new RecyclerAdapter(testList);
                recyclerView.setAdapter(adapter_cache);
            }
        }

    }

    //checking network availability
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager
                =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //caching data
    public void storeData(Response<List<Test>> data){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);

        editor.putString(KEY, jsonData);

        editor.apply();
        Toast.makeText(getApplicationContext(), "Cache successful", Toast.LENGTH_SHORT).show();

    }

    //retrieving data
    public String getData(){
        SharedPreferences sharedPref = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        String defaultValue = sharedPref.getString(KEY, "Value");
        Log.i("Tag", defaultValue);
        return defaultValue;
    }


}
