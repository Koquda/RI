package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class GetPayrollDetails implements Command<Optional<PayrollBLDto>> {
    private final String id;

    public GetPayrollDetails(String id) {
        checkArgument(id);
        this.id = id;
    }

    @Override
    public Optional<PayrollBLDto> execute() throws BusinessException {
        Optional<Payroll> op = Factory.repository.forPayroll().findById(id);
        return op.map(DtoAssembler::toDto);
    }

    private void checkArgument(String id) {
        ArgumentChecks.isNotNull(id);
        ArgumentChecks.isNotBlank(id);
    }

}
