package com.nogemasa.management.service.sale;

import com.nogemasa.management.pojo.SaleRecordContentPojo;

import java.util.List;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface ISaleRecordContentService {
    int addSaleRecordContent(SaleRecordContentPojo content);

    List<SaleRecordContentPojo> getSaleRecordContentList();
}
