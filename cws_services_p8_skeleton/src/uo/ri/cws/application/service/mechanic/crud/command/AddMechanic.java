package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class AddMechanic implements Command<MechanicDto> {

    private final MechanicDto dto;

    public AddMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotBlank(dto.dni);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isNotBlank(dto.surname);

        this.dto = dto;
    }

    public MechanicDto execute() throws BusinessException {
        BusinessChecks.isTrue(
                Factory.repository.forMechanic().findByDni(dto.dni).isEmpty(),
                "There is already a mechanic with that dni");
        Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname); // esta en
        // transient
        Factory.repository.forMechanic().add(m); // se pasa a persistent
        dto.id = m.getId();

        return dto;
    }

}
