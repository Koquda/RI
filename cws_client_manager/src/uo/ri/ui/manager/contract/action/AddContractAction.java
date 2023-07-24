package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.domain.Contract;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class AddContractAction implements Action {
    public static LocalDate getStartOfNextMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
    }

    @Override
    public void execute() throws BusinessException {
        String mechanicdNI = Console.readString("Mechanic DNI ");
        LocalDate startDate = getStartOfNextMonth();
        Console.println("Contract type");
        Console.println("PERMANENT \t SEASONAL \t FIXED_TERM");
        String typeName = Console.readString("Contract type name ");
        LocalDate endDate = null;
        if (typeName.toUpperCase().compareTo("PERMANENT") != 0) {
            endDate = Console.readDate("Type end date ");
        }

        Console.println("Professional group");
        Console.println("I \t II \t III \t IV \t V \t VI \t VII");
        String categoryName = Console.readString("Professional group name ");
        double yearBaseSalary = Console.readDouble("Annual base salary");

        ContractDto dto = new ContractDto();
        dto.dni = mechanicdNI;
        dto.contractTypeName = typeName;
        dto.professionalGroupName = categoryName;
        dto.startDate = startDate;
        dto.endDate = endDate;
        dto.annualBaseWage = yearBaseSalary;
        dto.state = Contract.ContractState.IN_FORCE;

        Factory.service.forContractService().addContract(dto);


        Console.println("Contract registered");
    }

}
