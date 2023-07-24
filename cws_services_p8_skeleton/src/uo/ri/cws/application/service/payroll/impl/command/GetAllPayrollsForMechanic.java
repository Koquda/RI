package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetAllPayrollsForMechanic
        implements Command<List<PayrollSummaryBLDto>> {
    private final String idMechanic;

    public GetAllPayrollsForMechanic(String idMechanic) {
        checkArgument(idMechanic);
        this.idMechanic = idMechanic;
    }


    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
        Optional<Mechanic> op =
                Factory.repository.forMechanic().findById(idMechanic);
        BusinessChecks.isTrue(op.isPresent());
        Mechanic m = op.get();
        Optional<Contract> contractInForceOpt = m.getContractInForce();
        List<Payroll> payrolls = new ArrayList<>();

        m.getTerminatedContracts()
                .forEach(c -> payrolls.addAll(c.getPayrolls()));

        contractInForceOpt.ifPresent(
                contract -> payrolls.addAll(contract.getPayrolls()));

        return DtoAssembler.toPayrollSummaryBLDtoList(payrolls);
    }


    private void checkArgument(String idMechanic) {
        ArgumentChecks.isNotNull(idMechanic);
        ArgumentChecks.isNotBlank(idMechanic);
    }

}
