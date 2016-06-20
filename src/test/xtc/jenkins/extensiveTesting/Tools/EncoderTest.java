package xtc.jenkins.extensiveTesting.tools;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Blaise Cador on 01/06/2016.
 */
public class EncoderTest {
    @Test
    public void base64encode() throws Exception {
        Assert.assertEquals("dGVzdA==", Encoder.base64encode("test"));
    }

    @Test
    public void base64decode() throws Exception {
        Assert.assertEquals("test", Encoder.base64decode("dGVzdA=="));
    }

}