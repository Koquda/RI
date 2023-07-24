package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.crud.command.DeleteMechanic;

public class DeleteMechanicAction implements Action {


	
	@Override
	public void execute() throws BusinessException {
		String idMechanic = Console.readString("Type mechanic id ");

		// Process
		BusinessFactory.forMechanicCrudService().deleteMechanic(idMechanic); // Comunicación UI - Lógica

		Console.println("Mechanic deleted");
	}

}
