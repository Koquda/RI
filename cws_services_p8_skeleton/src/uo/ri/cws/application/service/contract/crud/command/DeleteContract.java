package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class DeleteContract implements Command<Void> {

    private final String id;


    public DeleteContract(String id) {
        ArgumentChecks.isNotBlank(id);

        this.id = id;
    }


    @Override
    public Void execute() throws BusinessException {
        // Getting the optionals and checking if they exist
        Optional<Contract> opContract =
                Factory.repository.forContract().findById(id);
        BusinessChecks.exists(opContract, "Contract does not exist");
        Contract contract = opContract.get();
        if (!contract.getState().equals(Contract.ContractState.TERMINATED)) {
            BusinessChecks.isTrue(
                    contract.getMechanic().get().getAssigned().size() <= 0,
                    "Mechanic has assigned work orders");
        }
        BusinessChecks.isTrue(contract.getPayrolls().size() <= 0,
                "Contract " + "has payrolls");

        Factory.repository.forContract().remove(contract);

        return null;
    }
}
