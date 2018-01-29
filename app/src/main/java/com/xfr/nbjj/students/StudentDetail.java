package com.xfr.nbjj.students;

/**
 * Created by xfr on 2018/1/27.
 */

public class StudentDetail extends Student {
    // 学生详情信息中展示的属性：报酬、授课时间、学员描述
    private String salary, teachTime, describe;

    public StudentDetail() {

    }

    public StudentDetail(String number, String top, String grade, String sex, String course,
                         String sexRequire, String specificRequire, String location,
                         String status, String time, String salary, String teachTime, String describe) {
        super(number, top, grade, sex, course, sexRequire, specificRequire, location, status, time);
        this.salary = salary;
        this.teachTime = teachTime;
        this.describe = describe;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSalary() {
        return salary;
    }

    public void setTeachTime(String teachTime) {
        this.teachTime = teachTime;
    }

    public String getTeachTime() {
        return teachTime;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }
}

