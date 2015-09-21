package com.nogemasa.management.service.sale.impl;

import com.nogemasa.management.mapper.sale.SaleRecordContentMapper;
import com.nogemasa.management.pojo.SaleRecordContentPojo;
import com.nogemasa.management.service.sale.ISaleRecordContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("saleRecordContentService")
public class SaleRecordContentServiceImpl implements ISaleRecordContentService {
    @Autowired
    private SaleRecordContentMapper saleRecordContentMapper;

    @Override
    public int addSaleRecordContent(SaleRecordContentPojo content) {
        if (content == null || content.getStore() == null || content.getStore().getSid() == null) {
            return -1;
        }
        return saleRecordContentMapper.addSaleRecordContent(content);
    }

    @Override
    public List<SaleRecordContentPojo> getSaleRecordContentList() {
        return saleRecordContentMapper.getSaleRecordContentList();
    }
}
