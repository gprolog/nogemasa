package com.nogemasa.common.pojo;//

/**
 * 会员基本信息
 * <br/>create at 15-7-26
 *
 * @author liuxh
 * @since 1.0.0
 */
public class MemberInfoPojo {
    private String sid;// 会员编号
    private int subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    private String openid;// 用户的标识，对当前公众号唯一
    private String nickname;// 用户的昵称
    private int sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String city;// 用户所在城市
    private String country;// 用户所在国家
    private String province;// 用户所在省份
    private String language;// 用户的语言，简体中文为zh_CN
    private String headimgurl;// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    private long subscribe_time;// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private String unionid;// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
    private String remark;// 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    private int groupid;// 用户所在的分组ID

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public long getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(long subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberInfoPojo that = (MemberInfoPojo) o;

        if (groupid != that.groupid) {
            return false;
        }
        if (sex != that.sex) {
            return false;
        }
        if (subscribe != that.subscribe) {
            return false;
        }
        if (subscribe_time != that.subscribe_time) {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null) {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null) {
            return false;
        }
        if (headimgurl != null ? !headimgurl.equals(that.headimgurl) : that.headimgurl != null) {
            return false;
        }
        if (language != null ? !language.equals(that.language) : that.language != null) {
            return false;
        }
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) {
            return false;
        }
        if (!openid.equals(that.openid)) {
            return false;
        }
        if (province != null ? !province.equals(that.province) : that.province != null) {
            return false;
        }
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
            return false;
        }
        if (!unionid.equals(that.unionid)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = subscribe;
        result = 31 * result + openid.hashCode();
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + sex;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (headimgurl != null ? headimgurl.hashCode() : 0);
        result = 31 * result + (int) (subscribe_time ^ (subscribe_time >>> 32));
        result = 31 * result + (unionid != null ? unionid.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + groupid;
        return result;
    }

    @Override
    public String toString() {
        return "MemberInfoPojo{" +
                "subscribe=" + subscribe +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", language='" + language + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribe_time=" + subscribe_time +
                ", unionid='" + unionid + '\'' +
                ", remark='" + remark + '\'' +
                ", groupid=" + groupid +
                '}';
    }
}
