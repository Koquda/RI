package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class DeleteMechanic implements Command<Void> {

    private final String mechanicId;

    public DeleteMechanic(String mechanicId) {
        // Comprobaciones
        ArgumentChecks.isNotBlank(mechanicId);
        this.mechanicId = mechanicId;
    }

    public Void execute() throws BusinessException {
        Optional<Mechanic> op =
                Factory.repository.forMechanic().findById(mechanicId);
        BusinessChecks.isTrue(op.isPresent(), "Mechanic does not exist");
        BusinessChecks.isTrue(op.get().getAssigned().isEmpty(),
                "Mechanic has" + " workOrders assigned");
        BusinessChecks.isTrue(op.get().getTerminatedContracts().isEmpty(),
                "Mechanic has terminated contracts");
        BusinessChecks.isTrue(op.get().getContractInForce().isEmpty(),
                "Mechanic has a contract in force");

        Mechanic m = op.get();
        Factory.repository.forMechanic().remove(m);

        return null;
    }

}
