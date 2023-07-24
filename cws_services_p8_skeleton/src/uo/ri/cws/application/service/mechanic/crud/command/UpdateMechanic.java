package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class UpdateMechanic implements Command<Void> {

    private final MechanicDto dto;

    public UpdateMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotBlank(dto.id);
        ArgumentChecks.isNotBlank(dto.dni);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isNotBlank(dto.surname);

        this.dto = dto;
    }

    public Void execute() throws BusinessException {
        Optional<Mechanic> om =
                Factory.repository.forMechanic().findById(dto.id);
        BusinessChecks.isTrue(om.isPresent(), "Mechanic does not exist");

        Mechanic m = om.get();

        m.setName(dto.name);
        m.setSurname(dto.surname);

        // No hace falta hacer nada mas, el objeto m esta en estado persistent
        // Por lo tanto, se modifica directamente en la base de datos

        return null;
    }

}
