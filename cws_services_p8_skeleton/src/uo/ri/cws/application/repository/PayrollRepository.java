package uo.ri.cws.application.repository;

import uo.ri.cws.domain.Payroll;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends Repository<Payroll> {


    List<Payroll> findByContract(String contractId);

    List<Payroll> findCurrentMonthPayrolls();

    Optional<Payroll> findCurrentMonthByContractId(String contractId);
}
