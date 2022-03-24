package com.taotao.cloud.stock.biz.infrastructure.persistence.converter;

import com.xtoon.cloud.common.core.domain.StatusEnum;
import com.xtoon.cloud.common.core.exception.BaseException;
import com.xtoon.cloud.sys.domain.model.permission.PermissionId;
import com.xtoon.cloud.sys.domain.model.role.Role;
import com.xtoon.cloud.sys.domain.model.role.RoleCode;
import com.xtoon.cloud.sys.domain.model.role.RoleId;
import com.xtoon.cloud.sys.domain.model.role.RoleName;
import com.xtoon.cloud.sys.infrastructure.persistence.entity.SysPermissionDO;
import com.xtoon.cloud.sys.infrastructure.persistence.entity.SysRoleDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色Converter
 *
 * @author shuigedeng
 * @date 2021-02-19
 **/
public class RoleConverter {

    public static Role toRole(SysRoleDO sysRoleDO, List<SysPermissionDO> sysPermissionDOList) {
        if (sysRoleDO == null) {
            throw new BaseException("未找到角色");
        }
        List<PermissionId> permissionIds = null;
        if (sysPermissionDOList != null && !sysPermissionDOList.isEmpty()) {
            permissionIds = new ArrayList<>();
            for (SysPermissionDO sysPermissionDO : sysPermissionDOList) {
                permissionIds.add(new PermissionId(sysPermissionDO.getId()));
            }
        }
        Role role = new Role(new RoleId(sysRoleDO.getId()), new RoleCode(sysRoleDO.getRoleCode()), new RoleName(sysRoleDO.getRoleName()),
                sysRoleDO.getRemarks(), StatusEnum.getStatusEnum(sysRoleDO.getStatus()), permissionIds);
        return role;
    }

    public static SysRoleDO fromRole(Role role) {
        SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setId(role.getRoleId() == null ? null : role.getRoleId().getId());
        sysRoleDO.setRoleCode(role.getRoleCode() == null ? null : role.getRoleCode().getCode());
        sysRoleDO.setRoleName(role.getRoleName() == null ? null : role.getRoleName().getName());
        sysRoleDO.setRemarks(role.getRemarks());
        sysRoleDO.setStatus(role.getStatus() == null ? null : role.getStatus().getValue());
        return sysRoleDO;
    }

}
