package com.demo.service.impl;

import com.demo.dao.IncomeDao;
import com.demo.em.PayType;
import com.demo.model.Income;
import com.demo.request.InfoParam;
import com.demo.service.IncomeService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Resource
    private IncomeDao incomeDao;

    /**
     * 获取工资计算总额并且排序
     * @param param
     * @return
     */
    @Override
    public List<Income> getIncomes(InfoParam param) {
        List<Income> resultList = new ArrayList<>();
        Map<String,List<Income>> incomeMap = new HashMap<>();
        List<Income> incomes = incomeDao.findIncomes(param);
        for(Income in : incomes){
            List<Income> incomeList = incomeMap.get(in.getIdCard());
            if(incomeList == null){
                incomeList = new ArrayList<>();
            }
            incomeList.add(in);
            incomeMap.put(in.getIdCard(),incomeList);
        }
        for(String idCard : incomeMap.keySet()){
            List<Income> incomeList = incomeMap.get(idCard);
            resultList.addAll(incomeList);
            resultList.add(compute(incomeList));
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<Income> incomes) {
        for (Income income : incomes) {
            incomeDao.insert(income);
        }
    }

    @Override
    public void exportExcel(InfoParam param, HttpServletRequest request, HttpServletResponse response) {
        List<Income> incomes = getIncomes(param);
        try {
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 25);
            
            createTitle(sheet);

            int rowNum = 1;
            for(Income income : incomes) {
                Row row = sheet.createRow(rowNum);

                Cell cell = row.createCell(0);
                cell.setCellValue(income.getIdCard());

                cell = row.createCell(1);
                cell.setCellValue(income.getRealName());

                cell = row.createCell(2);
                cell.setCellValue(income.getLevel());

                cell = row.createCell(3);
                cell.setCellValue(income.getPay().doubleValue());

                cell = row.createCell(4);
                cell.setCellValue(PayType.getPayTypeByType(income.getType()).getName());

                cell = row.createCell(5);
                cell.setCellValue(income.getTax().doubleValue());

                cell = row.createCell(6);
                cell.setCellValue(income.getDate());

                rowNum++;
            }

            export(wb, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTitle(Sheet sheet) {

    }

    @Override
    public void downTemplate(HttpServletResponse response) {
        BufferedInputStream  bf = null;
        try {

            File  file = ResourceUtils.getFile("classpath:demo.xlsx");
            if(file.exists()){
                response.setHeader("content-type","application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("content-length", String.valueOf(file.length()));
                response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(file.getName(),"UTF-8"));
                byte[] buffer = new byte[1024];
                bf = new BufferedInputStream(new FileInputStream(file));
                OutputStream os = response.getOutputStream();
                int i = bf.read(buffer);
                while (i != -1){
                    os.write(buffer,0,i);
                    i= bf.read(buffer);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(bf !=null ){
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Income compute(List<Income> incomeList){
        BigDecimal totalPay =new BigDecimal(0);
        BigDecimal totalTax =new BigDecimal(0);
        if(incomeList == null ||incomeList.size() == 0){
            return null;
        }
        for(Income in : incomeList){
            totalPay =  totalPay.add(in.getPay());
            totalTax = totalTax.add(in.getTax());
        }
        Income in = incomeList.get(0);
        Income total = new Income();
        BeanUtils.copyProperties(in,total);
        total.setTax(totalTax);
        total.setPay(totalPay);
        total.setType(PayType.TOTAL.getType());
        return total;
    }

    private void export(Workbook workBook, HttpServletRequest request, HttpServletResponse response) {
        String fileName = DateFormatUtils.format(new Date(), "yyyy_MM_dd HH_mm_ss");
        fileName = processFileName(request, fileName);
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/octet-stream");
//        response.setHeader("content-length", String.valueOf(workBook.ge.length()));
        response.setHeader("Content-Disposition","attachment;filename="+  fileName + ".xlsx");
        try(OutputStream outputStream = response.getOutputStream()) {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processFileName(HttpServletRequest request, String fileName) {
        String codedfilename = fileName;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && agent.contains("MSIE") || null != agent && agent.contains("Trident")) {// ie
                String name = java.net.URLEncoder.encode(fileName, "UTF8");
                codedfilename = name.replace("+", "%20");
            } else if (null != agent && agent.contains("Mozilla")) {// 火狐,chrome等
                codedfilename = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }
}