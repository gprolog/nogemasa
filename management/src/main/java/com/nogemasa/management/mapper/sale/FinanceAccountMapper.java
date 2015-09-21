package com.nogemasa.management.mapper.sale;

import com.nogemasa.management.pojo.FinanceAccountPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br/>create at 15-8-30
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface FinanceAccountMapper {
    List<FinanceAccountPojo> getFinanceAccountList(@Param("accountType") String accountType);

    int addFinanceAccount(@Param("accountType") String accountType,
            @Param("accountRemainingSum") Double accountRemainingSum);

    int updateFinanceAccount(@Param("accountType") String accountType,
            @Param("accountRemainingSum") Double accountRemainingSum);
}
