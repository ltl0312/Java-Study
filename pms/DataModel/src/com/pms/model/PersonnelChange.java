package com.pms.model;

import java.sql.Date;
import java.sql.Timestamp;

public class PersonnelChange {
    private int id;
    private int EmployeeId;
    private String EmployeeName;
    private String changeType;
    private String description;
    private Timestamp changeTime;
    private int oldDepartmentId; // 仅用于调岗
    private int newDepartmentId; // 仅用于调岗
    private int oldJobCode; // 仅用于调岗
    private int newJobCode; // 仅用于调岗

    public PersonnelChange() {
    }
    public PersonnelChange(int id, int employeeid, String name, String change, String description) {
        this.id = id;
        this.EmployeeId = employeeid;
        this.EmployeeName = name;
        this.changeType = change;
        this.description = description;
    }
    public PersonnelChange(int employeeId, String employeeName, String changeType,
                           String description, Timestamp changeTime) {
        this.EmployeeId = employeeId;
        this.EmployeeName = employeeName;
        this.changeType = changeType;
        this.description = description;
        this.changeTime = changeTime;
    }

    // 调岗专用构造方法
    public PersonnelChange(int employeeId, String employeeName, String changeType,
                           String description, Timestamp changeTime,
                           int oldDepartmentId, int newDepartmentId,
                           int oldJobCode, int newJobCode) {
        this(employeeId, employeeName, changeType, description, changeTime);
        this.oldDepartmentId = oldDepartmentId;
        this.newDepartmentId = newDepartmentId;
        this.oldJobCode = oldJobCode;
        this.newJobCode = newJobCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int id) {
        this.EmployeeId = id;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String name) {
        this.EmployeeName = name;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String change) {
        this.changeType = change;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }
    public void setChangeTime(Timestamp changeTime) {
        this.changeTime = changeTime;
    }

    public int getOldDepartmentId() {
        return oldDepartmentId;
    }

    public void setOldDepartmentId(int oldDepartmentId) {
        this.oldDepartmentId = oldDepartmentId;
    }

    public int getNewDepartmentId() {
        return newDepartmentId;
    }

    public void setNewDepartmentId(int newDepartmentId) {
        this.newDepartmentId = newDepartmentId;
    }

    public int getOldJobCode() {
        return oldJobCode;
    }

    public void setOldJobCode(int oldJobCode) {
        this.oldJobCode = oldJobCode;
    }

    public int getNewJobCode() {
        return newJobCode;
    }

    public void setNewJobCode(int newJobCode) {
        this.newJobCode = newJobCode;
    }

}

