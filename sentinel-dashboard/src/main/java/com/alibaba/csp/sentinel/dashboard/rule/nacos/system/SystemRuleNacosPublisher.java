package com.alibaba.csp.sentinel.dashboard.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guanwu
 * @created 2022/9/14 22:05
 */

@Service
public class SystemRuleNacosPublisher implements DynamicRulePublisher<List<SystemRuleEntity>> {
     private final ConfigService configService;

    public SystemRuleNacosPublisher(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void publish(String app, List<SystemRuleEntity> rules) throws Exception {
        AssertUtil.assertNotBlank(app, "app cannot be empty");
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }
        this.configService.publishConfig(app + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, NacosConfigUtil.convertToRule(rules));

    }
}
