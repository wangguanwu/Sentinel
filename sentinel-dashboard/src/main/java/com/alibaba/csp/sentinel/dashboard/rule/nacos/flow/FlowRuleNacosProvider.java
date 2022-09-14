package com.alibaba.csp.sentinel.dashboard.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanwu
 * @created 2022/9/12 20:55
 */

@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {
    private final ConfigService configService;

    public FlowRuleNacosProvider(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public List<FlowRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {
        String config = configService.getConfig(appName + NacosConfigUtil.FLOW_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID,
                NacosConfigUtil.READ_TIMEOUT);
        if (StringUtil.isBlank(config)) {
            return Collections.emptyList();
        }
        List<FlowRule> list = JSON.parseArray(config, FlowRule.class);
        return list.stream()
                 .map(e -> FlowRuleEntity.fromFlowRule(appName, ip, port, e))
                .collect(Collectors.toList());

    }
}
