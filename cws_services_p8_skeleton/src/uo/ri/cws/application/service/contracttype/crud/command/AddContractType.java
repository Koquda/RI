package uo.ri.cws.application.service.contracttype.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class AddContractType
        implements Command<ContractTypeService.ContractTypeDto> {

    private final ContractTypeService.ContractTypeDto dto;

    public AddContractType(ContractTypeService.ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isTrue(dto.compensationDays >= 0);

        this.dto = dto;
    }

    @Override
    public ContractTypeService.ContractTypeDto execute()
            throws BusinessException {
        // Checks if the contract type already exists
        BusinessChecks.isTrue(
                Factory.repository.forContractType().findById(dto.id)
                        .isEmpty());
        BusinessChecks.isTrue(
                Factory.repository.forContractType().findByName(dto.name)
                        .isEmpty());
        // Adds the contract type
        ContractType ct = new ContractType(dto.name, dto.compensationDays);
        Factory.repository.forContractType().add(ct);
        dto.id = ct.getId();

        return dto;
    }

}
