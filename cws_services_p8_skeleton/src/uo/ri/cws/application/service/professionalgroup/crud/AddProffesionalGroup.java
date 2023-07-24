package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class AddProffesionalGroup
        implements Command<ProfessionalGroupService.ProfessionalGroupBLDto> {

    private final ProfessionalGroupService.ProfessionalGroupBLDto dto;

    public AddProffesionalGroup(
            ProfessionalGroupService.ProfessionalGroupBLDto dto) {
        checkArgument(dto);
        this.dto = dto;
    }

    @Override
    public ProfessionalGroupService.ProfessionalGroupBLDto execute()
            throws BusinessException {
        BusinessChecks.isTrue(notExistMechanic(),
                "Repeated professional group");
        ProfessionalGroup group =
                new ProfessionalGroup(dto.name, dto.trieniumSalary,
                        dto.productivityRate);
        dto.id = group.getId();
        Factory.repository.forProfessionalGroup().add(group);
        return dto;
    }

    private boolean notExistMechanic() {
        return Factory.repository.forProfessionalGroup().findByName(dto.name)
                .isEmpty();
    }

    private void checkArgument(
            ProfessionalGroupService.ProfessionalGroupBLDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isTrue(dto.trieniumSalary > 0);
        ArgumentChecks.isNotEmpty(dto.name);
        ArgumentChecks.isTrue(dto.productivityRate > 0);
        ArgumentChecks.isNotBlank(dto.name);
    }
}
