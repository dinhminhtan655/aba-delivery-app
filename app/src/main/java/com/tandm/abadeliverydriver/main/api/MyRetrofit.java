package com.tandm.abadeliverydriver.main.api;

import android.content.Context;

import com.tandm.abadeliverydriver.main.preference.ConnectStringPrefer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyRetrofit {

    private static Retrofit ourInstance;


    public static MyRequests initRequest(Context context) {

//        String BASE_URL = "http://118.69.37.34:567/";  //link public
//        String BASE_URL = "http://api-delivery.aba.com.vn:567/";  //link public tên miền
//        String BASE_URL = "https://api-delivery.aba.com.vn:44567/";  //link public tên miền ssl
//        String BASE_URL = "http://192.168.1.49/aba2/"; // link home
//        String BASE_URL = "http://192.168.0.117/aba2/"; // link home2
//        String BASE_URL = "http://192.168.6.143/aba2/"; // link cong ty
//        String BASE_URL = "http://192.168.2.113/aba2/"; // link Q12
//        String BASE_URL = "http://10.10.100.208/aba2/"; // link Kho SG1
//        String BASE_URL = "http://192.168.9.234/aba2/";  //link kho MD
//        String BASE_URL = "http://192.168.9.230/aba2/";  //link kho MD2 IP Tĩnh

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
        client.sslSocketFactory();

//        if (ourInstance == null) {
            ourInstance = new Retrofit.Builder()
                    .baseUrl( String.format("%s://%s/", ConnectStringPrefer.getSSLOrNoSSL(context), !ConnectStringPrefer.getDomain(context).equals("") ? ConnectStringPrefer.getDomain(context) : "api-delivery.aba.com.vn:44567"))
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
//        }

        return ourInstance.create(MyRequests.class);

    }

}
