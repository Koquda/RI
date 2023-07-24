package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;

import java.util.HashSet;

public class ProfessionalGroup extends BaseEntity {
    private String name;
    private double trienniumSalary;
    private double productivityRate;
    private HashSet<Contract> contracts = new HashSet<>();

    public ProfessionalGroup(String name, double trienniumSalary,
                             double productivityRate) {
        this.name = name;
        this.trienniumSalary = trienniumSalary;
        this.productivityRate = productivityRate;
    }

    public ProfessionalGroup() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTrienniumSalary() {
        return trienniumSalary;
    }

    public void setTrienniumSalary(double trieniumSalary) {
        this.trienniumSalary = trieniumSalary;
    }

    public double getProductivityRate() {
        return productivityRate;
    }

    public void setProductivityRate(double productivityRate) {
        this.productivityRate = productivityRate;
    }

    HashSet<Contract> _getContracts() {
        return contracts;
    }

    public HashSet<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

    public void setContracts(HashSet<Contract> contracts) {
        this.contracts = contracts;
    }

    public double getProductivityBonusPercentage() {
        return productivityRate;
    }

    public double getTrienniumPayment() {
        return trienniumSalary;
    }
}
