package com.vantiq.poc.topic;

import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestRestApi {

    // 在capital_heat namespace里面的token
    // ms_cptheat_demo: w8khbbIYraEjjSqf33en6mKPVptMeKvhVxyJluRJusc=
//    public static final String TOKEN = "u1R9afOzTVtIxJUB3tWbXJ1gJymlVLA4mlS7_axADsE=";
//    public static final String VANTIQ_URL = "https://dev.vantiq.cn";


//    public static final String TOPIC_SUB_2 = "/service2/DomainBar";
    // 发送到 '/test/topic', 需要在这个测试用户的当前name space里面
    public final String TOPIC_URL = VantiqTest.VANTIQ_URL + "/api/v1/resources/topics/" + VantiqTest.TOPIC_PUB + "?token=" + VantiqTest.TOKEN;

    // 不使用第三方库，只是用java.net实现
    public void postWithHttp() {

        // create a JSON object to POST
        JSONObject postJSON = new JSONObject();
        postJSON.put("name", "Brett 1");
        postJSON.put("id", "23456789");

        String json = postJSON.toString();
        try {
            URL url = new URL(TOPIC_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            String result = IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            System.out.println("Message Published");
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void postWithOKHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();

        JSONObject event = new JSONObject();
        event.put("name", "Brett 2");
        event.put("id", "23456789");
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), event.toString());

        Request request = new Request.Builder()
                .url(TOPIC_URL)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
