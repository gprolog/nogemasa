package com.nogemasa.weixin.test;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <br/>create at 15-9-1
 *
 * @author liuxh
 * @since 1.0.0
 */
public class TimeStampTest {
    @Test
    public void timeStamp2Date() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(1382694957L * 1000);
        Date date = new Date(timestamp.getTime());
        System.out.println("Format To Date:" + date);
        System.out.println(timestamp);
    }
}
