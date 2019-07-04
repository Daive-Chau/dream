package com.demo.dao;

import com.demo.model.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffDao {
    List<Staff> selectAllStaffs();

    Staff selectByUsername(@Param("username") String username);
}
