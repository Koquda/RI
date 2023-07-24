package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.MechanicServiceImpl;
import uo.ri.cws.application.business.mechanic.crud.command.AddMechanic;
import uo.ri.cws.application.business.mechanic.MechanicService.*;

// Esta clase es la parte de UI de AddMechanic
public class AddMechanicAction implements Action {


	
	@Override
	public void execute() throws BusinessException {

		// Get info
		String dni = Console.readString("Dni");
		String name = Console.readString("Name");
		String surname = Console.readString("Surname");

		// Process
		MechanicBLDto mechanicBLDto = new MechanicBLDto();
		mechanicBLDto.dni = dni;
		mechanicBLDto.name = name;
		mechanicBLDto.surname = surname;
		BusinessFactory.forMechanicCrudService().addMechanic(mechanicBLDto); // Comunicación UI - Lógica

		// Print result
		Console.println("Mechanic added");
	}

}
