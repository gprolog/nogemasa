package com.nogemasa.management.service.sale.impl;

import com.nogemasa.management.mapper.sale.SaleRecordDetailMapper;
import com.nogemasa.management.pojo.SaleRecordContentPojo;
import com.nogemasa.management.pojo.SaleRecordDetailPojo;
import com.nogemasa.management.service.sale.ISaleRecordDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("saleRecordDetailService")
public class SaleRecordDetailServiceImpl implements ISaleRecordDetailService {
    @Autowired
    private SaleRecordDetailMapper saleRecordDetailMapper;

    @Override
    public int addSaleRecordDetail(SaleRecordDetailPojo detail) {
        if (detail == null || detail.getContent() == null || detail.getContent().getSid() == null || detail
                .getGoods() == null || detail.getGoods().getSn() == null) {
            return -1;
        }
        return saleRecordDetailMapper.addSaleRecordDetail(detail);
    }

    @Override
    public List<SaleRecordDetailPojo> getSaleRecordDetailList(SaleRecordContentPojo content) {
        if (content == null || content.getSid() == null) {
            return Collections.emptyList();
        }
        return saleRecordDetailMapper.getSaleRecordDetailList(content);
    }
}
