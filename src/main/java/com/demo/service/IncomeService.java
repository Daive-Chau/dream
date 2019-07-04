package com.demo.service;

import com.demo.model.Income;
import com.demo.request.InfoParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IncomeService {
    List<Income> getIncomes(InfoParam param);

    void batchSave(List<Income> incomes);

    void exportExcel(InfoParam param, HttpServletRequest request, HttpServletResponse response);

    void downTemplate(HttpServletResponse response);
}
