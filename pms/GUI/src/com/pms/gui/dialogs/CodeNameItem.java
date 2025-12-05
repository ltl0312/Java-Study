package com.pms.gui.dialogs;

/**
 * 用于存储代码与名称的映射关系（部门/职位/学历通用）
 */
public class CodeNameItem {
    private int code;       // 对应数据库中的代码（int类型）
    private String name;    // 显示用的名称

    public CodeNameItem(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 下拉框显示名称
    @Override
    public String toString() {
        return name;
    }
}