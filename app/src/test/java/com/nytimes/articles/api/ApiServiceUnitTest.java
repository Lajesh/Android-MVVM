package com.nytimes.articles.api;

import com.nytimes.articles.data.remote.ApiConstants;
import com.nytimes.articles.data.remote.ApiService;
import com.nytimes.articles.data.remote.RequestInterceptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ApiServiceUnitTest {

   private ApiService apiService;


    @Before
    public void createService() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.addInterceptor(new RequestInterceptor());
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        apiService = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(ApiService.class);
    }


    @Test
    public void getPopularArticles() {
        try {
            Response response = apiService.loadPopularArticles(7).execute();
            assertEquals(response.code(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
