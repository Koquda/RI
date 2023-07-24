package uo.ri.ui.manager.contracttype.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListAllContractTypeAction implements Action {
    @Override
    public void execute() throws BusinessException {
        Console.println("\nList of contract types \n");

        Printer.printAllContractTypes(Factory.service.forContractTypeService()
                .findAllContractTypes());
    }
}
