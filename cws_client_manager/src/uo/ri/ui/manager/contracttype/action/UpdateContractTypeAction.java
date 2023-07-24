package uo.ri.ui.manager.contracttype.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateContractTypeAction implements Action {
    @Override
    public void execute() throws BusinessException {

        // Get info
        String name = Console.readString("Name ");
        Double compensationDays = Console.readDouble("Compensation days ");

        // Process

        ContractTypeService.ContractTypeDto ct =
                new ContractTypeService.ContractTypeDto();
        ct.compensationDays = compensationDays;
        ct.name = name;

        Factory.service.forContractTypeService().updateContractType(ct);

        // Print result
        Console.println("Contract type succesfully updated");

    }
}
