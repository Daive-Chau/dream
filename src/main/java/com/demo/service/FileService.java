package com.demo.service;

import com.demo.model.Income;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    List<Income> readExcel(InputStream inputStream);
}
