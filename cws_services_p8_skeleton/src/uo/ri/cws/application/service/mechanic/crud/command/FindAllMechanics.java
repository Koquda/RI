package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

import java.util.ArrayList;
import java.util.List;

public class FindAllMechanics implements Command<List<MechanicDto>> {

    public List<MechanicDto> execute() {
        List<Mechanic> mechanics = new ArrayList<>();
        mechanics.addAll(Factory.repository.forMechanic().findAll());
        return DtoAssembler.toMechanicDtoList(mechanics);
    }

}
