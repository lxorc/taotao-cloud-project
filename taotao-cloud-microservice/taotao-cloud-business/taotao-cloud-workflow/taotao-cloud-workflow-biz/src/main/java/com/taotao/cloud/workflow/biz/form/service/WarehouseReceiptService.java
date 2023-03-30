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

package com.taotao.cloud.workflow.biz.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taotao.cloud.workflow.biz.form.entity.WarehouseEntryEntity;
import com.taotao.cloud.workflow.biz.form.entity.WarehouseReceiptEntity;
import java.util.List;
import java.util.Map;

/** 入库申请单 */
public interface WarehouseReceiptService extends IService<WarehouseReceiptEntity> {

    /**
     * 列表
     *
     * @param id 主键值
     * @return
     */
    List<WarehouseEntryEntity> getWarehouseEntryList(String id);

    /**
     * 信息
     *
     * @param id 主键值
     * @return
     */
    WarehouseReceiptEntity getInfo(String id);

    /**
     * 保存
     *
     * @param id 主键值
     * @param entity 实体对象
     * @param warehouseEntryEntityList 子表
     * @throws WorkFlowException 异常
     */
    void save(
            String id,
            WarehouseReceiptEntity entity,
            List<WarehouseEntryEntity> warehouseEntryEntityList)
            throws WorkFlowException;

    /**
     * 提交
     *
     * @param id 主键值
     * @param entity 实体对象
     * @param warehouseEntryEntityList 子表
     * @throws WorkFlowException 异常
     */
    void submit(
            String id,
            WarehouseReceiptEntity entity,
            List<WarehouseEntryEntity> warehouseEntryEntityList,
            Map<String, List<String>> candidateList)
            throws WorkFlowException;

    /**
     * 更改数据
     *
     * @param id 主键值
     * @param data 实体对象
     */
    void data(String id, String data);
}
