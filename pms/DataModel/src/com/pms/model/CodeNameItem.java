package com.pms.model;

/**
 * 下拉框选项的通用封装类（值-文本对）
 */
public class CodeNameItem {
    // 存储实际值（如部门ID、职位编码）
    private int code;
    // 存储显示文本（如部门名称、职位名称）
    private String name;

    // 构造方法
    public CodeNameItem(int code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getter 和 Setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 重写toString，下拉框会显示该方法返回的文本
    @Override
    public String toString() {
        return name;
    }

    // 可选：重写equals和hashCode，避免下拉框选值异常
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeNameItem that = (CodeNameItem) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return code;
    }
}