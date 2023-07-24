package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class ListContractByIdAction implements Action {
    @Override
    public void execute() throws BusinessException {
        // Printer.displayAllContracts(Factory.service.forContractService()
        // .findAllContracts());

        String id = Console.readString("Type id");

        Optional<ContractService.ContractDto> opContract =
                Factory.service.forContractService().findContractById(id);


        if (opContract.isPresent()) {
            Printer.displayThisContractDetails(opContract.get());
        } else {
            Console.println("There is no contract with this id");
        }

    }
}
