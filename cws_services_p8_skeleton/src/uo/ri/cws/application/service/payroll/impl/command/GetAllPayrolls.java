package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

import java.util.List;

public class GetAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
        return DtoAssembler.toPayrollSummaryBLDtoList(
                Factory.repository.forPayroll().findAll());
    }

}
