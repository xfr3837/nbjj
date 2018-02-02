package com.xfr.nbjj;

import android.widget.Toast;

import com.xfr.nbjj.students.Student;
import com.xfr.nbjj.teachers.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 这个类用来搜素
 * @author 徐斐然
 */

public class Searcher {

    /**
     * 向这个方法传入搜索所需条件，返回网页中一页 teacher
     * 一般来说一页有十个，但是条件过于苛刻或者在最后一页的时候可能会少于十个
     * 如果不设置某项条件，要将这个参数设为空字符 ""
     * @return List<Teacher>
     */

    // TODO 明星教员、有照片这两个搜索条件还未涵盖
    // TODO 搜索条件应为 url encoding
    public static List<Teacher> getOnePageTeachersInfo(OkHttpClient client, String searchPage,
                                                       String searchNum, String searchMajor,
                                                       String searchSubject,
                                                       String searchLocation,
                                                       String searchUniversity,
                                                       String searchSex,
                                                       String searchEducation) {

        String url = "http://www.ningbojiajiao.com/Teacher.asp?" +
                "page=" + searchPage +
                "&num=" + searchNum +
                "&zy=" + searchMajor +
                "&km=" + searchSubject +
                "&skdq=" + searchLocation +
                "&xx=" + searchUniversity +
                "&sex=" + searchSex +
                "&xl=" + searchEducation;

        String number, name, sex, education, subjects, personalIntroduction, photo, time;

        List<Teacher> teachers = new ArrayList<>();

        Document document = getDocument(client, url);

        try {
            // 找到所有带有 onmouseout 属性的元素
            Elements elements = document.getElementsByAttribute("onmouseout");
            for (Element element : elements) {

                // 编号
                number = element.getElementsByClass("red_b_14").text();

                // 姓名 性别
                int i = 0;
                String teacherNameSex = element.getElementsByAttributeValue("width", "10%").get(0).text();
                while (i < teacherNameSex.length()) {
                    if (teacherNameSex.charAt(i) == '.')
                        break;
                    i ++;
                }

                // 姓名
                StringBuilder nameStringBuilder = new StringBuilder(teacherNameSex);
                name = nameStringBuilder.replace(i, teacherNameSex.length(), "").toString();

                // 性别
                StringBuilder sexStringBuilder = new StringBuilder(teacherNameSex);
                sex = sexStringBuilder.replace(0, teacherNameSex.length()-1, "").toString();

                // 学历
                education = element.getElementsByAttributeValue("width", "14%").text();

                // 课程
                subjects = element.getElementsByAttributeValue("width", "24%").get(0).text();

                // 介绍
                personalIntroduction = element.getElementsByAttributeValue("width", "24%").get(1).text();
                StringBuilder personalIntroductionStringBuilder = new StringBuilder(personalIntroduction);

                int watchPhotoIndex = personalIntroductionStringBuilder.indexOf(" [查看照片]");
                if (watchPhotoIndex != -1)
                    personalIntroductionStringBuilder = personalIntroductionStringBuilder
                            .replace(watchPhotoIndex, personalIntroductionStringBuilder.length(), "");

                personalIntroduction = personalIntroductionStringBuilder.toString();

                // 照片
                photo = element.getElementsByAttributeValue("width", "24%").select("a").attr("href");

                // 时间
                time = element.getElementsByAttributeValue("width", "9%").get(1).text();

                teachers.add(new Teacher(number, name, sex, education, subjects, personalIntroduction, photo, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }

    /**
     * 向这个方法传入搜索所需条件，返回网站中一页 student
     * 一般来说一页有十个，但是条件过于苛刻或者在最后一页的时候可能会少于十个
     * 如果不设置某项条件，要将这个参数设为空字符 ""
     * @return List<Student>
     */

    // TODO 搜索条件应为 url encoding
    public static List<Student> getOnePageStudentsInfo(OkHttpClient client, String searchPage,
                                                       String searchNum, String searchSubject,
                                                       String searchLocation,
                                                       String searchStudentSex,
                                                       String searchTeacherSex,
                                                       String searchStatus) {

        // 根据参数构造出 url
        String url = "http://www.ningbojiajiao.com/Student.asp?page=" + searchPage + "&num=" +
                searchNum + "&km=" + searchSubject + "&dq=" + searchLocation + "&sex=" +
                searchStudentSex + "&sex2=" + searchTeacherSex + "&zt=" + searchStatus;

        // 搜索页面每个学生的信息
        // 编号、置顶、年级、性别、学科、性别要求、具体要求、位置、状态、时间、
        String number, top, grade, sex, subject, sexRequire, specificRequire, location,
                status = null, time = null;

        List<Student> students = new ArrayList<Student>();

        Document document = getDocument(client, url);

        try {

            // 找到所有带有 onmouseout 属性的元素
            Elements elements = document.getElementsByAttribute("onmouseout");

            for (Element element : elements) {

                // 编号
                number = element.getElementsByClass("red_b_14").text();

                //置顶
                top = element.getElementsByClass("ZhiDing").text();
                if (top != "推荐信息")
                    top = "不置顶";

                // 年级、性别、科目
                // TODO 用 indexOf() 更方便，写的时候不知道
                int i = 0;
                String studentGradeSexCourse = element.getElementsByAttributeValue("width", "12%").text();
                while (i < studentGradeSexCourse.length()) {
                    if (studentGradeSexCourse.charAt(i) == '.')
                        break;
                    i ++;
                }

                // 年级
                StringBuilder gradeStringBuilder = new StringBuilder(studentGradeSexCourse);
                grade = gradeStringBuilder.replace(i, studentGradeSexCourse.length(), "").toString();

                // 性别
                StringBuilder sexStringBuilder = new StringBuilder(studentGradeSexCourse);
                sex = sexStringBuilder.replace(i+2, studentGradeSexCourse.length(), "")
                        .replace(0, i+1, "").toString();

                // 科目
                StringBuilder subjectStringBuilder = new StringBuilder(studentGradeSexCourse);
                subject = subjectStringBuilder.replace(0, i+3, "").toString();

                // 性别要求
                sexRequire = element.getElementsByAttributeValue("color", "#993300").text();

                // 具体要求
                // TODO 用 indexOf() 更方便，写的时候不知道
                specificRequire = element.getElementsByAttributeValue("width", "38%").text();
                int j = 0;
                while (j < specificRequire.length()) {
                    if (specificRequire.charAt(j) == ' ')
                        break;
                    j ++;
                }
                StringBuilder specificRequireStringBuilder = new StringBuilder(specificRequire);
                specificRequireStringBuilder.replace(0, j, "");
                specificRequire = specificRequireStringBuilder.toString();

                // 地址
                location = element.getElementsByAttributeValue("width", "14%").text();

                // 时间
                time = element.getElementsByAttributeValue("width", "10%").text();
                StringBuilder timeStringBuilder = new StringBuilder(time);
                timeStringBuilder.replace(timeStringBuilder.length()-6, timeStringBuilder.length(), "");
                time = timeStringBuilder.toString();

                // 状态
                status = element.getElementsByAttributeValue("width", "8%").get(1).text();
                if (status.equals(""))
                    status = "新家教";

                // 生成对应的对象然后存入 List
                students.add(new Student(number, top, grade, sex, subject,
                        sexRequire, specificRequire, location, status, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


    private static Document getDocument (OkHttpClient client, String url) {

        try {

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            // 以二进制流的形式读入数据， 以便使用 GBK 解码
            byte[] responseBytes = response.body().bytes();
            // 使用 GBK 解码，否则乱码
            String responseData = new String(responseBytes, "GBK");
            Document document = Jsoup.parse(responseData);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
