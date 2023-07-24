package uo.ri.cws.application.service.contracttype.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class UpdateContractType implements Command<Void> {

    private final ContractTypeService.ContractTypeDto dto;

    public UpdateContractType(ContractTypeService.ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isTrue(dto.compensationDays > 0);

        this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
        Optional<ContractType> oct =
                Factory.repository.forContractType().findByName(dto.name);
        BusinessChecks.isTrue(oct.isPresent(), "Contract type does not exist");

        ContractType contractType = oct.get();

        contractType.setCompensationDays(dto.compensationDays);
        // TODO: mirar si hay que cambiar algo mas a parte del compensation Days

        return null;
    }
}
