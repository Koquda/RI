package uo.ri.cws.ext.domain;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfessionalGroupTest {

    private ProfessionalGroup pg = null;

    @Before
    public void setUp() throws Exception {
        pg = new ProfessionalGroup("name", 1.5, 1.5);

    }

    /**
     * A new professional group object has no contracts
     */
    @Test
    public void testConstructor() {

        assertEquals("name", pg.getName());
        assertEquals(1.5, pg.getProductivityBonusPercentage(), 0.0);
        assertEquals(1.5, pg.getTrienniumPayment(), 0.0);
        assertTrue(pg.getContracts().isEmpty());

    }

    /**
     * After creating a contract, professional group references the contract
     */
    @Test
    public void testNewContract() {

        LocalDate endDate = LocalDate.now().plusMonths(6)
                .with(TemporalAdjusters.lastDayOfMonth());

        Contract contract = new Contract(new Mechanic("dni", "name", "surname"),
                new ContractType("name", 1), pg, endDate, 1000.0);

        assertTrue(pg.getContracts().contains(contract));
    }

}
