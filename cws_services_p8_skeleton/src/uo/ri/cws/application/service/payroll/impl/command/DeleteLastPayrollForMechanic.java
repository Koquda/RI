package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeleteLastPayrollForMechanic implements Command<Void> {
    private final String mechanicId;

    public DeleteLastPayrollForMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId);
        ArgumentChecks.isNotBlank(mechanicId);
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        Optional<Mechanic> mechanic =
                Factory.repository.forMechanic().findById(mechanicId);
        BusinessChecks.isTrue(mechanic.isPresent());
        deletePayrolls(mechanic.get());

        return null;
    }

    private void deletePayrolls(Mechanic mechanic) {
        List<Contract> listOfContractsOfMechanic =
                new ArrayList<>(mechanic.getTerminatedContracts());
        if (mechanic.getContractInForce().isPresent()) {
            listOfContractsOfMechanic.add(mechanic.getContractInForce().get());
        }
        for (Contract contract : listOfContractsOfMechanic) {
            for (Payroll p : contract.getPayrolls()) {
                if (isSameMonth(p.getDate(), LocalDate.now())) {
                    Factory.repository.forPayroll().remove(p);
                }
            }
        }

    }

    private boolean isSameMonth(LocalDate payrollDate, LocalDate currentDate) {
        int payrollYear = payrollDate.getYear();
        int currentYear = currentDate.getYear();
        int payrollMonth = payrollDate.getMonthValue();
        int currentMonth = currentDate.getMonthValue();

        return payrollYear == currentYear && payrollMonth == currentMonth;
    }


}
