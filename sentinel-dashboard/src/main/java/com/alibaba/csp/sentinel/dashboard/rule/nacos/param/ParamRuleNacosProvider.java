package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanwu
 * @created 2022/9/12 21:32
 */

@Component("paramRuleNacosProvider")
public class ParamRuleNacosProvider implements DynamicRuleProvider<List<ParamFlowRuleEntity>> {
    private final ConfigService configService;

    public ParamRuleNacosProvider(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public List<ParamFlowRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {
        AssertUtil.assertNotBlank(appName, "app cannot be empty");
        String config = this.configService.getConfig(appName + NacosConfigUtil.PARAM_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, NacosConfigUtil.READ_TIMEOUT);
        if (StringUtils.isEmpty(config)) {
            return Collections.emptyList();
        }
        //存储的事ParamFlowRule,所以不能直接转化为PramFlowEntity
        List<ParamFlowRule> paramFlowRules = JSON.parseArray(config, ParamFlowRule.class);
        return paramFlowRules.stream()
                .map(e -> ParamFlowRuleEntity.fromParamFlowRule(appName, ip, port, e))
                .collect(Collectors.toList());
    }
}
