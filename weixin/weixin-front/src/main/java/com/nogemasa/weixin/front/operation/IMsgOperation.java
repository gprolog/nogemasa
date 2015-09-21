package com.nogemasa.weixin.front.operation;

import com.nogemasa.weixin.front.pojo.ItemMessage;

import java.util.List;

/**
 * <br/>create at 15-8-28
 *
 * @author liuxh
 * @since 1.0.0
 */
public interface IMsgOperation {
    String zindpOperation();

    List<ItemMessage> rlfbOperation();

    String zxscOperation();
}
