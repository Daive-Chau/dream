package com.demo.service.impl;

import com.demo.em.PayType;
import com.demo.exception.BusinessException;
import com.demo.model.Income;
import com.demo.service.FileService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<Income> readExcel(InputStream inputStream) {
        try {
            List<Income> result = new ArrayList<>();
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum() + 1;
            int lastRowIndex = sheet.getLastRowNum();
            for(int rIndex = firstRowIndex ;rIndex <=lastRowIndex;rIndex ++){
                Row row = sheet.getRow(rIndex);
                if(row !=null){
                    Income income = new Income();

                    String idCard = getValue(row.getCell(0));
                    if(StringUtils.isEmpty(idCard)) {
                        throw new BusinessException("身份证号不能为空");
                    }
                    income.setIdCard(idCard);

                    String realName = getValue(row.getCell(1));
                    if(StringUtils.isEmpty(realName)) {
                        throw new BusinessException("姓名不能为空");
                    }
                    income.setRealName(realName);

                    String level = getValue(row.getCell(2));
                    if(StringUtils.isEmpty(level)) {
                        throw new BusinessException("职级不能为空");
                    }
                    income.setLevel(level);

                    String pay = getValue(row.getCell(3));
                    if(!NumberUtils.isParsable(pay)) {
                        throw new BusinessException("工资格式有误");
                    }
                    income.setPay(new BigDecimal(pay));

                    String type = getValue(row.getCell(4));
                    income.setType(PayType.getPayTypeByName(type).getType());

                    String tax = getValue(row.getCell(5));
                    if(NumberUtils.isParsable(tax)) {
                        income.setTax(new BigDecimal(tax));
                    }

                    String dateStr = getValue(row.getCell(6));
                    try {
                        Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
                        income.setDate(date);
                    } catch (ParseException e) {
                        throw new BusinessException("日期格式不合法");
                    }
                    result.add(income);
                }
            }

            return result;
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private String getValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        String value;
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                Date date = cell.getDateCellValue();
                return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
            } else {// 避免产生科学计数法
                BigDecimal numericCellValue = new BigDecimal(cell.getNumericCellValue());
                value = numericCellValue.toString();
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            value = String.valueOf(cell.getStringCellValue());
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            value = String.valueOf(cell.getStringCellValue());
        }

        return value;
    }
}
