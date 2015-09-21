package com.nogemasa.management.service.member;

import com.nogemasa.common.pojo.MemberInfoPojo;
import com.nogemasa.common.pojo.MemberPojo;

import java.util.List;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IMemberService {
    List<MemberPojo> getMemberList();

    List<MemberInfoPojo> listMemberByGroup(String groupId, String query);

    int addPointsForMember(String cardNo, int points);

    MemberPojo getMemberPojo(String cardNo);
}
