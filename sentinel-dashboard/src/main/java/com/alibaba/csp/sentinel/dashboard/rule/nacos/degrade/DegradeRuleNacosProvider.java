package com.alibaba.csp.sentinel.dashboard.rule.nacos.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanwu
 * @created 2022/9/14 21:59
 */

@Service
public class DegradeRuleNacosProvider implements DynamicRuleProvider<List<DegradeRuleEntity>> {
    private final ConfigService configService;

    public DegradeRuleNacosProvider(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public List<DegradeRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {
        String config = configService.getConfig(appName + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID,
                NacosConfigUtil.READ_TIMEOUT);
        if (StringUtil.isBlank(config)) {
            return Collections.emptyList();
        }
        List<DegradeRule> list = JSON.parseArray(config, DegradeRule.class);
        return list.stream()
                .map(e -> DegradeRuleEntity.fromDegradeRule(appName, ip, port, e))
                .collect(Collectors.toList());

    }
}
