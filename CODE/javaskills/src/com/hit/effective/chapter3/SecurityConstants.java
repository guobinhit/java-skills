package com.hit.effective.chapter3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * author:Charies Gavin
 * date:2018/5/28,8:54
 * https:github.com/guobinhit
 * description:常量数组
 */
public class SecurityConstants {

    private SecurityConstants() {
    }

    /**
     * 存在潜在的安全漏洞
     */
    public static final String[] UNSECURITY_DEFAULT_VALUES = {"5", "2", "0"};

    /**
     * 安全的定义方法
     */
    private static final String[] SECURITY_DEFAULT_VALUES = {"5", "2", "0"};

    /**
     * 提供一个不可变的列表
     */
    public static final List<String> SECURITY_DEFAULT_VALUES_LIST =
            Collections.unmodifiableList(Arrays.asList(SECURITY_DEFAULT_VALUES));

    /**
     * 返回私有数组的一个备份
     *
     * @return
     */
    public static final String[] values() {
        return SECURITY_DEFAULT_VALUES.clone();
    }
}
