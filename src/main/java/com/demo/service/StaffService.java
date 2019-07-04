package com.demo.service;

import com.demo.model.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> selectAll();

    boolean checkUser(String username,String password);
}
