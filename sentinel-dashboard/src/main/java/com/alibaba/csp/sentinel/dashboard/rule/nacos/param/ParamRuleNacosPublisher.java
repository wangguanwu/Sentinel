package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guanwu
 * @created 2022/9/12 21:11
 */

@Component("paramRuleNacosPublisher")
public class ParamRuleNacosPublisher implements DynamicRulePublisher<List<ParamFlowRuleEntity>> {
    private final ConfigService configService;

    public ParamRuleNacosPublisher(ConfigService configService) {
        this.configService = configService;
    }


    @Override
    public void publish(String app, List<ParamFlowRuleEntity> rules) throws Exception {
        AssertUtil.assertNotBlank(app, "app cannot be empty");
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }
        this.configService.publishConfig(app + NacosConfigUtil.PARAM_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, NacosConfigUtil.convertToRule(rules));


    }
}
