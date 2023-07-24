package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService.*;
import uo.ri.cws.application.business.mechanic.crud.command.UpdateMechanic;

public class UpdateMechanicAction implements Action {



	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String id = Console.readString("Type mechahic id to update"); 
		String name = Console.readString("Name"); 
		String surname = Console.readString("Surname");
		
		// Process
		MechanicBLDto mechanicBLDto = new MechanicBLDto();
		mechanicBLDto.id = id;
		mechanicBLDto.name = name;
		mechanicBLDto.surname = surname;
		BusinessFactory.forMechanicCrudService().updateMechanic(mechanicBLDto); // Comunicación UI - Lógica
		
		// Print result
		Console.println("Mechanic updated");
	}

}
