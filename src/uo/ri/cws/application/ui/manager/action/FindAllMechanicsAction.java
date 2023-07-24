package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.crud.command.FindAllMechanics;

import uo.ri.cws.application.ui.util.Printer;

public class FindAllMechanicsAction implements Action {


	
	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics \n");

		Printer.printMechanics(BusinessFactory.forMechanicCrudService().findAllMechanics()); // Comunicación UI - Lógica);

	}
}
