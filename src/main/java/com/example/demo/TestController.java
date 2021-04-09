package com.example.demo;

import com.example.demo.mapper.UerMaper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
