package com.alibaba.csp.sentinel.dashboard.rule.nacos.authority;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanwu
 * @created 2022/9/14 21:53
 */

@Service
public class AuthorityRuleNacosProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {

    private final ConfigService configService;

    public AuthorityRuleNacosProvider(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public List<AuthorityRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {

        String rulesStr = configService.getConfig(appName + NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID,
                NacosConfigUtil.READ_TIMEOUT);

        if (StringUtils.isEmpty(rulesStr)) {
            return Collections.emptyList();
        }

        List<AuthorityRule> list = JSON.parseArray(rulesStr, AuthorityRule.class);
        return list.stream()
                .map(r -> AuthorityRuleEntity.fromAuthorityRule(appName, ip, port, r))
                .collect(Collectors.toList());
    }
}
