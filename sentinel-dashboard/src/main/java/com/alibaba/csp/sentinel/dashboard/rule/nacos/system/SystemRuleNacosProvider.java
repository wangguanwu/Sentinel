package com.alibaba.csp.sentinel.dashboard.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanwu
 * @created 2022/9/14 22:08
 */

@Service
public class SystemRuleNacosProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {
    private final ConfigService configService;

    public SystemRuleNacosProvider(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public List<SystemRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {
        Asserts.notEmpty(appName, "app cannot be empty");
        String config = this.configService.getConfig(appName + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, NacosConfigUtil.READ_TIMEOUT);
        if (StringUtils.isEmpty(config)) {
            return Collections.emptyList();
        }
        List<SystemRule> paramFlowRules = JSON.parseArray(config, SystemRule.class);
        return paramFlowRules.stream()
                .map(e -> SystemRuleEntity.fromSystemRule(appName, ip, port, e))
                .collect(Collectors.toList());
    }
}
