package uo.ri.cws.ext.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Set;

import static org.junit.Assert.*;

public class GroupTests {
    private Mechanic mechanic;
    private Contract contract;
    private ContractType type;
    private ProfessionalGroup group;

    @Before
    public void setUp() {
        mechanic = new Mechanic("dni", "nombre", "apellidos");
        type = new ContractType("type", 1.5);
        group = new ProfessionalGroup("group", 100.0, 10.0);
        double wage = 10000.0;

        contract = new Contract(mechanic, type, group, wage);

    }

    @Test
    public void testLinkOnRun() {
        assertEquals(contract.getProfessionalGroup(), group);
        assertTrue(group.getContracts().contains(contract));
    }

    @Test
    public void testUnlinkOnRun() {

        Associations.Group.unlink(contract, group);

        assertNull(contract.getProfessionalGroup());

        assertFalse(group.getContracts().contains(contract));

    }

    @Test
    public void testSafeReturn() {
        Set<Contract> contracts = group.getContracts();
        int num = contracts.size();

        contracts.remove(contract);

        assertEquals(group.getContracts().size(), num);
        assertTrue("It must be a copy of the collection or a read-only " +
                "version", group.getContracts().contains(contract));
    }

}
