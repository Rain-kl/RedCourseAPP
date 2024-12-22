package com.example.myapplication.ui.course;

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

/**
 * ViewModel 类，负责管理与课程相关的数据
 */
public class CourseViewModel extends ViewModel {

    // LiveData 对象，用于存储和更新课程列表
    private final MutableLiveData<List<CourseBean>> courses = new MutableLiveData<>();

    /**
     * 获取课程列表的 LiveData
     *
     * @return 包含课程列表的 LiveData
     */
    public LiveData<List<CourseBean>> getCourses() {
        return courses;
    }

    /**
     * 从网络加载课程数据
     */
    public void loadCourses() {
        // 使用 Thread 来确保在网络线程中加载数据
        new Thread(() -> {
            try {
                List<CourseBean> data = fetchDataFromNetwork();
                // 更新 LiveData，这会触发观察者
                courses.postValue(data);
            } catch (IOException | JSONException e) {
                Log.e("CVModel", "Error loading courses", e);
            }
        }).start();
    }

    /**
     * 从网络获取课程数据
     *
     * @return 课程列表
     * @throws IOException      网络请求异常
     * @throws JSONException    JSON 解析异常
     */
    private List<CourseBean> fetchDataFromNetwork() throws IOException, JSONException {
        // 定义 API 地址
        String url = "http://159.75.231.207:9000/red/video/v_list.json";

        // 打开 HTTP 连接
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        // 读取响应内容
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // 解析 JSON 数据
        JSONArray jsonArray = new JSONArray(content.toString());

        List<CourseBean> listData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String id = item.getString("videoId");
            String title = item.getString("title");
            String desc = item.getString("desc");
            listData.add(new CourseBean(id, title, desc));
        }
        return listData;
    }
}
