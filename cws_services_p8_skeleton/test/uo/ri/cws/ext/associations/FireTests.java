package uo.ri.cws.ext.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class FireTests {
    private Mechanic mechanic;
    private Contract contract;
    private ContractType type;
    private ProfessionalGroup group;

    @Before
    public void setUp() {
        mechanic = new Mechanic("dni", "nombre", "apellidos");
        type = new ContractType("type", 1.5);
        group = new ProfessionalGroup("group", 100.0, 10.0);
        double wage = 1000.0;

        contract = new Contract(mechanic, type, group, wage);
    }

    @Test
    public void testLinkOnFire() {
        Associations.Fire.link(contract, mechanic);

        Optional<Mechanic> om = contract.getFiredMechanic();
        assertTrue(om.isPresent());
        Mechanic m = om.get();
        assertTrue(m.getTerminatedContracts().contains(contract));
        assertEquals(m, contract.getMechanic().get());
    }

    @Test
    public void testUnlinkOnFire() {
        Associations.Fire.link(contract, mechanic);

        Mechanic m = contract.getFiredMechanic().get();

        Associations.Fire.unlink(contract, mechanic);

        assertFalse(m.getTerminatedContracts().contains(contract));
        assertTrue(contract.getFiredMechanic().isEmpty());
    }

    @Test
    public void testSafeReturn() {
        Set<Contract> contracts = mechanic.getTerminatedContracts();
        int num = contracts.size();

        contracts.add(contract);

        assertEquals(contracts.size(), num + 1);
        assertFalse("It must be a copy of the collection or a read-only " +
                        "version",
                mechanic.getTerminatedContracts().contains(contract));
    }

}
