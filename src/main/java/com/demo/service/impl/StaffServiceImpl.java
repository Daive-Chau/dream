package com.demo.service.impl;

import com.demo.dao.StaffDao;
import com.demo.model.Staff;
import com.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDao staffDao;
    @Override
    public List<Staff> selectAll() {
        return staffDao.selectAllStaffs();
    }

    @Override
    public boolean checkUser(String username,String password) {
        Staff staff = staffDao.selectByUsername(username);
        if(staff != null && staff.getPassword().equals(password)){
            return true;
        }
        return false;
    }


}
