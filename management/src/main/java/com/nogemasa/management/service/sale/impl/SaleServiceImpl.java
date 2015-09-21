package com.nogemasa.management.service.sale.impl;

import com.nogemasa.management.mapper.sale.SaleRecordMapper;
import com.nogemasa.management.pojo.SaleRecordPojo;
import com.nogemasa.management.service.sale.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <br/>create at 15-8-24
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("saleService")
public class SaleServiceImpl implements ISaleService {
    @Autowired
    private SaleRecordMapper saleRecordMapper;

    @Override
    public int addSaleRecord(SaleRecordPojo saleRecord) {
        if (saleRecord == null || saleRecord.getStore() == null || saleRecord.getStore().getSid() == null) {
            return -1;
        }
        return saleRecordMapper.addSaleRecord(saleRecord);
    }
}
