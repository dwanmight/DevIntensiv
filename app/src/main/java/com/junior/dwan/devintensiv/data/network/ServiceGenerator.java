package com.junior.dwan.devintensiv.data.network;

import com.junior.dwan.devintensiv.utils.AppConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Might on 15.09.2016.
 */
public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient=new OkHttpClient.Builder();

    private static Retrofit.Builder sBuilder=new Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


   public static <S> S createService(Class<S> serviceClass){
       HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
       logging.setLevel(HttpLoggingInterceptor.Level.BODY);
       httpClient.addInterceptor(logging);


        Retrofit retrofit =sBuilder.client(httpClient.build())
                .build();
       return retrofit.create(serviceClass);
   }
}
