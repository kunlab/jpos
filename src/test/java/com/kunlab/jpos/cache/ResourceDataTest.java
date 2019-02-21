package com.kunlab.jpos.cache;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author likun
 */
public class ResourceDataTest {

    @Test
    public void execute() {

        ResourceData resourceData = new ResourceData();
        resourceData.setName("cache");
        resourceData.setNode("respcode");

        Map map = resourceData.execute();
        Assert.assertEquals("00", ((Map)map.get("respcode")).get("00000000"));
    }
}