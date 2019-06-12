package com.tracom.validator.MpesaApi;

import com.tracom.validator.MpesaApi.MpesaClass.MPESAValidationCallbackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MpesaController {

    public static final Logger logger = LoggerFactory.getLogger(MpesaController.class);

    @Autowired
    public MpesaC2BService mpesaC2BServicel;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(){
        mpesaC2BServicel.executeRegisterUrl();
        return  new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PostMapping(value = "/transact")
    public ResponseEntity<Object> simulateTransaction(){
        mpesaC2BServicel.simulateTransaction();
        return  new ResponseEntity<>(null,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/c2b/callback/validation", method = RequestMethod.POST)
    public ResponseEntity<?> MPESAC2BValidation(@RequestBody MPESAValidationCallbackResponse response) {

        logger.info("---------------Mpesa validator----------------");
        logger.info(response.toString());
        logger.info("log:vbdfhfdhfdhfdh " + response.getFirstName());
        logger.info("validator: " + response.toString());
        Map<String,Object> res = new HashMap<>();
        if (response != null) {
            res.put("ResultCode",1);
            res.put("ResultDesc","Failed");
            return ResponseEntity.ok(res);
        } else {
            res.put("ResultCode",0);
            res.put("ResultDesc","Success");
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "/api/v1/c2b/callback/confirmation", method = RequestMethod.POST)
    public ResponseEntity<?> MPESAC2BConfirmation(@RequestBody MPESAValidationCallbackResponse  response) {

        logger.info("---------------Mpesa----------------");
        logger.info(response.toString());

        logger.info("log: " + response.toString());

        return null;
    }
}
