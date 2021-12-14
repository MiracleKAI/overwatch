package com.miracle.overwatch.common.util;

import okhttp3.*;

import java.io.IOException;


/**
 * @author QiuKai
 * @date 2021/9/19 2:34 下午
 */
public class HttpUtils {

    public static void doGet(String url){
        // 1 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 2设置请求
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        // 3封装call
        Call call = client.newCall(request);
        // 4异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    System.out.println(response.body());
                }
            }
        });
        //同步调用,返回Response,会抛出IO异常
    }

    public static Response doPost(String url, String json){
        // 1 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 2 构建参数
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        // 3 构建 request
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        // 4 将Request封装为Call
        Call call = client.newCall(request);

        try {
            return call.execute();
        } catch (IOException e) {
            return null;
        }
    }
}
