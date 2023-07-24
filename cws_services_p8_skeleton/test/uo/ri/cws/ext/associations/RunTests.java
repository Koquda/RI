package uo.ri.cws.ext.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;

public class RunTests {
    private Mechanic mechanic;
    private Contract contract;
    private ContractType type;
    private ProfessionalGroup group;
    private Payroll payroll;

    @Before
    public void setUp() {
        mechanic = new Mechanic("dni", "nombre", "apellidos");
        type = new ContractType("type", 1.5);
        group = new ProfessionalGroup("group", 100.0, 10.0);
        double wage = 1000.0;

        contract = new Contract(mechanic, type, group, wage);
        payroll = new Payroll(contract, LocalDate.now());
    }

    @Test
    public void testLinkOnRun() {
        assertTrue(contract.getPayrolls().contains(payroll));
        assertEquals(payroll.getContract(), contract);
    }

    @Test
    public void testUnlinkOnRun() {

        Associations.Run.unlink(contract, payroll);

        assertNull(payroll.getContract());
        assertFalse(contract.getPayrolls().contains(payroll));
    }

    @Test
    public void testSafeReturn() {

        Set<Payroll> payrolls = contract.getPayrolls();
        int num = payrolls.size();

        payrolls.remove(payroll);

        assertEquals(contract.getPayrolls().size(), num);
        assertTrue("It must be a copy of the collection or a read-only " +
                "version", contract.getPayrolls().contains(payroll));
    }

}
