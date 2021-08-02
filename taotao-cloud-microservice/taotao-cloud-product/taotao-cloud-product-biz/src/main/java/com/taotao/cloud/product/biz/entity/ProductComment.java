package com.taotao.cloud.product.biz.entity;


import com.taotao.cloud.data.jpa.entity.BaseEntity;
import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import java.time.LocalDateTime;
import javax.persistence.Table;

/**
 * 商品评论表
 *
 * @author shuigedeng
 * @since 2020/4/30 16:06
 */
//@Entity
@Table(name = "tt_product_comment")
@org.hibernate.annotations.Table(appliesTo = "tt_product_comment", comment = "商品评论表")
public class ProductComment extends BaseEntity {

    private String productSpecName;

    private Long mallId;

    private Long sceneId;

    private Long customerId;

    private String memberNick;

    private String memberAvatar;

    private String orderCode;

    private short type;

    private short rank;

    private short hasImage;

    private Long commentPicId;

    private short hasSenWord;

    private String originContent;

    private String filterContent;

    private short opType;

    private short replyStatus;

    private String replyContent;

    private String replyOriContent;

    private LocalDateTime replyTime;

    private Long replyUserId;

    private Long replyPicId;

    private short hasAdd;

    private short afterDays;

    private LocalDateTime appendTime;

    private LocalDateTime createTime;

    //    @ApiModelProperty(value = "0隐藏  1显示", example = "0")
    private short status;

}
