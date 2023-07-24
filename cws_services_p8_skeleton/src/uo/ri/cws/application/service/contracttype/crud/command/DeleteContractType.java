package uo.ri.cws.application.service.contracttype.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteContractType implements Command<Void> {

    private final String name;

    public DeleteContractType(String name) {
        ArgumentChecks.isNotNull(name);
        ArgumentChecks.isNotBlank(name);

        this.name = name;
    }


    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.isTrue(
                Factory.repository.forContractType().findByName(name)
                        .isPresent());
        ContractType contractType =
                Factory.repository.forContractType().findByName(name).get();
        BusinessChecks.isTrue(contractType.getContracts().isEmpty());

        Factory.repository.forContractType().remove(contractType);
        return null;
    }
}
