package uo.ri.cws.ext.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HireTests {
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
    public void testLinkOnHire() {
        Optional<Mechanic> m = contract.getMechanic();
        Optional<Contract> contractInForce = mechanic.getContractInForce();

        assertTrue(m.isPresent());
        assertEquals(m.get(), mechanic);
        assertTrue(contractInForce.isPresent());
        assertEquals(contractInForce.get(), contract);

    }

    @Test
    public void testUnlinkOnHire() {
        Associations.Hire.unlink(contract, mechanic);

        assertTrue(mechanic.getContractInForce().isEmpty());
        assertTrue(contract.getMechanic().isPresent());
    }

    @Test
    public void testSecondLinkOnHire() {

        Contract secondContract =
                new Contract(mechanic, new ContractType("othertype", 2.5),
                        new ProfessionalGroup("othergroup", 200.0, 20.0),
                        2000.0);

        assertTrue(secondContract.getMechanic().isPresent());
        assertEquals(secondContract.getMechanic().get(), mechanic);
        assertTrue(mechanic.isInForce());
        assertEquals(mechanic.getContractInForce().get(), secondContract);
    }

}
