package com.nogemasa.weixin.mapper;

import com.nogemasa.common.message.mapper.ReplyNewsMessageMapper;
import com.nogemasa.common.message.pojo.ReplyNewsMessagePojo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <br/>create at 15-9-12
 *
 * @author liuxh
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class TextCommonMapper {
    @Autowired
    private ReplyNewsMessageMapper replyNewsMessageMapper;

    @Test
    public void testGetNewsMessages() {
        List<ReplyNewsMessagePojo> list = replyNewsMessageMapper.getNewsMessages();
        Assert.assertNotNull(list);
        Assert.assertNotEquals(list.size(), 0);
    }
}
