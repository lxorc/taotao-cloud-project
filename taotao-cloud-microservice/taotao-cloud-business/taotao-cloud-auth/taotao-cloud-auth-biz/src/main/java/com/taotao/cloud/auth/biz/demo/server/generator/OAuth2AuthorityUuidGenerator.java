/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.auth.biz.demo.server.generator;

import cn.herodotus.engine.data.jpa.hibernate.identifier.AbstractUuidGenerator;
import cn.herodotus.engine.oauth2.server.authentication.entity.OAuth2Authority;
import java.lang.reflect.Member;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

/**
 * Description: 使得保存实体类时可以在保留主键生成策略的情况下自定义表的主键
 *
 * @author : gengwei.zheng
 * @date : 2022/3/31 21:11
 */
public class OAuth2AuthorityUuidGenerator extends AbstractUuidGenerator {

    public OAuth2AuthorityUuidGenerator(
            OAuth2AuthorityUuid config, Member idMember, CustomIdGeneratorCreationContext creationContext) {
        super(idMember);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        OAuth2Authority authority = (OAuth2Authority) object;

        if (StringUtils.isEmpty(authority.getAuthorityId())) {
            return super.generate(session, object);
        } else {
            return authority.getAuthorityId();
        }
    }
}
