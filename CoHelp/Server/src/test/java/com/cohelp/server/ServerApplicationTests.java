package com.cohelp.server;

import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.mapper.ActivityMapper;
import com.cohelp.server.model.domain.IdAndType;
import com.cohelp.server.model.domain.Mail;
import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.service.ImageService;
import com.cohelp.server.service.impl.GeneralServiceImpl;
import com.cohelp.server.utils.MailUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        matcher.reset();    // ??????????????????
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
        List<Activity> activityList = activityMapper.listByHot();
        activityList.forEach(activity -> {
            return;
        });
        System.out.println(1);

        ArrayList<Activity> activities = new ArrayList<>();
        activities.forEach(
                activity -> {
                    System.out.println(activity.getActivityTime());
                    System.out.println(2);
                }
        );
        System.out.println(3);
    }

    @Test
    void test07() {
        System.out.println(Help.class.getName());
        long l = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(l);

    }


    @Test
    void sendmailtest(){
        MailUtils.sendMail(new Mail("Test","???????????????"),"2939814223@qq.com");
    }

    @Test
    public void testTypeEnum(){
        System.out.println(TypeEnum.isTopic(5));
    }

    @Test
    public void test08(){
        String encryptedPassword = DigestUtils.md5DigestAsHex(("CoHelp" + "1234567890").getBytes());
        System.out.println(encryptedPassword);
    }
}
