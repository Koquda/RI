package uo.ri.cws.ext.persistence.association;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;
import uo.ri.cws.persistence.util.UnitOfWork;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RunMappingTests {

    private UnitOfWork unitOfWork;
    private EntityManagerFactory factory;
    private Mechanic mechanic;
    private Contract contract;
    private ContractType type;
    private ProfessionalGroup group;
    private Payroll payroll;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("carworkshop");
        unitOfWork = UnitOfWork.over(factory);

        mechanic = new Mechanic("mechanic-dni");

        type = new ContractType("contract-type-name", 2.0);
        group = new ProfessionalGroup("professional-group-name", 300.0, 10.0);
        contract = new Contract(mechanic, type, group, 3000.0);
        payroll = new Payroll(contract);

        unitOfWork.persist(payroll, contract, group, type, mechanic);
    }

    @After
    public void tearDown() {
        unitOfWork.remove(payroll, contract, group, type, mechanic);
        factory.close();
    }

    /**
     * A contract recovers its payrolls
     */
    @Test
    public void testContractRecoversPayroll() {

        Contract restored =
                unitOfWork.findById(Contract.class, contract.getId());

        assertTrue(restored.getPayrolls().contains(payroll));
        assertEquals(1, restored.getPayrolls().size());
    }

    /**
     * A payroll recovers its contract
     */
    @Test
    public void testPayrollRecoversContractInForce() {

        Payroll restored = unitOfWork.findById(Payroll.class, payroll.getId());

        assertEquals(restored.getContract(), contract);
    }
}
