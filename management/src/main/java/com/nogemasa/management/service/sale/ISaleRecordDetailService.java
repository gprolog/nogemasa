package com.nogemasa.management.service.sale;

import com.nogemasa.management.pojo.SaleRecordContentPojo;
import com.nogemasa.management.pojo.SaleRecordDetailPojo;

import java.util.List;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface ISaleRecordDetailService {
    int addSaleRecordDetail(SaleRecordDetailPojo detail);

    List<SaleRecordDetailPojo> getSaleRecordDetailList(SaleRecordContentPojo content);
}
