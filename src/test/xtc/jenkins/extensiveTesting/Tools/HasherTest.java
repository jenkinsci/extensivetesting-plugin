package xtc.jenkins.extensiveTesting.tools;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Blaise Cador on 01/06/2016.
 */
public class HasherTest {
    @Test
    public void sha1() throws Exception {
        Assert.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", Hasher.sha1("test"));
    }

}