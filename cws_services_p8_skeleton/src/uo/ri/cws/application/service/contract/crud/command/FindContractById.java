package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class FindContractById
        implements Command<Optional<ContractService.ContractDto>> {

    private final String id;

    public FindContractById(String id) {
        ArgumentChecks.isNotBlank(id);
        this.id = id;
    }


    @Override
    public Optional<ContractService.ContractDto> execute()
            throws BusinessException {
        Optional<Contract> oc = Factory.repository.forContract().findById(id);
        if (oc.isPresent()) {
            Contract c = oc.get();
            ContractService.ContractDto contractDto =
                    DtoAssembler.toDto(oc.get());
            if (contractDto.dni == null) {
                contractDto.dni = c.getFiredMechanic().get().getDni();
            }
            return Optional.of(contractDto);
        }
        return Optional.empty();
    }
}
