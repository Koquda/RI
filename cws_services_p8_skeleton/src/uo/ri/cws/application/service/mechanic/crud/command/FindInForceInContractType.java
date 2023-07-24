package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindInForceInContractType
        implements Command<List<MechanicCrudService.MechanicDto>> {

    private final String contractTypeName;

    public FindInForceInContractType(String contractTypeName) {
        checkArgs(contractTypeName);
        this.contractTypeName = contractTypeName;
    }

    @Override
    public List<MechanicCrudService.MechanicDto> execute()
            throws BusinessException {
        Optional<ContractType> oct = Factory.repository.forContractType()
                .findByName(contractTypeName);
        if (oct.isEmpty()) {
            return new ArrayList<>();
        }
        return DtoAssembler.toMechanicDtoList(Factory.repository.forMechanic()
                .findInForceInContractType(oct.get()));
    }

    private void checkArgs(String contractType) {
        ArgumentChecks.isNotBlank(contractType);
    }


}
