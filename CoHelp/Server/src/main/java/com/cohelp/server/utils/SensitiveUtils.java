package com.cohelp.server.utils;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;

import java.util.List;

/**
 * 敏感词过滤工具类
 *
 * @author jianping5
 * @createDate 14/1/2023 下午 1:21
 */
public class SensitiveUtils {

    /**
     * 判断是否包含敏感词
     * @param texts
     * @return
     */
    public static boolean contains(String... texts) {
        // 遍历文本数组，判断是否包含敏感词
        for (String text : texts) {
            if (SensitiveWordBs.newInstance().contains(text)) {
                List<String> all = SensitiveWordBs.newInstance().findAll(text);
                if (all.size() == 1 && "活动".equals(all.get(0))) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
}
