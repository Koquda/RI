package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindContractsByMechanic
        implements Command<List<ContractSummaryDto>> {

    private final String mechanicDni;

    public FindContractsByMechanic(String mechanicDni) {
        ArgumentChecks.isNotBlank(mechanicDni);
        this.mechanicDni = mechanicDni;
    }

    @Override
    public List<ContractSummaryDto> execute() throws BusinessException {
        Optional<Mechanic> om =
                Factory.repository.forMechanic().findByDni(mechanicDni);
        if (om.isEmpty()) {
            return new ArrayList<>();
        }
        Mechanic mechanic = om.get();
        List<Contract> contracts =
                new ArrayList<>(mechanic.getTerminatedContracts());
        if (mechanic.getContractInForce().isPresent()) {
            contracts.add(mechanic.getContractInForce().get());
        }
        List<ContractSummaryDto> summaryDtos =
                DtoAssembler.toContractSummaryDtoList(contracts);
        summaryDtos.forEach(c -> c.dni = mechanicDni);
        return summaryDtos;
    }
}
