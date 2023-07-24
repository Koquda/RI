package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;

import java.util.HashSet;

public class ContractType extends BaseEntity {
    private final HashSet<Contract> contracts = new HashSet<>();
    private String name;
    private double compensationDays;

    public ContractType(String name, double compensationDays) {
        this.name = name;
        this.compensationDays = compensationDays;
    }

    public ContractType() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCompensationDays() {
        return compensationDays;
    }

    public void setCompensationDays(double compensationDays) {
        this.compensationDays = compensationDays;
    }

    HashSet<Contract> _getContracts() {
        return contracts;
    }

    public HashSet<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

}
