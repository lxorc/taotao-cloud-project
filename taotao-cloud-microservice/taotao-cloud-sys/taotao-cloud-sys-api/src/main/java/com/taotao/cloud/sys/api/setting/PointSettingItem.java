package com.taotao.cloud.sys.api.setting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 积分签到设置
 *
 */
@Data
public class PointSettingItem implements Comparable<PointSettingItem>, Serializable {


    @ApiModelProperty(value = "签到天数")
    private Integer day;


    @ApiModelProperty(value = "赠送积分")
    private Integer point;

    public Integer getPoint() {
        if (point == null || point < 0) {
            return 0;
        }
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Override
    public int compareTo(PointSettingItem pointSettingItem) {
        return this.day - pointSettingItem.getDay();
    }
}
