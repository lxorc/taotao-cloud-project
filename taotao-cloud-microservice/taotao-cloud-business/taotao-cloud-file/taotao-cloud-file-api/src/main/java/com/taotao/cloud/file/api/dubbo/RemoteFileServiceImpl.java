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

package com.taotao.cloud.file.api.dubbo;
//
// import com.ruoyi.common.core.exception.ServiceException;
// import com.ruoyi.common.core.utils.StringUtils;
// import com.ruoyi.common.oss.core.OssClient;
// import com.ruoyi.common.oss.entity.UploadResult;
// import com.ruoyi.common.oss.factory.OssFactory;
// import com.ruoyi.resource.api.RemoteFileService;
// import com.ruoyi.resource.api.domain.SysFile;
// import com.ruoyi.resource.domain.bo.SysOssBo;
// import com.ruoyi.resource.service.ISysOssService;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.dubbo.config.annotation.DubboService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
/// **
// * 文件请求处理
// *
// * @author Lion Li
// */
// @Slf4j
// @Service
// @RequiredArgsConstructor
// @DubboService
// public class RemoteFileServiceImpl implements RemoteFileService {
//
//    private final ISysOssService sysOssService;
//
//    /**
//     * 文件上传请求
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public SysFile upload(String name, String originalFilename, String contentType, byte[] file)
// throws ServiceException {
//        try {
//            String suffix = StringUtils.substring(originalFilename,
// originalFilename.lastIndexOf("."), originalFilename.length());
//            OssClient storage = OssFactory.instance();
//            UploadResult uploadResult = storage.uploadSuffix(file, suffix, contentType);
//            // 保存文件信息
//            SysOssBo oss = new SysOssBo();
//            oss.setUrl(uploadResult.getUrl());
//            oss.setFileSuffix(suffix);
//            oss.setFileName(uploadResult.getFilename());
//            oss.setOriginalName(originalFilename);
//            oss.setService(storage.getConfigKey());
//            sysOssService.insertByBo(oss);
//            SysFile sysFile = new SysFile();
//            sysFile.setOssId(oss.getOssId());
//            sysFile.setName(uploadResult.getFilename());
//            sysFile.setUrl(uploadResult.getUrl());
//            return sysFile;
//        } catch (Exception e) {
//            log.error("上传文件失败", e);
//            throw new ServiceException("上传文件失败");
//        }
//    }
//
//    /**
//     * 通过ossId查询对应的url
//     *
//     * @param ossIds ossId串逗号分隔
//     * @return url串逗号分隔
//     */
//    @Override
//    public String selectUrlByIds(String ossIds) {
//        return sysOssService.selectUrlByIds(ossIds);
//    }
//
// }
