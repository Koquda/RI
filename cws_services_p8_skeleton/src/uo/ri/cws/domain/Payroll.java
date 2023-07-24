package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

import java.time.LocalDate;

public class Payroll extends BaseEntity {
    private Contract contract;
    private LocalDate date;
    private double monthlyWage;
    private double bonus;
    private double productivityBonus;
    private double trienniumPayment;
    private double incomeTax;
    private double nic;
    private double netWage;

    public Payroll(Contract contract, LocalDate date, double monthlyWage,
                   double bonus, double productivity, double trienniums,
                   double tax, double nic) {
        ArgumentChecks.isNotNull(contract);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isTrue(monthlyWage >= 0);
        ArgumentChecks.isTrue(bonus >= 0);
        ArgumentChecks.isTrue(productivity >= 0);
        ArgumentChecks.isTrue(trienniums >= 0);
        ArgumentChecks.isTrue(tax >= 0);
        ArgumentChecks.isTrue(nic >= 0);

        this.contract = contract;
        this.date = date;
        this.monthlyWage = monthlyWage;
        this.bonus = bonus;
        this.productivityBonus = productivity;
        this.trienniumPayment = trienniums;
        this.incomeTax = tax;
        this.nic = nic;
        this.netWage =
                monthlyWage + bonus + productivity + trienniums - tax - nic;
        Associations.Run.link(this, contract);
    }

    public Payroll(Contract contract, LocalDate date) {
        this(contract, date, 0, 0, 0, 0, 0, 0);
        this.monthlyWage = contract.getAnnualBaseWage() / 14;
        this.bonus = getInitialBonus(date, contract);
        this.productivityBonus = getInitialProductivityBonus(date, contract);
        this.trienniumPayment = getInitialTriennialPayment(contract);
        this.incomeTax = getInitialIncomeTax(contract);
        this.nic = getInitialNic(contract);
    }

    public Payroll(Contract contract) {
        this(contract, LocalDate.now());
    }

    public Payroll() {

    }

    private double getInitialIncomeTax(Contract contract) {
        double rate = 0;
        double wage = contract.getAnnualBaseWage();
        if (wage > 0 && wage <= 12450) {
            rate = 0.19;
        } else if (wage >= 12450 && wage <= 20200) {
            rate = 0.24;
        } else if (wage >= 20200 && wage <= 35200) {
            rate = 0.30;
        } else if (wage >= 35200 && wage <= 60000) {
            rate = 0.37;
        } else if (wage >= 60000 && wage <= 300000) {
            rate = 0.45;
        } else {
            rate = 0.47;
        }
        return Round.twoCents(rate *
                (monthlyWage + bonus + productivityBonus + trienniumPayment));
    }

    private double getInitialNic(Contract contract) {
        return Round.twoCents(contract.getAnnualBaseWage() * 0.05 / 12);
    }

    private double getInitialTriennialPayment(Contract contract) {
        int contractYears = 0;
        int trienios = 0;
        double result = 0;
        if (contract.getState() == Contract.ContractState.IN_FORCE ||
                contract.getEndDate().isEmpty() ||
                checkIfIsOnSameMonth(contract.getEndDate().get())) {
            contractYears = date.getYear() - contract.getStartDate().getYear();

            trienios = contractYears / 3;

        }
        result = trienios *
                contract.getProfessionalGroup().getTrienniumPayment();
        return result;
    }

    private boolean checkIfIsOnSameMonth(LocalDate date) {
        return date.getYear() == date.getYear() &&
                date.getMonth() == date.getMonth();
    }

    private double getInitialBonus(LocalDate date, Contract contract) {
        if (date.getMonthValue() == 12 || date.getMonthValue() == 6) {
            return contract.getAnnualBaseWage() / 14;
        }
        return 0;
    }

    private double getInitialProductivityBonus(LocalDate date,
                                               Contract contract) {
        double totalAmount = 0;
        Mechanic mToCalculate;
        if (contract.getMechanic().isPresent()) {
            mToCalculate = contract.getMechanic().get();
        } else {
            mToCalculate = contract.getFiredMechanic().get();
        }

        for (WorkOrder workOrder : mToCalculate.getAssigned()) {
            totalAmount += workOrder.getAmount();
        }
        if (totalAmount != 0) {
            totalAmount = totalAmount * contract.getProfessionalGroup()
                    .getProductivityBonusPercentage() / 100;
        }
        return Math.round(totalAmount * 100d) / 100d;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        ArgumentChecks.isNotNull(date);
        this.date = date;
    }

    public double getMonthlyWage() {
        return monthlyWage;
    }

    public void setMonthlyWage(double monthlyWage) {
        ArgumentChecks.isNotNull(monthlyWage);
        this.monthlyWage = monthlyWage;
    }

    public double getInitialBonus() {
        return bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        ArgumentChecks.isNotNull(bonus);
        this.bonus = bonus;
    }

    public double getProductivityBonus() {
        return productivityBonus;
    }

    public void setProductivityBonus(double productivityBonus) {
        ArgumentChecks.isNotNull(productivityBonus);
        this.productivityBonus = productivityBonus;
    }

    public double getTrienniumPayment() {
        return trienniumPayment;
    }

    public void setTrienniumPayment(double trienniumPayment) {
        ArgumentChecks.isNotNull(trienniumPayment);
        this.trienniumPayment = trienniumPayment;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        ArgumentChecks.isNotNull(incomeTax);
        this.incomeTax = incomeTax;
    }

    public double getNIC() {
        return nic;
    }

    public void setNIC(double nic) {
        ArgumentChecks.isNotNull(nic);
        this.nic = nic;
    }

    public double getNetWage() {
        return netWage;
    }

    public void setNetWage(double netWage) {
        ArgumentChecks.isNotNull(netWage);
        this.netWage = netWage;
    }
}
