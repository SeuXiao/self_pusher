package com.example.self_pusher.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.self_pusher.entity.Day;
import com.example.self_pusher.entity.Sentence;
import com.example.self_pusher.entity.Weather;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PusherService {
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public static String getToken(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("appid","wx7554bea3c354417c");
        map.put("secret","c441f07e41f2ee5d3476939f4690f42d");
        String res = restTemplate.getForObject(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}",
                String.class,
                map);
        JSONObject object = JSON.parseObject(res);
        return object.getString("access_token");
    }

    public static List<String> getAllPeople() {
        RestTemplate restTemplate = new RestTemplate();
        String access_token = getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN",access_token);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body).getJSONObject("data");
        JSONArray array = object.getJSONArray("openid");
        return array.toJavaList(String.class);
    }

    public static List<Weather> getWeather() {
        List<Weather> allWeather=new ArrayList<>();
        String []district_id={"110108","330106","320115","310104","110108"};
        RestTemplate restTemplate = new RestTemplate();
        for (String s : district_id) {
            Weather weather = new Weather();
            Map<String, String> map = new HashMap<>();
            map.put("district_id", s);
            map.put("data_type", "all");
            map.put("ak", "d81YkXn8PFSc1q7dQInI32rkIg6XVdIH");
            String res = restTemplate.getForObject(
                    "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                    String.class,
                    map);
            JSONObject json = JSONObject.parseObject(res);
            JSONObject now = json.getJSONObject("result").getJSONObject("now");
            weather.setText(now.getString("text"));
            weather.setTemp(now.getString("temp"));
            weather.setFeelsLike(now.getString("feels_like"));
            allWeather.add(weather);
        }
        return allWeather;
    }
    public static Sentence getSentence(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.iciba.com/dsapi";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        String content=jsonObject.getString("content");
        String note=jsonObject.getString("note");
        Sentence sentence=new Sentence();
        sentence.setContent(content);
        sentence.setNote(note);
        return sentence;
    }

    public static Day getDay() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String today=simpleDateFormat.format(new Date());
        map.put("day",today);
        map.put("app_id", "gnfkhxiriooaksmm");
        map.put("app_secret", "ZlJJNk0rS1lCMUUra3pya1gxaXRkZz09");
        String res = restTemplate.getForObject(
                "https://www.mxnzp.com/api/holiday/single/{day}?ignoreHoliday=false&app_id={app_id}&app_secret={app_secret}",
                String.class,
                map);
        JSONObject json = JSONObject.parseObject(res);
        JSONObject data = json.getJSONObject("data");
        String solarTerms=data.getString("solarTerms");
        String avoid=data.getString("avoid");
        String suit=data.getString("suit");
        Day day=new Day();
        day.setSolarTerms(solarTerms);
        day.setAvoid(avoid);
        day.setSuit(suit);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String res1 = restTemplate.getForObject(
                "https://www.mxnzp.com/api/history/today?type=1&app_id={app_id}&app_secret={app_secret}",
                String.class,
                map);
        JSONArray object = JSON.parseObject(res1).getJSONArray("data");
        JSONObject object1=object.getJSONObject(0);
        String title=object1.getString("title");
        day.setStore(title);
        return day;
    }

    public static int after(){
        String date="2021-01-13";
        int day=0;
        try {
            long time = System.currentTimeMillis()-simpleDateFormat.parse(date).getTime();
            day=(int)(time/86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
}
