package com.nogemasa.common.mapper;

import com.nogemasa.common.pojo.MemberPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface MemberMapper {
    List<MemberPojo> getMemberList();

    MemberPojo getMemberByOpenId(String openId);

    MemberPojo getMemberByCardNo(String cardNo);

    MemberPojo getMemberByCardNoSimple(String cardNo);

    int addMember(MemberPojo member);

    int updateMemberPoints(@Param("openid") String openid, @Param("points") long points);

    int addPointsForMember(@Param("cardNo") String cardNo, @Param("points") int points);
}
