package uo.ri.cws.application.service.contracttype.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class FindContractTypeByName
        implements Command<Optional<ContractTypeService.ContractTypeDto>> {

    private final String name;

    public FindContractTypeByName(String name) {
        ArgumentChecks.isNotBlank(name);

        this.name = name;
    }

    @Override
    public Optional<ContractTypeService.ContractTypeDto> execute()
            throws BusinessException {
        Optional<ContractType> contractType =
                Factory.repository.forContractType().findByName(name);
        if (contractType.isPresent()) {
            return Optional.of(DtoAssembler.toDto(contractType.get()));
        }
        return Optional.empty();
    }
}
