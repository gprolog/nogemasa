package com.nogemasa.common.pojo;

/**
 * <br/>create at 15-8-15
 *
 * @author liuxh
 * @since 1.0.0
 */
public class MemberGroupPojo {
    private String sid;// 用户分组编号
    private String id;// 分组id，由微信分配
    private String name;// 分组名字，UTF8编码
    private long count;// 分组内用户数量

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
