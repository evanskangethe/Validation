package com.tracom.validator.MpesaApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class MpesaService {

    public  final static Logger logger = LoggerFactory.getLogger(MpesaService.class);

    @Autowired
    private HttpCall<MpesaAuthorizationResponse> authorizationHttpCall;

    @Value("${mpesa.api.key}")
    private String consumer_key;

    @Value("${mpesa.api.secret}")
    private String consumer_secret;

    @Value("${mpesa.api.url}")
    private String app_url;

    public Map<String,Object> generateToken(){
        Map<String,Object> res = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String appString = consumer_key + ":" + consumer_secret;
            byte[] auth = Base64.encodeBase64(appString.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(auth);
            headers.add("authorization", authHeader);

            logger.info("URL: " + app_url);
            final ResponseEntity<MpesaAuthorizationResponse> response = authorizationHttpCall.sendAPIGatewayGETRequest(app_url,  headers, MpesaAuthorizationResponse.class);
            MpesaAuthorizationResponse authorizationResponse =response.getBody();


            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println();
                logger.info("STATE 200 RESPONSE FROM MPESA GATEWAY: " + objectMapper.writeValueAsString(authorizationResponse), Console.class);
                System.out.println();

                res.put("status", "00");
                res.put("accessToken", authorizationResponse.getAccess_token());
                res.put("expiresIn", authorizationResponse.getExpires_in());
                res.put("message", "Success.");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

}
