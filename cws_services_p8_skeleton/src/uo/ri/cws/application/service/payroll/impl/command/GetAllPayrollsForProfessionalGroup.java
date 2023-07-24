package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetAllPayrollsForProfessionalGroup
        implements Command<List<PayrollSummaryBLDto>> {
    private final String name;

    public GetAllPayrollsForProfessionalGroup(String name) {
        checkArgument(name);
        this.name = name;
    }

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
        Optional<ProfessionalGroup> professionalGroupOpt =
                Factory.repository.forProfessionalGroup().findByName(name);

        BusinessChecks.isTrue(professionalGroupOpt.isPresent());

        ProfessionalGroup professionalGroup = professionalGroupOpt.get();

        return DtoAssembler.toPayrollSummaryBLDtoList(
                getPayrollsToList(professionalGroup));
    }

    private List<Payroll> getPayrollsToList(
            ProfessionalGroup professionalGroup) {
        return professionalGroup.getContracts().stream()
                .flatMap(c -> c.getPayrolls().stream())
                .collect(Collectors.toList());
    }

    private void checkArgument(String name) {
        ArgumentChecks.isNotNull(name);
        ArgumentChecks.isNotBlank(name);
    }

}
