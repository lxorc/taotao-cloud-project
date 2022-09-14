package com.taotao.cloud.sensitive.sensitive.sensitive.api.impl;


import com.taotao.cloud.sensitive.sensitive.sensitive.api.ICondition;
import com.taotao.cloud.sensitive.sensitive.sensitive.api.IContext;

/**
 * 一致返回真的条件
 */
public class ConditionAlwaysTrue implements ICondition {
    @Override
    public boolean valid(IContext context) {
        return true;
    }
}
