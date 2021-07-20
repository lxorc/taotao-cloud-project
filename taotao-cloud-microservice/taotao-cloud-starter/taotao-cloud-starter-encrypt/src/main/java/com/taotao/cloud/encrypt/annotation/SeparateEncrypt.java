package com.taotao.cloud.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 独立注解
 * 用于部分类和方法加密使用
 *
 * @author gaoyang shuigedeng
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SeparateEncrypt {
}
