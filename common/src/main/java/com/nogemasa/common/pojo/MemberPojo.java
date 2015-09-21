package com.nogemasa.common.pojo;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
public class MemberPojo {
    private String sid;// 编号
    private MemberInfoPojo memberInfo;// 会员数据/用户数据
    private long points;// 用户积分
    private String card_no;// 会员卡号
    private String openid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public MemberInfoPojo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfoPojo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "MemberPojo{" +
                "sid='" + sid + '\'' +
                ", memberInfo=" + memberInfo +
                ", points=" + points +
                ", card_no='" + card_no + '\'' +
                '}';
    }
}
