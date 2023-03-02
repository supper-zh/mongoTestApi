package com.example.mongotestapi.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mongotestapi.dao.JokeDao;
import com.example.mongotestapi.domain.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JokeService {
    private String jokeApiKey = "a1ba98d3190a4114f4e31b4cc3481823";
    @Autowired
    JokeDao jokeDao;

    public void httpRequest() {
        //得到long类型当前时间
        long l = System.currentTimeMillis();
        //new日期对象
        Date date = new Date(l);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        //调用的api的接口地址
        String apiPath = "https://way.jd.com/showapi/wbxh?time=" + time +
                "&page=2&maxResult=20&showapi_sign=bd0592992b4d4050bfc927fe7a4db9f3&appkey=" + jokeApiKey;

        BufferedReader in = null;
        StringBuffer result = null;

        try {
            URL url = new URL(apiPath);
            //打开和url之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            connection.connect();
            result = new StringBuffer();
            //读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            String result2 =  result.toString();
            //返回json字符串
            //获取数据
            JSONObject jsonObject = JSON.parseObject(result2);

//            测试
            String code = jsonObject.getString("code");
            System.out.println(code);

//            String code1 = jsonObject.toString();
//            String code2 = jsonObject.toJSONString();
//            System.out.println(code1);
//            System.out.println(code2);
            System.out.println(apiPath); //展示完整请求路径

//
            JSONObject resultJsonObject = jsonObject.getJSONObject("result");
            JSONObject bodyJsonObject = resultJsonObject.getJSONObject("showapi_res_body");
            JSONArray jsonArray = bodyJsonObject.getJSONArray("contentlist");
            //遍历json集合，取出数据
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                //System.out.println(jsonObject2);
                Joke joke = new Joke();
                //jsonObject2.get("x_x").toString()中的“x_x”要和实际返回的json数据中的字段名一致，否则可能会出现找不到字段的错误提示
                joke.setId(jsonObject2.get("id").toString());
                joke.setText(jsonObject2.get("text").toString());
                joke.setTitle(jsonObject2.get("title").toString());
                joke.setType(jsonObject2.get("type").toString());
                joke.setCt(jsonObject2.get("ct").toString());
                //dao层保存数据存入数据库
                jokeDao.save(joke);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


