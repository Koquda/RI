package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class AddContract implements Command<ContractService.ContractDto> {

    private final ContractService.ContractDto dto;
    private final ContractRepository contractRepo =
            Factory.repository.forContract();

    public AddContract(ContractService.ContractDto dto)
            throws BusinessException {
        checkArgs(dto);
        this.dto = dto;
    }

    @Override
    public ContractService.ContractDto execute() throws BusinessException {
        // Getting the optionals
        Optional<ContractType> oct = Factory.repository.forContractType()
                .findByName(dto.contractTypeName);
        Optional<Mechanic> om =
                Factory.repository.forMechanic().findByDni(dto.dni);
        Optional<ProfessionalGroup> opg =
                Factory.repository.forProfessionalGroup()
                        .findByName(dto.professionalGroupName);

        // Checking if the optionals are present
        BusinessChecks.exists(oct, "Contract type does not exist");
        BusinessChecks.exists(om, "Mechanic does not exist");
        BusinessChecks.exists(opg, "Professional group does not exist");

        // If the endDate is not null then it checks if the endDate is after
        // the startDate
        if (dto.endDate != null) {
            BusinessChecks.isTrue(dto.endDate.isAfter(dto.startDate),
                    "End " + "date must be after start date");
        }

        // Getting the object to create the contract
        ContractType contractType = oct.get();
        Mechanic mechanic = om.get();
        ProfessionalGroup professionalGroup = opg.get();


        // If there is another contract in force
        if (mechanic.isInForce()) {
            // Terminate contract in force and compute settlement
            // Contract startDate will be the first day of the next month
            // Everything is done in the terminate method
            mechanic.getContractInForce().get().terminate();
        }

        // Create the contract
        Contract contract =
                new Contract(mechanic, contractType, professionalGroup,
                        dto.startDate, dto.annualBaseWage);
        contractRepo.add(contract);
        dto.id = contract.getId();

        return dto;
    }

    private void checkArgs(ContractService.ContractDto dto)
            throws BusinessException {
        ArgumentChecks.isNotNull(dto, "Dto es null");
        ArgumentChecks.isNotBlank(dto.contractTypeName,
                "ContractTypeName is " + "blank");
        ArgumentChecks.isNotBlank(dto.dni, "Dni is blank");
        ArgumentChecks.isNotNull(dto.startDate, "StartDate es null");
        ArgumentChecks.isNotBlank(dto.professionalGroupName,
                "ProfessionalGroupName is blank");

        if (dto.endDate != null) {
            BusinessChecks.isTrue(dto.startDate.isBefore(dto.endDate),
                    "EndDate es anterior a StartDate");
            ArgumentChecks.isTrue(
                    dto.professionalGroupName.equalsIgnoreCase("FIXED_TERM"));
        }
        ArgumentChecks.isTrue(dto.annualBaseWage > 0,
                "AnnualBaseWage es " + "menor o igual a 0");
    }
}
