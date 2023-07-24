package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.util.List;

public class ListAllContractAction implements Action {
    @Override
    public void execute() throws BusinessException {
        Console.println("\nPrinting contracts summary\n");
        List<ContractSummaryDto> lc =
                Factory.service.forContractService().findAllContracts();


        if (lc.isEmpty()) {
            Console.print("There is no Contract ");
        } else {
            for (ContractSummaryDto c : lc) {
                Printer.displayThisContractDetailsWithPayrolls(c);
            }
        }
    }
}
