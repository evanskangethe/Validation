package com.tracom.validator.MpesaApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracom.validator.MpesaApi.MpesaClass.MpesaRequestUrl;
import com.tracom.validator.MpesaApi.MpesaClass.MpesaResponseParameter;
import com.tracom.validator.MpesaApi.MpesaClass.MpesaSimulateUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

@Service
public class MpesaC2BService {

    @Autowired
    public MpesaService mpesaService;

    public  final static Logger logger = LoggerFactory.getLogger(MpesaService.class);

    @Autowired
    private HttpCall<MpesaResponseParameter> authorizationHttpCall;

    @Value("${mpesa.api.url.c2b}")
    private String csb_url;

    @Value("${mpesa.api.c2b.confirmation.url}")
    private String confirmation_url;

    @Value("${mpesa.api.c2b.validation.url}")
    private String validation_url;

    @Value("${mpesa.api.c2b.short.code}")
    private String short_code;

    @Value("${mpesa.api.url.simulate_url}")
    private String simulate_url;

    public void executeRegisterUrl(){
        Map<String,Object> res = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            HttpHeaders headers = new HttpHeaders();
            String Access_token = (String) mpesaService.generateToken().get("accessToken");
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("authorization", "Bearer " +Access_token);

            MpesaRequestUrl mpesaRequestUrl = new MpesaRequestUrl();
            mpesaRequestUrl.setShortCode(short_code);
            mpesaRequestUrl.setConfirmationURL(confirmation_url);
            mpesaRequestUrl.setValidationURL(validation_url);
            mpesaRequestUrl.setResponseType("Cancelled");

            final ResponseEntity<MpesaResponseParameter> response = authorizationHttpCall.sendAPIGatewayPOSTRequest(csb_url,objectMapper.writeValueAsString(mpesaRequestUrl),headers, MpesaResponseParameter.class);

            MpesaResponseParameter mpesaResponseParameter = response.getBody();
            logger.info(response.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void simulateTransaction(){
        ObjectMapper objectMapper = new ObjectMapper();
        executeRegisterUrl();
        try{
            String Access_token = (String) mpesaService.generateToken().get("accessToken");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("authorization", "Bearer " +Access_token);

            MpesaSimulateUrl mpesaSimulateUrl = new MpesaSimulateUrl();
            mpesaSimulateUrl.setShortCode("600435");
            mpesaSimulateUrl.setCommandID("CustomerPayBillOnline");
            mpesaSimulateUrl.setAmount("10000");
            mpesaSimulateUrl.setMsisdn("254708374149");
            mpesaSimulateUrl.setBillRefNumber("MXAF545656445646");

            final ResponseEntity<MpesaResponseParameter> response = authorizationHttpCall.sendAPIGatewayPOSTRequest(simulate_url,objectMapper.writeValueAsString(mpesaSimulateUrl),headers,MpesaResponseParameter.class);

            MpesaResponseParameter mpesaResponseParameter = response.getBody();
            logger.info(response.toString());

        }catch (Exception e){

        }
    }
}
