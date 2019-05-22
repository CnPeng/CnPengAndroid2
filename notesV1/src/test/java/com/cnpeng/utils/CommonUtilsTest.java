package com.cnpeng.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * 作者：CnPeng
 * 时间：2018/8/7
 * 功用：
 * 其他：
 */
public class CommonUtilsTest {

    @Test
    public void getMax() {
        assertEquals(CommonUtils.getMax(5, 6),6);
    }
}