package foo.bar;


import org.junit.Assert;
import org.junit.Test;

public class MainTest {
    @Test
    public void testIntegrity() {
        STATZFIN statzfin = new STATZFIN();
        statzfin.setCDAVTR("TOTO");
        Assert.assertEquals("TOTO", statzfin.getCDAVTR());
    }

}