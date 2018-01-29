package com.xfr.nbjj.students;

/**
 * Created by xfr on 2018/1/27.
 */

public class Student {
    // 学生列表网页中展示的属性：编号、置顶、年级、性别、学科、性别要求、具体要求、位置、状态、时间
    private String number, top, grade, sex, subject, sexRequire, specificRequire, location, status, time;


    public Student() {

    }

    public Student(String number, String top, String grade, String sex, String subject,
                   String sexRequire, String specificRequire, String location,
                   String status, String time) {
        this.number = number;
        this.top = top;
        this.grade = grade;
        this.sex = sex;
        this.subject = subject;
        this.sexRequire = sexRequire;
        this.specificRequire = specificRequire;
        this.location = location;
        this.status = status;
        this.time = time;
    }

    @Override
    public String toString() {
        return "number:" + number + "\n" +
                "top:" + top + "\n" +
                "grade:" + grade +"\n" +
                "sex:" + sex + "\n" +
                "course:" + subject + "\n" +
                "sexRequire:" + sexRequire + "\n" +
                "specificRequire:" + specificRequire + "\n" +
                "location:" + location + "\n" +
                "status:" + status + "\n" +
                "time:" + time + "\n";
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getTop() {
        return top;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSubject(String course) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSexRequire(String sexRequire) {
        this.sexRequire = sexRequire;
    }

    public String getSexRequire() {
        return sexRequire;
    }

    public void setSpecificRequire(String specificRequire) {
        this.specificRequire = specificRequire;
    }

    public String getSpecificRequire() {
        return specificRequire;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }



}
