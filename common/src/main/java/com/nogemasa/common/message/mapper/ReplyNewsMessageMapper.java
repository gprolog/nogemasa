package com.nogemasa.common.message.mapper;

import com.nogemasa.common.message.pojo.ReplyNewsMessagePojo;

import java.util.List;

/**
 * <br/>create at 15-9-12
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface ReplyNewsMessageMapper {
    /**
     * 查询所有图文消息
     *
     * @return 图文消息列表
     */
    List<ReplyNewsMessagePojo> getAllNewsMessages();

    /**
     * 查询有效图文消息
     *
     * @return 图文消息列表
     */
    List<ReplyNewsMessagePojo> getNewsMessages();
}
