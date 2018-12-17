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
    public static final String TOKEN = "EgIBT5Hm30yolfKgu5JuN4K0iQ5iPBncsUucktSsyjo=";
    public static final String VANTIQ_URL = "https://dev.vantiq.cn";
    // 发送到 '/test/topic', 需要在这个测试用户的当前name space里面
    public static final String TOPIC_URL = VANTIQ_URL + "/api/v1/resources/topics//mt/capitalheat/sap/domain1?token=" + TOKEN;

    public static void main(String[] args) {
        TestRestApi.postUpdateMaterial();
    }

    // 不使用第三方库，只是用java.net实现
    public static void postInvalidData() {

        // create a JSON object to POST
        JSONObject postJSON = new JSONObject();
        postJSON.put("name", "Brett Rudenstein");
        postJSON.put("age", "48");
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

    public static void postNewMaterial() {
        OkHttpClient okHttpClient = new OkHttpClient();

        // post data里面的数据需要是一个json格式的字符串，如 {"EVENT_KEY": "NEW_Material", "User_ID": "23456789"}
        JSONObject event = new JSONObject();
        event.put("EVENT_KEY", "NEW_Material");
        event.put("Service_Token", "some  token");
        event.put("User_ID", "23456789");
        JSONObject data = new JSONObject();
        data.put("MaterialID", "m1101");
        data.put("name", "物料的名字");
        data.put("prop1", "属性值");
        data.put("value1", 12.6);
        event.put("data", data);
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

    public static void postUpdateMaterial() {
        OkHttpClient okHttpClient = new OkHttpClient();

        // post data里面的数据需要是一个json格式的字符串，如 {"EVENT_KEY": "NEW_Material", "User_ID": "23456789"}
        JSONObject event = new JSONObject();
        event.put("EVENT_KEY", "UPDATE_Material");
        event.put("Service_Token", "some  token");
        event.put("User_ID", "23456789");
        JSONObject data = new JSONObject();
        data.put("MaterialID", "m1101");
        data.put("name", "物料的名字");
        data.put("prop1", "属性值");
        data.put("value1", 12.6);
        event.put("data", data);
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
