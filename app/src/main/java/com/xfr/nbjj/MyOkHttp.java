package com.xfr.nbjj;


import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.xfr.nbjj.students.Student;
import com.xfr.nbjj.students.StudentDetail;
import com.xfr.nbjj.teachers.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 这个类用来搜素
 * @author 徐斐然
 */

public class MyOkHttp {

    public static int login(String name, String passWord) {
        // 0: 成功
        // 1: 账号或密码错误
        // 2: 网络超时
        // 3: 其他错误
        String cookies, cookie_ASPSESSIONIDSXXXXXXX, cookie_pwd, cookie_CityID, cookie_HZJJ8User, cookie_lx, cookie_id,
                cookie_ASPNET_SessionId, cookie_JIAJIAOBA;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        // request1: 第一个 GET, 获取 ASPSESSIONIDSXXXXXXX
        Request request1 = new Request.Builder()
                .url("http://www.ningbojiajiao.com/m/")
                .build();
        try {
            name = URLEncoder.encode(name, "gbk");
            passWord = URLEncoder.encode(passWord, "gbk");
            // response1:第一次 GET 获取 ASPSESSIONIDSXXXXXXX 时的 response
            Response response1 = client.newCall(request1).execute();
            cookie_ASPSESSIONIDSXXXXXXX = response1.header("Set-Cookie").split(" ")[0];

            RequestBody body = new FormBody.Builder()
                    .addEncoded("name", name)
                    .addEncoded("password", passWord)
                    .build();

            // request2: POST
            Request request2 = new Request.Builder()
                    .url("http://www.ningbojiajiao.com/m/m_Login.asp?action=save")
                    .header("Cookie", cookie_ASPSESSIONIDSXXXXXXX)
                    .post(body)
                    .build();
            // response2: POST 返回的 response
            Response response2 = client.newCall(request2).execute();
            cookie_pwd = response2.headers("Set-Cookie").get(0).split(" ")[0];
            cookie_CityID = response2.headers("Set-Cookie").get(1).split(" ")[0];
            cookie_HZJJ8User = response2.headers("Set-Cookie").get(2).split(" ")[0];
            cookie_lx = response2.headers("Set-Cookie").get(3).split(" ")[0];
            cookie_id = response2.headers("Set-Cookie").get(4).split(" ")[0];

            // request3: POST 之后第一个重定向
            Request request3 = new Request.Builder()
                    .url("http://www.ningbojiajiao.com" + response2.header("Location"))
                    .header("Cookie", cookie_ASPSESSIONIDSXXXXXXX)
                    .build();
            // response3: POST 之后第一个重定向的 response
            Response response3 = client.newCall(request3).execute();

            cookie_ASPNET_SessionId = response3.headers("Set-Cookie").get(0).split(" ")[0];
            cookie_JIAJIAOBA = response3.headers("Set-Cookie").get(1).split(" ")[0];

            cookies = cookie_ASPSESSIONIDSXXXXXXX + " " + cookie_pwd + " " + cookie_CityID + " " + cookie_HZJJ8User
                    + " " + cookie_lx + " " + cookie_id + " " + cookie_ASPNET_SessionId + " " + cookie_JIAJIAOBA;
            Request request4 = new Request.Builder()
                    .url("http://www.ningbojiajiao.com/m/Login_Succeed.asp")
                    .header("Cookie", cookies)
                    .build();
            Response response4 = client.newCall(request4).execute();
            // 以二进制流的形式读入数据， 以便使用 GBK 解码
            byte[] responseBytes = response4.body().bytes();
            // 使用 GBK 解码，否则乱码
            String responseData = new String(responseBytes, "gb2312");
            Document document = Jsoup.parse(responseData);

            if (document.getElementsByAttributeValue("id", "ContentTitle").text().isEmpty())
                return 3;
            else
                return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    public static StudentDetail getStudentDetailInfo(Context context, String pageNumber) {
        String url, status, time, number, location, sex, grade, subject, salary, teachTime, studentDescribe, teacherRequire;

        url = "http://www.ningbojiajiao.com/student/" + pageNumber + ".html";

        // 据说这样可以自动化处理 cookies
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(context));
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
        Document document = getDocument(client, url);

        try {
            Elements elements = document.getElementsByAttributeValue("align", "left");
            status = elements.get(0).text();
            status = new StringBuilder(status)
                    .replace(status.length() - 16, status.length(), "").toString();
            time = elements.get(1).text();
            number = elements.get(2).text();
            location = elements.get(3).text();
            location = new StringBuilder(location)
                    .replace(location.length() - 9, location.length(), "").toString();
            sex = elements.get(4).text();
            grade = elements.get(5).text();
            subject = elements.get(6).text();
            salary = elements.get(7).text();
            salary = new StringBuilder(salary)
                    .replace(salary.length() - 12, salary.length(), "").toString();
            teachTime = elements.get(8).text();
            studentDescribe = elements.get(9).text();
            teacherRequire = document.getElementsByAttributeValue("colspan", "2").get(2).text();
            return new StudentDetail(status, time, number, location, sex, grade, subject, salary, teachTime, studentDescribe, teacherRequire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StudentDetail();

    }

    /**
     * 向这个方法传入搜索所需条件，返回网页中一页 teacher
     * 一般来说一页有十个，但是条件过于苛刻或者在最后一页的时候可能会少于十个
     * 如果不设置某项条件，要将这个参数设为空字符 ""
     * @return List<Teacher>
     */

    // TODO 明星教员、有照片这两个搜索条件还未涵盖
    public static List<Teacher> getOnePageTeachersInfo(Context context, String url) {

        // 据说这样可以自动化处理 cookies
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(context));
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        // 搜索所得网页中老师的各个属性，用于 Teacher 的构造函数
        //编号、姓名、性别、身份(学历)、科目、自我介绍、照片、时间
        String number, name, sex, star, identity, subjects, personalIntroduction, photo, time;

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
                sex = sexStringBuilder.replace(0, i+1, "")
                        .replace(1, sexStringBuilder.length(), "").toString();


                // 如果是明星教员，star.equals("明星教员") 为真
                // 否则 star.equals("") 为真
                star = element.getElementsByAttributeValue("id", "star").next().text();

                // 身份(学历)
                identity = element.getElementsByAttributeValue("width", "14%").text();

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

                // 如果存在照片， 则 photo 是地址
                // 如果不存在照片， photo.equals("") 为真
                photo = element.getElementsByAttributeValue("width", "24%").select("a").attr("href");

                // 时间
                time = element.getElementsByAttributeValue("width", "9%").get(1).text();

                teachers.add(new Teacher(number, name, sex, star, identity, subjects, personalIntroduction, photo, time));
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

    public static List<Student> getOnePageStudentsInfo(Context context, String url) {

        // 据说这样可以自动化处理 cookies
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(context));
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();



        // 搜索页面每个学生的信息，用于 Student 的构造函数
        // 编号、置顶、年级、性别、学科、性别要求、具体要求、位置、状态、时间、
        String number, top, grade, sex, subject, sexRequire, specificRequire, location, status, time;

        List<Student> students = new ArrayList<Student>();

        Document document = getDocument(client, url);
        //HttpUrl httpUrl = HttpUrl.parse("http://ningbojiajiao.com");
        //cookieJar.saveFromResponse(httpUrl, client);

        try {

            // 找到所有带有 onmouseout 属性的元素
            Elements elements = document.getElementsByAttribute("onmouseout");

            for (Element element : elements) {

                // 编号
                number = element.getElementsByClass("red_b_14").text();

                // 如果置顶， top.equals("推荐信息") 为真
                top = element.getElementsByClass("ZhiDing").text();

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


    // 获取老师 url
    public static String getTeachersURl(String searchPage, String searchNum,
                                        String searchMajor, String searchSubject,
                                        String searchLocation, String searchUniversity,
                                        String searchSex, String searchEducation) {
        // 将参数转换成 url encoding
        try {
            searchMajor = URLEncoder.encode(searchMajor, "gbk");
            searchSubject = URLEncoder.encode(searchSubject, "gbk");
            searchLocation = URLEncoder.encode(searchLocation, "gbk");
            searchUniversity = URLEncoder.encode(searchUniversity, "gbk");
            searchSex = URLEncoder.encode(searchSex, "gbk");
            searchEducation = URLEncoder.encode(searchEducation, "gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 构造 url
        String url = "http://www.ningbojiajiao.com/Teacher.asp?" +
                "page=" + searchPage +
                "&num=" + searchNum +
                "&zy=" + searchMajor +
                "&km=" + searchSubject +
                "&skdq=" + searchLocation +
                "&xx=" + searchUniversity +
                "&sex=" + searchSex +
                "&xl=" + searchEducation;
        return url;
    }


    // 获取学生 url
    public static String getStudentsURL(String searchPage, String searchNum, String searchSubject,
                                       String searchLocation, String searchStudentSex,
                                       String searchTeacherSex, String searchStatus) {
        // 将参数转换成 url encoding
        try {
            searchSubject = URLEncoder.encode(searchSubject, "gbk");
            searchLocation = URLEncoder.encode(searchLocation, "gbk");
            searchStudentSex = URLEncoder.encode(searchStudentSex, "gbk");
            searchTeacherSex = URLEncoder.encode(searchTeacherSex, "gbk");
            searchStatus = URLEncoder.encode(searchStatus, "gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 构造 url
        String url = "http://www.ningbojiajiao.com/Student.asp?" +
                "page=" + searchPage +
                "&num=" + searchNum +
                "&km=" + searchSubject +
                "&dq=" + searchLocation +
                "&sex=" + searchStudentSex +
                "&sex2=" + searchTeacherSex +
                "&zt=" + searchStatus;
        return url;
    }

    //获取 Document 类型的网页，用来解析
    private static Document getDocument (OkHttpClient client, String url) {

        try {

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            // 以二进制流的形式读入数据， 以便使用 GBK 解码
            byte[] responseBytes = response.body().bytes();
            // 使用 GBK 解码，否则乱码
            String responseData = new String(responseBytes, "gb2312");
            Document document = Jsoup.parse(responseData);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
