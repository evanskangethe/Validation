package com.tracom.validator.ApplicationUser;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private JSONObject jsonObject;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Object> signup (@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            jsonObject.put("valid",false);
            jsonObject.put("message",errors);

            return new ResponseEntity<>(jsonObject,HttpStatus.OK);
        }
        userRepository.save(user);
        jsonObject.put("valid",true);
        jsonObject.put("message","User Created successfully");
        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login (@RequestBody User user){
        User verify = userRepository.findUserByUsername(user.getUsername());

        if( verify != null || verify.getPassword() == user.getPassword()) {
            Map<String, String> userDetail = new HashMap<>();
            userDetail.put("username", verify.getUsername());
            userDetail.put("Email", verify.getEmail());
            jsonObject.put("valid", true);
            jsonObject.put("message", userDetail);
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        jsonObject.put("valid",false);
        jsonObject.put("message","Please enter a valid username and password");
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

}
