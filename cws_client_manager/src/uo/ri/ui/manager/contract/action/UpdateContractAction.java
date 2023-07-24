package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.time.LocalDate;

public class UpdateContractAction implements Action {
    @Override
    public void execute() throws BusinessException {
        Printer.displayAllContracts(
                Factory.service.forContractService().findAllContracts());
        String id = Console.readString("Type contract id ");
        LocalDate endD = Console.readDate("Type contract start date ");
        double salary = Console.readDouble("Type annual base wage ");

        ContractService.ContractDto cdto = new ContractService.ContractDto();
        cdto.id = id;
        cdto.endDate = endD;
        cdto.annualBaseWage = salary;

        Factory.service.forContractService().updateContract(cdto);


        Console.println("Contract updated");
    }
}
