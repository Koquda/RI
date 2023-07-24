package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class FindProfessionalGroupByName implements
        Command<Optional<ProfessionalGroupService.ProfessionalGroupBLDto>> {

    private final String name;

    public FindProfessionalGroupByName(String name) {
        ArgumentChecks.isNotBlank(name);
        this.name = name;
    }


    @Override
    public Optional<ProfessionalGroupService.ProfessionalGroupBLDto> execute()
            throws BusinessException {
        return Factory.repository.forProfessionalGroup().findByName(name)
                .map(DtoAssembler::toDto);
    }
}
