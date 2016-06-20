package xtc.jenkins.extensiveTesting.tools;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Blaise Cador on 01/06/2016.
 */
public class CompressorTest {
    @Test
    public void inflater() throws Exception {
        Assert.assertEquals("test", Compressor.inflater("eNorSS0uAQAEXQHB"));
    }

}