package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Contract extends BaseEntity {
    private Mechanic mechanic;
    private Mechanic firedMechanic;
    private LocalDate startDate;
    private double annualBaseWage;
    private LocalDate endDate;
    private double settlement;
    private ContractType contractType;
    private Set<Payroll> payrolls = new HashSet<>();
    private ProfessionalGroup professionalGroup;
    private ContractState state;

    public Contract() {
    }

    public Contract(Mechanic mechanic, ContractType ct, ProfessionalGroup pg,
                    LocalDate endDate, double annualBaseWage) {
        ArgumentChecks.isNotNull(mechanic);
        ArgumentChecks.isNotNull(ct);
        ArgumentChecks.isNotNull(pg);
        ArgumentChecks.isTrue(annualBaseWage > 0);

        this.mechanic = mechanic;
        this.contractType = ct;
        this.professionalGroup = pg;
        this.startDate = getStartOfNextMonth();
        this.endDate = endDate;
        this.annualBaseWage = annualBaseWage;
        this.state = ContractState.IN_FORCE;
        Associations.Group.link(this, pg);
        Associations.Type.link(this, ct);
        Associations.Hire.link(this, mechanic);
    }


    public Contract(Mechanic mechanic, ContractType type,
                    ProfessionalGroup group, double wage) {
        this(mechanic, type, group, null, wage);
    }

    public static LocalDate getStartOfNextMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static LocalDate getEndOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    public Optional<Mechanic> getMechanic() {
        if (mechanic == null) {
            return Optional.empty();
        }
        return Optional.of(mechanic);
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public ProfessionalGroup getProfessionalGroup() {
        return professionalGroup;
    }

    public void setProfessionalGroup(ProfessionalGroup professionalGroup) {
        this.professionalGroup = professionalGroup;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Optional<LocalDate> getEndDate() {
        if (endDate == null) {
            return Optional.empty();
        }
        return Optional.of(endDate);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getAnnualBaseWage() {
        return annualBaseWage;
    }

    public void setAnnualBaseWage(double annualBaseWage) {
        this.annualBaseWage = annualBaseWage;
    }

    public ContractState getState() {
        return state;
    }

    public void setState(ContractState state) {
        this.state = state;
    }

    Set<Payroll> _getPayrolls() {
        return payrolls;
    }

    public HashSet<Payroll> getPayrolls() {
        return new HashSet<>(payrolls);
    }

    public void setPayrolls(HashSet<Payroll> payrolls) {
        this.payrolls = payrolls;
    }

    public double getSettlement() {
        return settlement;
    }

    public void setSettlement(double settlement) {
        this.settlement = settlement;
    }

    public Optional<Mechanic> getFiredMechanic() {
        if (firedMechanic == null) {
            return Optional.empty();
        }
        return Optional.of(firedMechanic);
    }

    public void setFiredMechanic(Mechanic firedMechanic) {
        this.firedMechanic = firedMechanic;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Contract other = (Contract) obj;
        return Objects.equals(mechanic, other.mechanic) &&
                Objects.equals(startDate, other.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mechanic, contractType, professionalGroup,
                startDate, endDate, annualBaseWage, state, settlement,
                payrolls);
    }

    public void terminate() {
        this.endDate = getEndOfMonth();
        if (Period.between(this.startDate, this.endDate).getYears() > 0) {
            this.settlement = calcularIndemnizacion();
        }
        Associations.Hire.unlink(this, mechanic);
        Associations.Fire.link(this, mechanic);
        this.state = ContractState.TERMINATED;
    }

    private double calcularIndemnizacion() {
        double sum = 0.0;
        for (Payroll payroll : payrolls) {
            int years = Period.between(payroll.getDate(), LocalDate.now())
                    .getYears();
            if (years < 1) {
                sum += payroll.getMonthlyWage() + payroll.getInitialBonus() +
                        payroll.getProductivityBonus() +
                        payroll.getTrienniumPayment();
            }
        }
        double a = sum / 365;

        double b = this.contractType.getCompensationDays();

        int c = Period.between(this.startDate, this.endDate).getYears();

        double indemnizacion = a * b * c;

        return Math.floor(indemnizacion * 100) / 100;
    }

    public enum ContractState {
        IN_FORCE, TERMINATED
    }


}