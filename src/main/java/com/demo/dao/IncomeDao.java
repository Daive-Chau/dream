package com.demo.dao;

import com.demo.model.Income;
import com.demo.request.InfoParam;


import java.util.List;

public interface IncomeDao {

    List<Income> findIncomes(InfoParam infoParam);

    void insert(Income income);
}
