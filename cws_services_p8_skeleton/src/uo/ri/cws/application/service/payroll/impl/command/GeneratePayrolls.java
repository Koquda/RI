package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Payroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneratePayrolls implements Command<Void> {
    private final LocalDate present;

    public GeneratePayrolls(LocalDate present) {
        this.present = Objects.requireNonNullElseGet(present, LocalDate::now);
    }

    public GeneratePayrolls() {
        this.present = LocalDate.now();
    }

    @Override
    public Void execute() throws BusinessException {
        createPayrrols(getListOfContractsToPayroll());
        return null;
    }

    private void createPayrrols(List<Contract> listOfContractsToPayroll) {
        listOfContractsToPayroll.forEach(c -> Factory.repository.forPayroll()
                .add(new Payroll(c, present)));
    }

    private List<Contract> getListOfContractsToPayroll() {
        List<Contract> result = new ArrayList<>();
        for (Contract contract : Factory.repository.forContract().findAll()) {
            if (contract.getMechanic().isEmpty() &&
                    contract.getFiredMechanic().isEmpty()) {
                result.add(contract);
            } else if (contract.getState() == ContractState.IN_FORCE ||
                    (contract.getEndDate().isPresent() &&
                            isSameMonth(contract.getEndDate().get()))) {
                if (!checkIfAnyPayrollGeneratedThisMonth(contract)) {

                    result.add(contract);
                }
            }
        }

        return result;

    }

    private boolean checkIfAnyPayrollGeneratedThisMonth(Contract contract) {
        return contract.getPayrolls().stream().map(Payroll::getDate)
                .anyMatch(this::isSameMonth);
    }

    private boolean isSameMonth(LocalDate date) {
        return (date.getYear() == present.getYear() &&
                date.getMonth() == present.getMonth());

    }

}
