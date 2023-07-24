package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

import java.util.List;

public class FindAllProffesionalGroup implements
        Command<List<ProfessionalGroupService.ProfessionalGroupBLDto>> {

    @Override
    public List<ProfessionalGroupService.ProfessionalGroupBLDto> execute()
            throws BusinessException {
        return DtoAssembler.toProfessionalGroupList(
                Factory.repository.forProfessionalGroup().findAll());
    }
}
