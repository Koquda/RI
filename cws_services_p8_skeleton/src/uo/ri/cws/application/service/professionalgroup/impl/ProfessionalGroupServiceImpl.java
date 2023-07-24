package uo.ri.cws.application.service.professionalgroup.impl;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.professionalgroup.crud.*;
import uo.ri.cws.application.util.command.CommandExecutor;

import java.util.List;
import java.util.Optional;

public class ProfessionalGroupServiceImpl implements ProfessionalGroupService {

    private final CommandExecutor executor = Factory.executor.forExecutor();

    @Override
    public ProfessionalGroupBLDto addProfessionalGroup(
            ProfessionalGroupBLDto dto) throws BusinessException {
        return executor.execute(new AddProffesionalGroup(dto));
    }

    @Override
    public void deleteProfessionalGroup(String name) throws BusinessException {
        executor.execute(new DeleteProffesionalGroup(name));
    }

    @Override
    public void updateProfessionalGroup(ProfessionalGroupBLDto dto)
            throws BusinessException {
        executor.execute(new UpdateProfessionalGroup(dto));
    }

    @Override
    public Optional<ProfessionalGroupBLDto> findProfessionalGroupByName(
            String id) throws BusinessException {
        return executor.execute(new FindProfessionalGroupByName(id));
    }

    @Override
    public List<ProfessionalGroupBLDto> findAllProfessionalGroups()
            throws BusinessException {
        return executor.execute(new FindAllProffesionalGroup());
    }
}
