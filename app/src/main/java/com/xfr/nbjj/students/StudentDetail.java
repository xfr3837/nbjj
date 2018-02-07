package com.xfr.nbjj.students;

/**
 * Created by xfr on 2018/1/27
 */
public class StudentDetail {
    // 学生详情信息中展示的属性：报酬、授课时间、学员描述
    private String status, time, number, location, sex, grade, subject, salary, teachTime, studentDescribe, teacherRequire;

    public StudentDetail() {}

    public StudentDetail(String status, String time, String number, String location, String sex,
                         String grade, String subject, String salary, String teachTime,
                         String studentDescribe, String teacherRequire) {
        this.status = status;
        this.time = time;
        this.number = number;
        this.location = location;
        this.sex = sex;
        this.grade = grade;
        this.subject = subject;
        this.salary = salary;
        this.teachTime = teachTime;
        this.studentDescribe = studentDescribe;
        this.teacherRequire = teacherRequire;
    }

    @Override
    public String toString() {
        return "家教信息状态：" + getStatus() + "\n" +
                "家教发布时间：" + getTime() + "\n" +
                "家教信息编号：" + getNumber() + "\n" +
                "所在区域：" + getLocation() + "\n" +
                "学员性别：" + getSex() + "\n" +
                "学员年级身份：" + getGrade() + "\n" +
                "求教学科：" + getSubject() + "\n" +
                "家教老师报酬：" + getSalary() + "\n" +
                "授课时间安排：" + getTeachTime() + "\n" +
                "学员情况描述：" + getStudentDescribe() + "\n" +
                "对教员的要求：" + getTeacherRequire() + "\n";
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getNumber() {
        return number;
    }

    public String getLocation() {
        return location;
    }

    public String getSex() {
        return sex;
    }

    public String getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }


    public String getSalary() {
        return salary;
    }

    public String getTeachTime() {
        return teachTime;
    }

    public String getStudentDescribe() {
        return studentDescribe;
    }

    public String getTeacherRequire() {
        return teacherRequire;
    }
}
