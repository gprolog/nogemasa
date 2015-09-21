package com.nogemasa.management.service.sale.impl;

import com.google.common.collect.Lists;
import com.nogemasa.management.mapper.sale.PromotionMapper;
import com.nogemasa.management.pojo.PromotionPojo;
import com.nogemasa.management.service.sale.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-8-24
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("promotionService")
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public List<PromotionPojo> listPromotion() {
        List<PromotionPojo> promotionList = promotionMapper.listPromotion();
        List<PromotionPojo> result = Lists.newArrayList();
        for (PromotionPojo p : promotionList) {
            if ("1".equals(p.getPromotionType())) {// 满减
                String[] txt = p.getCountMethod().split(",");
                if (txt.length >= 2) {
                    try {
                        Double.valueOf(txt[0]);
                        Double.valueOf(txt[1]);
                        p.setShowText("满" + txt[0] + "减" + txt[1]);
                        result.add(p);
                    } catch (Exception ignored) {
                    }
                }
            } else if ("2".equals(p.getPromotionType())) {// 折扣
                try {
                    double a = Double.valueOf(p.getCountMethod());
                    p.setShowText("打" + (a * 10) + "折");
                    result.add(p);
                } catch (Exception ignored) {
                }
            } else if ("3".equals(p.getPromotionType())) {// 满赠
                String[] txt = p.getCountMethod().split(",");
                if (txt.length >= 2) {
                    try {
                        Double.valueOf(txt[0]);
                        Double.valueOf(txt[1]);
                        p.setShowText("满" + txt[0] + "送" + txt[1]);
                        result.add(p);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return result;
    }
}
