package com.example.myapplication.db.course;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CourseViewModel extends ViewModel {
    private final MutableLiveData<List<CourseBean>> courses = new MutableLiveData<>();

    public LiveData<List<CourseBean>> getCourses() {
        return courses;
    }

    public void loadCourses() {
        // 使用Thread来确保在网络线程中加载数据
        new Thread(() -> {
            try {
                List<CourseBean> data = fetchDataFromNetwork();
                // 更新LiveData，这会触发观察者
                courses.postValue(data);
            } catch (IOException | JSONException e) {
                Log.e("CVModel", "Error loading courses", e);
            }
        }).start();
    }

    private List<CourseBean> fetchDataFromNetwork() throws IOException, JSONException {
        String url = "http://159.75.231.207:9000/red/video/v_list.json";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // 直接解析为 JSONArray，因为根元素是数组
        JSONArray jsonArray = new JSONArray(content.toString());

        List<CourseBean> listData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            int id = item.getInt("videoId");
            String title = item.getString("title");
            String desc = item.getString("desc");
            String imageUrl = "http://159.75.231.207:9000/red/video/v_" + id + ".png";
            listData.add(new CourseBean(imageUrl, title, desc));
        }
        return listData;
    }
}