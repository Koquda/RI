package uo.ri.cws.ext.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Set;

import static org.junit.Assert.*;

public class TypeTests {
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
        assertEquals(contract.getContractType(), type);
        assertTrue(type.getContracts().contains(contract));
    }

    @Test
    public void testUnlinkOnRun() {

        Associations.Type.unlink(contract, type);

        assertNull(contract.getContractType());
        assertFalse(type.getContracts().contains(contract));

    }

    @Test
    public void testSafeReturn() {
        Set<Contract> contracts = type.getContracts();
        int num = contracts.size();

        contracts.remove(contract);

        assertEquals(type.getContracts().size(), num);
        assertTrue("It must be a copy of the collection or a read-only " +
                "version", type.getContracts().contains(contract));
    }

}
