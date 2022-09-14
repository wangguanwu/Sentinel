package com.alibaba.csp.sentinel.dashboard.rule.nacos.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guanwu
 * @created 2022/9/14 22:01
 */

@Service
public class DegradeRuleNacosPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {

    private final ConfigService configService;

    public DegradeRuleNacosPublisher(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {
        AssertUtil.assertNotBlank(app, "app name cannot be blank");
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }
        configService.publishConfig(app + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID,
                NacosConfigUtil.convertToRule(rules));
    }
}
