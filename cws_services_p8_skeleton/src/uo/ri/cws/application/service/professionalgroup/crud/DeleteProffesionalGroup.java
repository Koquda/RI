package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class DeleteProffesionalGroup implements Command<Void> {

    private final String name;

    public DeleteProffesionalGroup(String name) {
        checkArgument(name);
        this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
        Optional<ProfessionalGroup> professionalGroupOpt =
                Factory.repository.forProfessionalGroup().findByName(name);
        BusinessChecks.isNotNull(professionalGroupOpt,
                "Professional group does not exist");
        BusinessChecks.isTrue(professionalGroupOpt.isPresent(),
                "Professional group is not present");
        BusinessChecks.isTrue(
                professionalGroupOpt.get().getContracts().isEmpty(),
                "Professional group has contracts in relation");
        Factory.repository.forProfessionalGroup()
                .remove(professionalGroupOpt.get());
        return null;
    }

    private void checkArgument(String name) {
        ArgumentChecks.isNotNull(name);
        ArgumentChecks.isNotEmpty(name);
        ArgumentChecks.isNotBlank(name);
    }
}
