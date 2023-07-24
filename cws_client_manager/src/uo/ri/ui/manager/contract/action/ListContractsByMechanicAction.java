package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListContractsByMechanicAction implements Action {
    @Override
    public void execute() throws Exception {
        String idM = Console.readString("Type mechanic dni ");
        List<ContractService.ContractSummaryDto> result =
                Factory.service.forContractService()
                        .findContractsByMechanicDni(idM);


        if (!result.isEmpty()) {
            Printer.displayAllContractsDetailsWithPayrolls(result);
        } else {
            Console.println(
                    "There are no contracts for a mechanic with this dni");
        }

    }
}
