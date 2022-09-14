package com.alibaba.csp.sentinel.dashboard.rule.nacos.authority;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guanwu
 * @created 2022/9/14 21:45
 */

@Service
public class AuthorityRuleNacosPublisher implements DynamicRulePublisher<List<AuthorityRuleEntity>> {
    private final ConfigService configService;

    public AuthorityRuleNacosPublisher(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void publish(String app, List<AuthorityRuleEntity> rules) throws Exception {
        Asserts.notEmpty(app, "app name cannot be empty");
        if (rules == null){
            return;
        }
        configService.publishConfig(app + NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, NacosConfigUtil.convertToRule(rules));
    }
}
