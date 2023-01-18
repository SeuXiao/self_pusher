package com.example.self_pusher.utils;

import com.example.self_pusher.entity.Day;
import com.example.self_pusher.entity.Sentence;
import com.example.self_pusher.entity.Weather;
import com.example.self_pusher.service.PusherService;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PushImpl {
    @Scheduled(cron = "0 0 9 * * ?")
    public static void testMessage() {
        String appId = "wx7554bea3c354417c";
        String secret = "c441f07e41f2ee5d3476939f4690f42d";
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        // 设置appId和secret
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();

        wxMpService.setWxMpConfigStorage(wxStorage);
        // 设置消息模板的templateID

        String messageTemplateId = "-hek3zjDL51jK5ZWuG2fUgLyAtzcTsugdZf8qFolV70";
        String messageTemplateId2="PwERM8xbXd26YV07yLIp8Jlw4b8UGOvjcPwQbyyuhRk";

        //2,推送消息
        List<String> list = PusherService.getAllPeople();
        List<Weather> allweather = PusherService.getWeather();
        Sentence sentence = PusherService.getSentence();
        Day day = PusherService.getDay();

        for (String s : list) {
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(s) // 要推送的用户openid
                    .templateId(messageTemplateId) // 模版id
                    .build();
            templateMessage.addData(new WxMpTemplateData("text1", "天气:" + allweather.get(0).getText(), "#FF00FF"));
            templateMessage.addData(new WxMpTemplateData("temp1", "室外:" + allweather.get(0).getTemp() + "度", "#FF33FF"));
            templateMessage.addData(new WxMpTemplateData("feelsLike1", "体感:" + allweather.get(0).getFeelsLike() + "度", "#FF66FF"));
            templateMessage.addData(new WxMpTemplateData("text2", "天气:" + allweather.get(1).getText(), "#CC00FF"));
            templateMessage.addData(new WxMpTemplateData("temp2", "室外:" + allweather.get(1).getTemp() + "度", "#CC33FF"));
            templateMessage.addData(new WxMpTemplateData("feelsLike2", "体感:" + allweather.get(1).getFeelsLike() + "度", "#CC66FF"));
            templateMessage.addData(new WxMpTemplateData("text3", "天气:" + allweather.get(2).getText(), "#9900FF"));
            templateMessage.addData(new WxMpTemplateData("temp3", "室外:" + allweather.get(2).getTemp() + "度", "#9933FF"));
            templateMessage.addData(new WxMpTemplateData("feelsLike3", "体感:" + allweather.get(2).getFeelsLike() + "度", "#9966FF"));
            templateMessage.addData(new WxMpTemplateData("text4", "天气:" + allweather.get(3).getText(), "#FFB5C5"));
            templateMessage.addData(new WxMpTemplateData("temp4", "室外:" + allweather.get(3).getTemp() + "度", "#FFB6C1"));
            templateMessage.addData(new WxMpTemplateData("feelsLike4", "体感:" + allweather.get(3).getFeelsLike() + "度", "#FFB5C5"));
            templateMessage.addData(new WxMpTemplateData("solarTerms", day.getSolarTerms(), "#99FF33"));
            templateMessage.addData(new WxMpTemplateData("avoid", "不宜:" + day.getAvoid(), "#FF0000"));
            templateMessage.addData(new WxMpTemplateData("suit", "宜:" + day.getSuit(), "#99FF99"));
            templateMessage.addData(new WxMpTemplateData("store", day.getStore(), "#FF9966"));
            templateMessage.addData(new WxMpTemplateData("content", sentence.getContent(), "#4876FF"));
            try {
                wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        String lova_day=Integer.toString(PusherService.after());
        WxMpTemplateMessage templateMessage1 = WxMpTemplateMessage.builder()
                .toUser("om-w16jpTDbBr9tKxivuOymcPNiM") // 要推送的用户openid
                .templateId(messageTemplateId2) // 模版id
                .build();
        templateMessage1.addData(new WxMpTemplateData("city", "北京海淀区", "#FF00FF"));
        templateMessage1.addData(new WxMpTemplateData("text", "天气:" + allweather.get(3).getText(), "#FF00FF"));
        templateMessage1.addData(new WxMpTemplateData("temp", "室外:" + allweather.get(3).getTemp() + "度", "#FF33FF"));
        templateMessage1.addData(new WxMpTemplateData("feelsLike", "体感:" + allweather.get(3).getFeelsLike() + "度", "#FF66FF"));
        templateMessage1.addData(new WxMpTemplateData("love_day",  lova_day, "#FF00FF"));
        templateMessage1.addData(new WxMpTemplateData("solarTerms", day.getSolarTerms(), "#99FF33"));
        templateMessage1.addData(new WxMpTemplateData("avoid", "不宜:" + day.getAvoid(), "#FF0000"));
        templateMessage1.addData(new WxMpTemplateData("suit", "宜:" + day.getSuit(), "#99FF99"));
        templateMessage1.addData(new WxMpTemplateData("store", day.getStore(), "#FF9966"));
        templateMessage1.addData(new WxMpTemplateData("content", sentence.getContent(), "#4876FF"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
