package com.jude.sms.api.maisui.client;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.jude.sms.api.maisui.bo.*;
import com.jude.sms.api.maisui.enums.SMSMaisuiUrlEnums;
import com.jude.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 16:20
 */
@Configuration
@Data
@Slf4j
@Validated
public class MaisuiHttpClient {

    @Value("${sms.maisui.baseUrl}")
    private String baseUrl;  //

    @Value("${sms.maisui.userName}")
    private String userName;  //

    @Value("${sms.maisui.password}")
    private String password; //

    private String token;

    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().
                add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().
                add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        SmsLogin smsLogin = new SmsLogin();
        smsLogin.setUserName(userName);
        smsLogin.setPassword(generateSig(generateSig(userName) + generateSig(password)));
        String response = getStringResponseEntity(smsLogin, SMSMaisuiUrlEnums.LOGIN);
        SmsResponse<SmsAuth> smsResponse = JSONObject.parseObject(response, new TypeReference<SmsResponse<SmsAuth>>() {});

        if (smsResponse.getSuccess()) {
            if (Objects.nonNull(smsResponse.getData()) && StringUtil.isNotEmpty(smsResponse.getData().getToken())) {
                token = smsResponse.getData().getToken();
            }
        }
    }

    private String getApiUrl(String endpoint) {
        return baseUrl + endpoint;
    }

    // 获取加签sig的通用方法
    public static String generateSig(String base) {
        try {
            // 这里可以调用MD5加密方法
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bytes = base.getBytes(StandardCharsets.UTF_8);
            byte[] digest = instance.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.info("没有MD5解析功能");
            return null;
        }
    }

    public String getStringResponseEntity(@NotNull Object request, @NotNull SMSMaisuiUrlEnums smsMaisuiUrlEnums) {
        String url = getApiUrl(smsMaisuiUrlEnums.getOperate());
        HttpHeaders headers = new HttpHeaders();
        String requestJson = JSONObject.toJSONString(request);
        headers.set("Content-Type", "application/json");
        headers.set("token", this.token);
        log.info("[{}]req:[{}]", smsMaisuiUrlEnums.getDesc(), requestJson);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(requestJson, headers), String.class);
        String body = response.getBody();
        SmsResponse smsReceiptResponse = JSONObject.parseObject(body, SmsResponse.class);

        log.info("[{}]res:[{}]", smsMaisuiUrlEnums.getDesc(), body);
        return body;
    }
}
