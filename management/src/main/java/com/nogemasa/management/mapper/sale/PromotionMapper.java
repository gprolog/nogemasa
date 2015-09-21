package com.nogemasa.management.mapper.sale;

import com.nogemasa.management.pojo.PromotionPojo;

import java.util.List;

/**
 * <br/>create at 15-8-24
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface PromotionMapper {
    List<PromotionPojo> listPromotion();

    PromotionPojo getPromotionBySid();
}
