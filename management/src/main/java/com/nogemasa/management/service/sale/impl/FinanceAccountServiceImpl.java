package com.nogemasa.management.service.sale.impl;

import com.nogemasa.management.mapper.sale.FinanceAccountMapper;
import com.nogemasa.management.pojo.FinanceAccountPojo;
import com.nogemasa.management.service.sale.IFinanceAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br/>create at 15-9-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Service("financeAccountService")
public class FinanceAccountServiceImpl implements IFinanceAccountService {
    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public int updateFinanceAccount(String accountType, Double accountRemainingSum) {
        if (accountType == null || accountRemainingSum == 0) {
            return -1;
        }
        List<FinanceAccountPojo> list = financeAccountMapper.getFinanceAccountList(accountType);
        if (list.size() == 0) {
            return financeAccountMapper.addFinanceAccount(accountType, accountRemainingSum);
        } else {
            return financeAccountMapper.updateFinanceAccount(accountType, accountRemainingSum);
        }
    }
}
