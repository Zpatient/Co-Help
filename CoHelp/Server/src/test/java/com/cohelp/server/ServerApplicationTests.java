package com.cohelp.server;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.cohelp.server.mapper.ActivityMapper;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Mail;
import com.cohelp.server.model.domain.Result;
import com.cohelp.server.model.domain.TopicNumber;
import com.cohelp.server.model.entity.Course;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Selection;
import com.cohelp.server.model.entity.User;
import com.cohelp.server.model.vo.AskVO;
import com.cohelp.server.model.vo.CourseVO;
import com.cohelp.server.model.vo.DetailResponse;
import com.cohelp.server.service.CollectService;
import com.cohelp.server.service.CourseService;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.service.UserService;
import com.cohelp.server.service.impl.GeneralServiceImpl;
import com.cohelp.server.utils.MailUtils;
import com.cohelp.server.utils.UserHolder;
import com.google.gson.Gson;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cohelp.server.constant.PatternConstant.PHONE_NUMBER_PATTERN;

@SpringBootTest
class ServerApplicationTests {


    @Resource
    private Gson gson;

    @Resource
    private ImageService imageService;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private UserService userService;

    @Resource
    private CourseService courseService;

    @Resource
    private CollectService collectService;

    @Resource
    private GeneralServiceImpl generalServiceImpl;

    @Test
    void contextLoads() {
        System.out.println();
    }

    @Test
    void test01() {
        // Pattern p= Pattern.compile(WORD_NUMBER_PATTERN);
        //
        // String str = "123asjoijo???";
        // Matcher m = p.matcher(str);
        // System.out.println(m.matches());

        // boolean userAccountValid = RegexUtils.isUserAccountValid("123ekljl");
        // System.out.println(userAccountValid);

        IdAndType idAndType = new IdAndType();
        idAndType.setType(1);
        idAndType.setId(18);
        System.out.println(ObjectUtils.anyNull(idAndType));
    }

    @Test
    void test02() {
        String str = "";
        Pattern p= Pattern.compile(PHONE_NUMBER_PATTERN);
        System.out.println(p.matcher(str).matches());
        System.out.println(LocalDateTime.now().getYear());
    }

    @Test
    public void patternTest() {
        String str = "hello123";
        String regex = "hello";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find());
        matcher.reset();    // 重置匹配位置
        System.out.println(matcher.find());
        System.out.println(matcher.matches());
    }

    @Test
    void test03() {
    }

    @Test
    void test04() {
        // System.out.println(getAnimalSign(LocalDateTime.now().getYear() - 18));
        // LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localDateTime = df.format(time);
        System.out.println(localDateTime);
        LocalDateTime ldt = LocalDateTime.parse(localDateTime, df);
        System.out.println(ldt);

        System.out.println("###############");
        System.out.println(gson.toJson(LocalDateTime.now(), LocalDateTime.class));
    //    2022-10-26 18:42:47
    }

    @Test
    void test05() {
        int a = new Integer(1000);
        int b = new Integer(1000);
        System.out.println(a == b);
    }

    @Test
    void test06() {
//        ArrayList<Integer> integerArrayList = new ArrayList<>();
//        List<Activity> activityList = activityMapper.listByHot(integerArrayList);
//        activityList.forEach(activity -> {
//            return;
//        });
//        System.out.println(1);
//
//        ArrayList<Activity> activities = new ArrayList<>();
//        activities.forEach(
//                activity -> {
//                    System.out.println(activity.getActivityTime());
//                    System.out.println(2);
//                }
//        );
//        System.out.println(3);
    }

    @Test
    void test07() {
        System.out.println(Help.class.getName());
        long l = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(l);

    }


    @Test
    void sendmailtest(){
        MailUtils.sendMail(new Mail("Test","给爷成功？"),"2939814223@qq.com");
    }

    @Test
    public void test10(){

        String res = "{\"code\":\"201\",\"data\":{\"id\":1,\"userAccount\":\"1234567890\",\"userName\":\"超级管理员\",\"userPassword\":null,\"avatar\":1,\"sex\":1,\"phoneNumber\":\"19121755640\",\"userEmail\":\"2712748478@qq.com\",\"userRole\":2,\"state\":0,\"userCreateTime\":\"2023-01-20 21:56:42\",\"age\":18,\"teamId\":1,\"teamName\":\"默认\",\"animalSign\":\"鸡\"},\"message\":\"登录成功\"}";
        Gson gson = new Gson();
    }


    @Test
    public void test08(){
        String encryptedPassword = DigestUtils.md5DigestAsHex(("CoHelp" + "1234567890").getBytes());
        System.out.println(encryptedPassword);
    }
    @Test
    public void test09(){
        TopicNumber currentDayPublish = generalServiceImpl.getCurrentDayPublish(1);
        TopicNumber currentYearPublish = generalServiceImpl.getCurrentYearPublish(1);
    }
    @Test
    void test(){
        User user = userService.getById(19);
        UserHolder.setUser(user);

        // 获取学年
        Result<Set<String>> setResult = userService.listSemester();
        Set<String> data = setResult.getData();
        System.out.println(data);

        // 获取课程
        Result<List<CourseVO>> setResult1 = courseService.listCourse("2019-2020-1");
        System.out.println(setResult1.getData());

        // 获取提问
        Result<List<AskVO>> listResult = courseService.listAsk(1, 5, 1, "2019-2020-1", 1);
        System.out.println(listResult.getData());

        // 获取发布
        Result<List<DetailResponse>> listResult1 = userService.searchPublish();
        System.out.println(listResult1.getData());

        // 获取收藏
        Result result = collectService.listCollect(user);
        System.out.println("收藏: " + result.getData());

    }



}


