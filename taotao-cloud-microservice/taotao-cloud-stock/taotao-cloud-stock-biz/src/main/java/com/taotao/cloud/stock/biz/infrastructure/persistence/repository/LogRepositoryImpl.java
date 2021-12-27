package com.taotao.cloud.stock.biz.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtoon.cloud.sys.domain.model.log.Log;
import com.xtoon.cloud.sys.domain.model.log.LogRepository;
import com.xtoon.cloud.sys.infrastructure.persistence.converter.LogConverter;
import com.xtoon.cloud.sys.infrastructure.persistence.entity.SysLogDO;
import com.xtoon.cloud.sys.infrastructure.persistence.mapper.SysLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 日志-Repository实现类
 *
 * @author haoxin
 * @date 2021-02-02
 **/
@Repository
public class LogRepositoryImpl extends ServiceImpl<SysLogMapper, SysLogDO> implements LogRepository, IService<SysLogDO> {

    @Override
    public void store(Log log) {
        SysLogDO sysLogDO = LogConverter.fromLog(log);
        if (sysLogDO.getId() == null) {
            this.save(sysLogDO);
        } else {
            this.updateById(sysLogDO);
        }
    }
}
