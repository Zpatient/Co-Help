package com.cohelp.server;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.cohelp.server.utils.SensitiveUtils;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author jianping5
 * @createDate 13/1/2023 下午 7:48
 */

@SpringBootTest
public class SensitiveWordTest {

    @Test
    void test1() {
        String text = "色片";
        // Assertions.assertTrue(SensitiveWordBs.newInstance().contains(text));
        // 判断是否包含敏感词
        System.out.println(SensitiveWordBs.newInstance().contains(text));

        // 返回所有敏感词
        List<String> all = SensitiveWordBs.newInstance().findAll(text);
        System.out.println(all);

        String text1 = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前";
        System.out.println(SensitiveWordBs.newInstance().contains(text1));

        List<String> all1 = SensitiveWordBs.newInstance().findAll(text1);
        System.out.println(all1);

        boolean contains = SensitiveUtils.contains(text, text1);
        System.out.println(contains);
    }
}
