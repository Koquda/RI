package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Optional;

public class UpdateContract implements Command<Void> {

    private final ContractService.ContractDto dto;

    public UpdateContract(ContractService.ContractDto dto) {
        checkArgs(dto);
        this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
        Optional<Contract> oc =
                Factory.repository.forContract().findById(dto.id);
        BusinessChecks.exists(oc, "Contract does not exist");
        Contract c = oc.get();
        BusinessChecks.isTrue(c.getState() == Contract.ContractState.IN_FORCE,
                "Contract is not in force");
        if (c.getContractType().getName().equalsIgnoreCase("FIXED_TERM") &&
                dto.endDate != null) {
            BusinessChecks.isTrue(dto.endDate.isAfter(c.getStartDate()),
                    "End" + " date must be after current end date");
        }

        if (dto.endDate != null) {
            c.setEndDate(dto.endDate);
        }
        c.setAnnualBaseWage(dto.annualBaseWage);


        return null;
    }

    private void checkArgs(ContractService.ContractDto dto) {
        ArgumentChecks.isNotNull(dto, "Dto is null");
        ArgumentChecks.isNotBlank(dto.id, "Id is null");
        ArgumentChecks.isTrue(dto.annualBaseWage > 0,
                "Annual base wage is " + "not positive");
    }
}
