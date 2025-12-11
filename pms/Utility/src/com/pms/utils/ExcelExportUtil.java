package com.pms.utils;

import com.pms.model.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel导出工具类，用于导出员工信息报表
 */
public class ExcelExportUtil {

    /**
     * 导出员工信息到Excel文件
     * @param employees 员工列表
     * @param filePath 导出文件路径
     */
    public static void exportEmployeeReport(List<Employee> employees, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建报表工作表
            Sheet sheet = workbook.createSheet("员工信息报表");
            
            // 设置单元格样式
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle statisticStyle = createStatisticStyle(workbook);
            
            // 按部门对员工进行分组
            Map<String, List<Employee>> departmentMap = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartmentName));
            
            // 1. 添加报表标题
            int currentRowIndex = 0;
            Row titleRow = sheet.createRow(currentRowIndex++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("员工信息报表");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 9));
            
            // 2. 添加报表日期
            Row dateRow = sheet.createRow(currentRowIndex++);
            Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue("导出日期: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            dateCell.setCellStyle(dataStyle);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 9));
            
            // 3. 添加空行
            currentRowIndex++;
            
            // 4. 遍历每个部门
            for (Map.Entry<String, List<Employee>> entry : departmentMap.entrySet()) {
                String departmentName = entry.getKey();
                List<Employee> deptEmployees = entry.getValue();
                
                // 计算部门统计信息
                long activeCount = deptEmployees.stream()
                        .filter(emp -> 't' == emp.getState()).count();
                long resignedCount = deptEmployees.stream()
                        .filter(emp -> 'f' == emp.getState()).count();
                
                // 4.1 添加部门标题
                Row deptTitleRow = sheet.createRow(currentRowIndex++);
                Cell deptTitleCell = deptTitleRow.createCell(0);
                deptTitleCell.setCellValue(departmentName);
                deptTitleCell.setCellStyle(titleStyle);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(currentRowIndex-1, currentRowIndex-1, 0, 9));
                
                // 4.2 添加部门统计信息
                Row deptStatRow = sheet.createRow(currentRowIndex++);
                deptStatRow.createCell(0).setCellValue("部门统计");
                deptStatRow.createCell(1).setCellValue("总人数: " + deptEmployees.size());
                deptStatRow.createCell(2).setCellValue("在职人数: " + activeCount);
                deptStatRow.createCell(3).setCellValue("离职人数: " + resignedCount);
                for (int i = 0; i < 4; i++) {
                    deptStatRow.getCell(i).setCellStyle(statisticStyle);
                }
                
                // 4.3 添加部门员工表头
                Row headerRow = sheet.createRow(currentRowIndex++);
                String[] headers = {"员工ID", "姓名", "性别", "职务", "学历", "联系电话", "邮箱", "地址", "出生日期", "状态"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }
                
                // 4.4 添加部门员工数据
                for (Employee emp : deptEmployees) {
                    Row dataRow = sheet.createRow(currentRowIndex++);
                    
                    dataRow.createCell(0).setCellValue(emp.getId());
                    dataRow.createCell(1).setCellValue(emp.getName());
                    dataRow.createCell(2).setCellValue(emp.getSex());
                    dataRow.createCell(3).setCellValue(emp.getJobName());
                    dataRow.createCell(4).setCellValue(emp.getEduLevelName());
                    dataRow.createCell(5).setCellValue(emp.getTel());
                    dataRow.createCell(6).setCellValue(emp.getEmail());
                    dataRow.createCell(7).setCellValue(emp.getAddress());
                    
                    // 格式化日期
                    if (emp.getBirthday() != null) {
                        dataRow.createCell(8).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(emp.getBirthday()));
                    } else {
                        dataRow.createCell(8).setCellValue("");
                    }
                    
                    dataRow.createCell(9).setCellValue('t' == emp.getState() ? "在职" : "离职");
                    
                    // 设置数据行样式
                    for (int i = 0; i < 10; i++) {
                        dataRow.getCell(i).setCellStyle(dataStyle);
                    }
                }
                
                // 在每个部门后添加空行
                currentRowIndex++;
            }
            
            // 5. 自动调整列宽
            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i);
                // 增加一些额外宽度
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 256 * 2);
            }
            
            // 6. 保存文件
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("导出Excel文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建标题样式
     */
    private static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
    
    /**
     * 创建表头样式
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
    
    /**
     * 创建数据样式
     */
    private static CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
    
    /**
     * 创建统计信息样式
     */
    private static CellStyle createStatisticStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
}
