package com.pms.model;

public class PersonnelChange {
    private int id;
    private int Employeeid;
    private String Employeename;
    private String changeType;
    private String description;
    private String changeTime;

    public PersonnelChange() {
    }
    public PersonnelChange(int id, int employeeid, String name, String change, String description) {
        this.id = id;
        this.Employeeid = employeeid;
        this.Employeename = name;
        this.changeType = change;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return Employeeid;
    }

    public void setEmployeeId(int id) {
        this.Employeeid = id;
    }

    public String getEmployeeName() {
        return Employeename;
    }

    public void setEmployeeName(String name) {
        this.Employeename = name;
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

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
