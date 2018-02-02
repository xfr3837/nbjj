package com.xfr.nbjj.teachers;

/**
 * Created by xfr on 2018/1/27
 */
public class Teacher {
    // 编号、姓名、性别、学历、可教授课程、个人介绍、是否有照片、登录时间
    private String number, name, sex, education, subjects, personalIntroduction, photo,time;

    public Teacher(String number, String name, String sex, String education, String subjects,
                   String personalIntroduction, String photo, String time) {
        this.number = number;
        this.name = name;
        this.sex = sex;
        this.education = education;
        this.subjects = subjects;
        this.personalIntroduction = personalIntroduction;
        this.photo = photo;
        this.time = time;
    }

    @Override
    public String toString() {
        return "number:" + number + "\n" +
                "name:" + name + "\n" +
                "sex:" + sex + "\n" +
                "education:" + education + "\n" +
                "subjects:" + subjects + "\n" +
                "personalIntroduction:" + personalIntroduction + "\n" +
                "photo:" + photo + "\n" +
                "time:" + time + "\n";
    }
}