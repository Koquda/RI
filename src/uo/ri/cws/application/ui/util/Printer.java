package uo.ri.cws.application.ui.util;

import java.util.List;
import uo.ri.cws.application.business.mechanic.MechanicService.*;
import console.Console;

public class Printer {

	public static void printMechanic(MechanicBLDto m) {
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s %-10.2s\n", m.id, m.dni, m.name, m.surname, m.version);
	}

	public static void printMechanics(List<MechanicBLDto> list) {

		Console.printf("\t%-36s %-9s %-10s %-25s %-10s\n", "Mechanic identifier", "DNI", "Name", "Surname", "Version");
		for (MechanicBLDto m : list)
			printMechanic(m);
	}

	/*

	public static void printInvoice(Invoice_BLDto invoice) {

		double importeConIVa = invoice.total;
		double iva = invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice number: %d%n", invoice.number);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY%n", invoice.date);
		Console.printf("\tAmount: %.2f %n", importeSinIva);
		Console.printf("\tVat: %.1f %% %n", invoice.vat);
		Console.printf("\tTotal (vat included): %.2f %n", invoice.total);
		Console.printf("\tState: %s%n", invoice.state);
	}

	public static void printInvoicingWorkOrder(InvoicingWorkOrder_BLDto arg) {
		
		Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n",  
				arg.id
				, arg.description 
				, arg.date
				, arg.state
				, arg.total
			);		
	}

	public static void printInvoicingWorkOrders(List<InvoicingWorkOrder_BLDto> arg) {
		Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%s\n",  
				"Identifier", "description", "date", "state", "total");
		for (InvoicingWorkOrder_BLDto inv : arg)
			printInvoicingWorkOrder(inv);
	}
	
	*/
}
