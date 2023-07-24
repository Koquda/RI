package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class TerminateContract implements Command<Void> {

    private final String id;

    public TerminateContract(String id) {
        ArgumentChecks.isNotBlank(id);
        this.id = id;
    }

    @Override
    public Void execute() throws BusinessException {
        Optional<Contract> oc = Factory.repository.forContract().findById(id);
        BusinessChecks.exists(oc, "Contract does not exist");
        Contract c = oc.get();
        BusinessChecks.isTrue(
                c.getState().equals(Contract.ContractState.IN_FORCE),
                "Contract is not in force");

        c.terminate();
        // TODO: despues del terminate hay que hacer algo mas?
        return null;
    }
}
