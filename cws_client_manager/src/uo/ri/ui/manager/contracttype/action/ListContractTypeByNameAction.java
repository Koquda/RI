package uo.ri.ui.manager.contracttype.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class ListContractTypeByNameAction implements Action {
    @Override
    public void execute() throws BusinessException {

        String name = Console.readString("Contract type name ");

        Optional<ContractTypeService.ContractTypeDto> ct =
                Factory.service.forContractTypeService()
                        .findContractTypeByName(name);

        Printer.printContractType(ct.get());
    }
}
