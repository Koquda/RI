package uo.ri.cws.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CashTest {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * A new cash object has no accumulated
     */
    @Test
    public void testConstructor() {
        Client c = new Client("123", "name", "surname");
        Cash m = new Cash(c);

        assertEquals(m.getClient(), c);
        assertEquals(0.0, m.getAccumulated(), 0.0);
    }

    /**
     * After paying with cash its accumulated increases
     */
    @Test
    public void testCashPay() {
        Client c = new Client("123", "name", "surname");
        Cash m = new Cash(c);
        m.pay(10);

        assertEquals(10.0, m.getAccumulated(), 0.0);
    }

}
