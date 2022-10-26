package com.cohelp.server;

import com.cohelp.server.constant.TypeEnum;
import com.cohelp.server.controller.UserController;
import com.cohelp.server.model.domain.Mail;
import com.cohelp.server.utils.MailUtils;
import com.cohelp.server.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cohelp.server.constant.PatternConstant.PHONE_NUMBER_PATTERN;

@SpringBootTest
class ServerApplicationTests {

    @Resource
    private UserController userController;

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

        boolean userAccountValid = RegexUtils.isUserAccountValid("123ekljl");
        System.out.println(userAccountValid);
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
        String userAccount = "0123456789";
        String userPassword = "wjp123456789";
        String userConfirmPassword = "wjp123456789";
        String phoneNumber = "13467893245";
        String userEmail = "2712748478@qq.com";
  //      userController.userRegister(new RegisterRequest(userAccount, userPassword, userConfirmPassword, phoneNumber, userEmail));
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
    }

    @Test
    void test05() {
        int a = new Integer(1000);
        int b = new Integer(1000);
        System.out.println(a == b);
    }

    @Test
    void sendmailtest(){
        MailUtils.sendMail(new Mail("Test","给爷成功？"),"2939814223@qq.com");
    }
    @Test
    public void testTypeEnum(){
        System.out.println(TypeEnum.isTopic(5));
    }
}
