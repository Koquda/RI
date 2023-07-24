package uo.ri.ui.manager.contracttype.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddContractTypeAction implements Action {
    @Override
    public void execute() throws BusinessException {
        // Get info
        String name = Console.readString("Name ");
        Double compensationDays = Console.readDouble("Compensation days ");

        // Process

        ContractTypeDto ct = new ContractTypeDto();
        ct.compensationDays = compensationDays;
        ct.name = name;

        ContractTypeDto result =
                Factory.service.forContractTypeService().addContractType(ct);


        // Print result
        Console.println("New contract type succesfully added");
        Printer.printContractType(result);
    }
}
