package com.nogemasa.common.mapper;

import com.nogemasa.common.pojo.MemberInfoPojo;

import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface MemberInfoMapper {
    List<MemberInfoPojo> listMemberByParam(Map<String, String> params);

    MemberInfoPojo getMemberByOpenId(String openId);

    int addMemberInfoBatch(List<MemberInfoPojo> list);

    int addMemberInfo(MemberInfoPojo member);

    int enabledMemberInfo(String openId);

    int disabledMemberInfo(MemberInfoPojo member);

    int updateMemberInfo(MemberInfoPojo member);

}
