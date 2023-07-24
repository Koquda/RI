package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateProfessionalGroup implements Command<Void> {
    private final ProfessionalGroupService.ProfessionalGroupBLDto dto;
    private final ProfessionalGroupRepository professionalGroupRepository =
            Factory.repository.forProfessionalGroup();

    public UpdateProfessionalGroup(
            ProfessionalGroupService.ProfessionalGroupBLDto dto) {
        checkArgument(dto);
        this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.isTrue(
                professionalGroupRepository.findByName(dto.name).isPresent(),
                "Professional group does not exist.");

        ProfessionalGroup professionalGroup =
                professionalGroupRepository.findById(dto.id).get();

        BusinessChecks.hasVersion(professionalGroup, dto.version);

        professionalGroup.setName(dto.name);
        professionalGroup.setTrienniumSalary(dto.trieniumSalary);
        professionalGroup.setProductivityRate(dto.productivityRate);

        return null;
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
