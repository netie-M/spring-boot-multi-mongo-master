package com.quark.cobra.posp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quark.cobra.posp.domain.BaseReqBo;
import com.quark.cobra.posp.domain.BaseRspBo;
import com.quark.cobra.posp.service.IPospService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Service
@Slf4j
public class PospServiceImpl implements IPospService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${posp.server.api.url}")
    private String pospApiUrl;
    @Value("${posp.server.gate-way.url}")
    private String pospGatewayUrl;

    @Override
    public BaseRspBo doPostPospApi(BaseReqBo baseReqBo){
        String responseString = restTemplate.postForObject(pospApiUrl,baseReqBo,String.class);
        return StringUtils.isEmpty(responseString)?null:JSON.parseObject(responseString,BaseRspBo.class);
    }

    @Override
    public String doPostPospGateWay(BaseReqBo baseReqBo) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type",MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add("Accept", "text/plain;charset=utf-8");
        headers.add("Accept-Encoding", "gzip");
        headers.add("Content-Encoding", "UTF-8");
        Map map = JSON.toJavaObject((JSONObject)JSON.toJSON(baseReqBo),Map.class);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.setAll(map);
        HttpEntity< MultiValueMap<String,String>> requestEntity = new HttpEntity<MultiValueMap<String,String>>(params,headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(pospGatewayUrl,requestEntity,byte[].class);
        return gunzip(responseEntity.getBody());
    }

    public String gunzip(byte[] bytes){
        try {
            @Cleanup ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
            @Cleanup GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer))>= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString("utf-8");
        } catch (IOException e) {
            log.error("gunzip失败",e);
            return "";
        }
    }
}
