package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanwu
 * @created 2022/9/12 20:49
 */
public class NacosConfigUtil {

    public static final String GROUP_ID = "SENTINEL_GROUP";
    public static final String FLOW_DATA_ID_POSTFIX = "-flow-rules";
    public static final String PARAM_DATA_ID_POSTFIX = "-param-flow-rules";
    public static final String DEGRADE_DATA_ID_POSTFIX = "-degrade-rules";
    public static final String AUTHORITY_DATA_ID_POSTFIX = "-authority-rules";
    public static final String SYSTEM_DATA_ID_POSTFIX = "-system-rules";
    public static final long READ_TIMEOUT = 3 * 1000L;
    private NacosConfigUtil() {

    }

    public static String convertToRule(List<? extends RuleEntity> entities) {
        return JSON.toJSONString(entities.stream()
                .map(RuleEntity::toRule)
                .collect(Collectors.toList())
        );
    }
}
