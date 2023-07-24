package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

import java.util.List;

public class FindMechanicsInForce
        implements Command<List<MechanicCrudService.MechanicDto>> {
    @Override
    public List<MechanicCrudService.MechanicDto> execute()
            throws BusinessException {
        List<Mechanic> mechanics =
                Factory.repository.forMechanic().findAllInForce();
        return DtoAssembler.toMechanicDtoList(mechanics);
    }
}
