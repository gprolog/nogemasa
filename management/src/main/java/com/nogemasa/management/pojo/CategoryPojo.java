package com.nogemasa.management.pojo;

import java.util.List;

/**
 * <br/>create at 15-8-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public class CategoryPojo {
    private String sid;// 编号
    private String name;// 名称
    private CategoryPojo root;// 根节点
    private CategoryPojo parent;// 父节点
    private List<CategoryPojo> children;// 子节点列表
    private boolean isLeaf;// 是否叶子节点

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryPojo getRoot() {
        return root;
    }

    public void setRoot(CategoryPojo root) {
        this.root = root;
    }

    public CategoryPojo getParent() {
        return parent;
    }

    public void setParent(CategoryPojo parent) {
        this.parent = parent;
    }

    public List<CategoryPojo> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryPojo> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
