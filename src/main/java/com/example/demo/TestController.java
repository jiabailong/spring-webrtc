package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.mapper.UerMaper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private UerMaper userMappper;

    @GetMapping("")
    public  String getName(){
        List<User> list=userMappper.selectAll();
        System.out.println(Arrays.toString(list.toArray()));


        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .serializeNulls()
                .create();
        JsonElement element =
                gson.toJsonTree(list , new TypeToken<List<User>>() {}.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        return jsonArray.toString();
    }
    @GetMapping("user")
    public  int adduser(){
        User user=new User();
        user.setName("oppo");
        user.setPhone("123456789");
        userMappper.addUser(user);
        return 1;
    }
    @GetMapping("login")
    public  String login(@RequestParam String name,@RequestParam String pwd){
        System.out.println(name+"==="+pwd);

        User user=new User();
        user.setName(name);
        user.setPhone(pwd);
        List<User>list=userMappper.selectUser(user);
        if(list==null||list.size()==0){
            return"no user";
        }
        Algorithm algorithm=Algorithm.HMAC256("109800099877");
        String token=JWT.create().withAudience("ATI").withClaim("name",name).sign(algorithm);
        return token;
    }
    @GetMapping("test")
    public  String veryToken(@RequestParam String token){
        Algorithm algorithm=Algorithm.HMAC256("109800099877");
        DecodedJWT decodedJWT=null;
        try {
         decodedJWT = JWT.require(algorithm).build().verify(token);

        }catch (SignatureVerificationException e){
            e.printStackTrace();
        }
        if(decodedJWT==null){
            return "验证失败";
        }
        System.out.println("getHeader=="+decodedJWT.getHeader());
        System.out.println("getPayload=="+decodedJWT.getPayload());
        System.out.println("claim=="+decodedJWT.getClaims().get("name"));

        return decodedJWT.getClaims().get("name").asString();
    }
}
