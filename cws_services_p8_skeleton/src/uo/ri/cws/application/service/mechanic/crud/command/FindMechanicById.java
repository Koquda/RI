package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

    private final String id;

    public FindMechanicById(String id) {
        // Comprobaciones
        ArgumentChecks.isNotBlank(id);
        this.id = id;
    }

    public Optional<MechanicDto> execute() throws BusinessException {
        Optional<Mechanic> om = Factory.repository.forMechanic().findById(id);
        if (om.isPresent()) {
            return Optional.of(DtoAssembler.toDto(om.get()));
        }
        return Optional.empty();
    }

}
