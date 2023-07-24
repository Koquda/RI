package uo.ri.cws.domain;

import org.junit.Test;
import uo.ri.util.random.Random;

import static org.junit.Assert.assertEquals;

public class VoucherTest {

    /**
     * Any new payment mean has 0 accumulated
     */
    @Test
    public void testNewVoucher() {
        Voucher b = new Voucher("123", 100.0);

        assertEquals("no-description", b.getDescription());
        assertEquals("123", b.getCode());
        assertEquals(0.0, b.getAccumulated(), 0.0);
        assertEquals(100.0, b.getAvailable(), 0.0);
    }

    /**
     * After paying with a voucher its accumulated increases
     * and its available decreases
     */
    @Test
    public void testVoucherPayment() {
        String code = generateNewCode();
        Voucher b = new Voucher(code, "For test", 100);
        b.pay(10);

        assertEquals("For test", b.getDescription());
        assertEquals(b.getCode(), code);
        assertEquals(10.0, b.getAccumulated(), 0.0);
        assertEquals(90.0, b.getAvailable(), 0.0);
    }

    /**
     * A voucher cannot be charged with an amount greater than its available
     *
     * @return
     */
    @Test(expected = IllegalStateException.class)
    public void testCannotBeCharged() {
        Voucher b = new Voucher("123", "For test", 10.0);
        b.pay(11.0); // raises exception
    }

    private String generateNewCode() {
        return "V-" + Random.string(5) + "-" + Random.integer(1000, 9999);
    }

}
