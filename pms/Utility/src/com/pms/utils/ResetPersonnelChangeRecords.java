package com.pms.utils;

import com.pms.service.PersonnelChangeService;

public class ResetPersonnelChangeRecords {
    public static void main(String[] args) {
        PersonnelChangeService changeService = new PersonnelChangeService();
        
        System.out.println("开始重置人事变动记录...");
        
        // 删除所有人事变动记录
        boolean deleteSuccess = changeService.deleteAllChanges();
        if (deleteSuccess) {
            System.out.println("成功删除所有人事变动记录");
        } else {
            System.out.println("删除人事变动记录失败");
            return;
        }
        
        // 重置人事变动记录的ID自增
        boolean resetSuccess = changeService.resetAutoIncrement();
        if (resetSuccess) {
            System.out.println("成功重置人事变动记录的ID自增，从1开始");
        } else {
            System.out.println("重置人事变动记录ID自增失败");
            return;
        }
        
        System.out.println("人事变动记录重置完成！");
    }
}