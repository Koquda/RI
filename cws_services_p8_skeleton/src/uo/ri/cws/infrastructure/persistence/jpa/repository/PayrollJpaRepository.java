package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
        implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
        return null;
    }

    @Override
    public List<Payroll> findCurrentMonthPayrolls() {
        LocalDate unMesAtras = LocalDate.now();
        unMesAtras.minusMonths(1);
        return Jpa.getManager()
                .createNamedQuery("Payroll" + ".findCurrentMonthPayrolls",
                        Payroll.class).setParameter(1, LocalDate.now())
                .setParameter(2, unMesAtras).getResultList();
    }

    @Override
    public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
        return Optional.empty();
    }

}
