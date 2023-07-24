package uo.ri.cws.ext.domain;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.util.math.Round;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;

public class PayrollTest {

    private final double productivityPercent = 10.0;
    private final double trienniumAmount = 100.0;
    private final ProfessionalGroup group =
            new ProfessionalGroup("XI", trienniumAmount, productivityPercent);
    private final ContractType type = new ContractType("T", 1.5);
    private final Mechanic mechanic =
            new Mechanic("dni", "nombre", "apellidos");

    private final double expectedMonthlyWage = 3650;
    private final double annualWage = 51100; // salario base anual
    private final double expectedNic = Round.twoCents(annualWage * 0.05 / 12);

    private Contract contract;

    @Before
    public void setUp() throws Exception {
        LocalDate endDate = LocalDate.now().plusMonths(6)
                .with(TemporalAdjusters.lastDayOfMonth());

        contract = new Contract(mechanic, type, group, endDate, annualWage);
    }

    /**
     * A new payroll
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPayrollIllegalExceptionIfNullArg() {
        new Payroll(null);
    }

    /**
     * A new payroll
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPayrollIllegalExceptionIfNullDate() {
        new Payroll(contract, null);
    }

    /**
     * A new payroll
     */
    @Test
    public void testConstructorNow() {

        Payroll p = new Payroll(contract);
        assertEquals(p.getContract(), contract);
        assertEquals(p.getDate(), LocalDate.now());
    }

    /**
     * A new payroll
     */
    @Test
    public void testConstructorWithDate() {
        LocalDate d = LocalDate.now().minusDays(1).minusMonths(2).minusYears(3);

        Payroll p = new Payroll(contract, d);
        assertEquals(contract, p.getContract());
        assertEquals(d, p.getDate());
    }

    @Test
    public void testNoActivityShortTermContract() {
        LocalDate january = LocalDate.now().withMonth(1)
                .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate march = LocalDate.now().withMonth(3)
                .with(TemporalAdjusters.firstDayOfMonth());

        contract.setStartDate(january);

        Payroll p = new Payroll(contract, march);
        double expectedIrpf = expectedMonthlyWage * 0.37;

        assertEquals(march, p.getDate());
        assertEquals(contract, p.getContract());

        assertEquals(expectedMonthlyWage, p.getMonthlyWage(), 0.01);
        assertEquals(0.0, p.getBonus(), 0.01);
        assertEquals(0.0, p.getProductivityBonus(), 0.01);
        assertEquals(0.0, p.getTrienniumPayment(), 0.01);
        assertEquals(expectedNic, p.getNIC(), 0.01);
        assertEquals(expectedIrpf, p.getIncomeTax(), 0.01);

    }

    @Test
    public void testJuneNoActivityShortTermContract() {
        LocalDate january = LocalDate.now().withMonth(1)
                .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate june = LocalDate.now().withMonth(6)  // June -> extra
                .with(TemporalAdjusters.firstDayOfMonth());

        contract.setStartDate(january);
        Payroll p = new Payroll(contract, june);
        double expectedIrpf = expectedMonthlyWage * 2 * 0.37;

        assertEquals(june, p.getDate());
        assertEquals(contract, p.getContract());

        assertEquals(expectedMonthlyWage, p.getMonthlyWage(), 0.01);
        assertEquals(expectedMonthlyWage, p.getBonus(), 0.01);
        assertEquals(0.0, p.getProductivityBonus(), 0.01);
        assertEquals(0.0, p.getTrienniumPayment(), 0.01);
        assertEquals(expectedNic, p.getNIC(), 0.01);
        assertEquals(expectedIrpf, p.getIncomeTax(), 0.01);
    }

    @Test
    public void testNovemberWithProductivityNoTriennium() {
        double workOrderAmount = 365.0;
        double productivity = workOrderAmount * productivityPercent / 100;
        double irpf = (expectedMonthlyWage + productivity) * 0.37;

        LocalDate january = LocalDate.now().withMonth(1)
                .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate november = LocalDate.now().withMonth(11)
                .with(TemporalAdjusters.firstDayOfMonth());

        contract.setStartDate(january);
        addWorkOrdersToMechanic(november, workOrderAmount);
        Payroll p = new Payroll(contract, november);

        assertEquals(november, p.getDate());
        assertEquals(contract, p.getContract());

        assertEquals(expectedMonthlyWage, p.getMonthlyWage(), 0.01);
        assertEquals(0.0, p.getBonus(), 0.01);
        assertEquals(productivity, p.getProductivityBonus(), 0.01);
        assertEquals(0.0, p.getTrienniumPayment(), 0.01);
        assertEquals(expectedNic, p.getNIC(), 0.01);
        assertEquals(irpf, p.getIncomeTax(), 0.01);

    }

    @Test
    public void testJuneWithProductivityNoTriennium() {
        double interventions = 365.0;
        double productivity = interventions * productivityPercent / 100; //
        // productividad mensual
        double irpf = (this.expectedMonthlyWage * 2 + productivity) * 0.37;

        LocalDate january = LocalDate.now().withMonth(1)
                .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate june = LocalDate.now().withMonth(6)  // June -> extra
                .with(TemporalAdjusters.firstDayOfMonth());

        contract.setStartDate(january);
        addWorkOrdersToMechanic(june, interventions);
        Payroll p = new Payroll(contract, june);

        assertEquals(june, p.getDate());
        assertEquals(contract, p.getContract());

        assertEquals(expectedMonthlyWage, p.getMonthlyWage(), 0.01);
        assertEquals(expectedMonthlyWage, p.getBonus(), 0.01);// june -> extra
        assertEquals(productivity, p.getProductivityBonus(), 0.01);
        assertEquals(0.0, p.getTrienniumPayment(), 0.01);
        assertEquals(expectedNic, p.getNIC(), 0.01);
        assertEquals(irpf, p.getIncomeTax(), 0.01);
    }

    @Test
    public void testNovemberWithProductivityWithTriennium() {
        double workOrderAmount = 365.0;
        double productivity = workOrderAmount * productivityPercent / 100;
        double irpf =
                (expectedMonthlyWage + productivity + trienniumAmount) * 0.37;

        LocalDate januaryThreeYearsAgo =
                LocalDate.now().withMonth(1).minusYears(3)
                        .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate november = LocalDate.now().withMonth(11)
                .with(TemporalAdjusters.firstDayOfMonth());

        contract.setStartDate(januaryThreeYearsAgo);
        addWorkOrdersToMechanic(november, workOrderAmount);
        Payroll p = new Payroll(contract, november);

        assertEquals(november, p.getDate());
        assertEquals(contract, p.getContract());

        assertEquals(expectedMonthlyWage, p.getMonthlyWage(), 0.01);
        assertEquals(0.0, p.getBonus(), 0.01);
        assertEquals(productivity, p.getProductivityBonus(), 0.01);
        assertEquals(trienniumAmount, p.getTrienniumPayment(), 0.01);
        assertEquals(expectedNic, p.getNIC(), 0.01);
        assertEquals(irpf, p.getIncomeTax(), 0.01);
    }

    @Test
    public void testJuneWithProductivityWithTriennium() {
        double workOrderAmount = 365.0;
        double productivity = workOrderAmount * productivityPercent / 100;
        double irpf =
                (expectedMonthlyWage * 2 + productivity + trienniumAmount) *
                        0.37;

        LocalDate januaryThreeYearsAgo =
                LocalDate.now().withMonth(1).minusYears(3)
                        .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate june = LocalDate.now().withMonth(6)
                .with(TemporalAdjusters.firstDayOfMonth());

        contract.setStartDate(januaryThreeYearsAgo);
        addWorkOrdersToMechanic(june, workOrderAmount);
        Payroll p = new Payroll(contract, june);

        assertEquals(june, p.getDate());
        assertEquals(contract, p.getContract());
        assertEquals(expectedMonthlyWage, p.getMonthlyWage(), 0.01);
        assertEquals(expectedMonthlyWage, p.getBonus(), 0.01);// june -> extra
        assertEquals(productivity, p.getProductivityBonus(), 0.01);
        assertEquals(trienniumAmount, p.getTrienniumPayment(), 0.01);
        assertEquals(expectedNic, p.getNIC(), 0.01);
        assertEquals(p.getIncomeTax(), irpf, 0.01);
    }

    private void addWorkOrdersToMechanic(LocalDate month, double amount) {

        Vehicle v = new Vehicle("plate", "seat", "panda");

        WorkOrder wo = new WorkOrder(v, month.atStartOfDay());
        wo.assignTo(mechanic);
        wo.setStateForTesting(WorkOrderState.INVOICED);
        wo.setAmountForTesting(amount);
        new Intervention(mechanic, wo, month.atStartOfDay(), 10);
    }

}
