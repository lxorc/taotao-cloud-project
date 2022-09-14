package com.taotao.cloud.sensitive.sensitive.sensitive.annotation.custom;



import com.taotao.cloud.sensitive.sensitive.sensitive.annotation.metadata.SensitiveStrategy;
import com.taotao.cloud.sensitive.sensitive.sensitive.core.custom.CustomPasswordStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义密码脱敏策略
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveStrategy(CustomPasswordStrategy.class)
public @interface SensitiveCustomPasswordStrategy {
}
