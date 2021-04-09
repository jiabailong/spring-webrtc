package com.example.demo.mapper;

import com.example.demo.User;

import java.util.List;

public interface UerMaper {
    public  List<User> selectAll();
    public  int addUser(User user);
    public  List<User> selectUser(User user);

}
