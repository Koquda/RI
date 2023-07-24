package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindMechanicsinProfessionalGroups
        implements Command<List<MechanicCrudService.MechanicDto>> {

    private final String professionalGroupId;
    private final MechanicRepository mechanicRepository =
            Factory.repository.forMechanic();
    private final ProfessionalGroupRepository professionalGroupRepository =
            Factory.repository.forProfessionalGroup();

    public FindMechanicsinProfessionalGroups(String name) {
        argumentChecks(name);
        this.professionalGroupId = name;
    }

    @Override
    public List<MechanicCrudService.MechanicDto> execute()
            throws BusinessException {
        List<MechanicCrudService.MechanicDto> result = new ArrayList<>();
        Optional<ProfessionalGroup> pg =
                professionalGroupRepository.findByName(professionalGroupId);
        return pg.map(professionalGroup -> DtoAssembler.toMechanicDtoList(
                mechanicRepository.findAllInProfessionalGroup(
                        professionalGroup))).orElse(result);
    }

    private void argumentChecks(String name) {
        ArgumentChecks.isNotNull(name, "Invalid name, cannot be null");
        ArgumentChecks.isNotBlank(name, "Invalid name, cannot be empty");
        ArgumentChecks.isNotEmpty(name, "Invalid name, cannot be empty");
    }
}
