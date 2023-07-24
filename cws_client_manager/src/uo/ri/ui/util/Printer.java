package uo.ri.ui.util;

import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.vehicleType.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.util.console.Console;

import java.util.List;

public class Printer {

    public static void printInvoice(InvoiceDto invoice) {

        double importeConIVa = invoice.total;
        double iva = invoice.vat;
        double importeSinIva = importeConIVa / (1 + iva / 100);

        Console.printf("Invoice #: %d\n", invoice.number);
        Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", invoice.date);
        Console.printf("\tTotal: %.2f �\n", importeSinIva);
        Console.printf("\tTax: %.1f %% \n", invoice.vat);
        Console.printf("\tTotal, tax inc.: %.2f �\n", invoice.total);
        Console.printf("\tStatus: %s\n", invoice.state);
    }

    public static void printPaymentMeans(List<PaymentMeanDto> medios) {
        Console.println();
        Console.println("Available payment means");

        Console.printf("\t%s \t%-8.8s \t%s \n", "Id", "Type", "Acummulated");
        for (PaymentMeanDto medio : medios) {
            printPaymentMean(medio);
        }
    }

    private static void printPaymentMean(PaymentMeanDto medio) {
        Console.printf("\t%s \t%-8.8s \t%s \n", medio.id,
                medio.getClass().getName()  // not the best...
                , medio.accumulated);
    }

    public static void printWorkOrder(WorkOrderDto rep) {

        Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n",
                rep.id, rep.description, rep.date, rep.state, rep.total);
    }

    public static void printMechanic(MechanicDto m) {

        Console.printf("\t%s %-10.10s %-15.15s %-25.25s\n", m.id, m.dni, m.name,
                m.surname);
    }

    public static void printVehicleType(VehicleTypeDto vt) {

        Console.printf("\t%s %-10.10s %5.2f %d\n", vt.id, vt.name,
                vt.pricePerHour, vt.minTrainigHours);
    }

    public static void displayAllContracts(
            List<ContractService.ContractSummaryDto> allContracts) {
        for (ContractService.ContractSummaryDto c : allContracts)
            displayThisContractDetails(c);
    }

    public static void displayThisContractDetails(
            ContractService.ContractSummaryDto c) {
        Console.println("Identifier" + c.id);
        Console.print("\tDni: " + c.dni);
        Console.print("\tState: " + c.state);
        Console.print("\tSettlement: " + c.settlement);
    }

    public static void displayThisContractDetailsWithPayrolls(
            ContractService.ContractSummaryDto c) {
        Console.println("Identifier" + c.id);
        Console.print("\tDni: " + c.dni);
        Console.print("\tState: " + c.state);
        Console.print("\tSettlement: " + c.settlement);
        Console.print("\tNumber of payrolls: " + c.numPayrolls);
    }

    public static void displayThisContractDetails(
            ContractService.ContractDto contractDto) {
        Console.println("Identifier" + contractDto.id);
        Console.print("\tDni: " + contractDto.dni);
        Console.print("\tState: " + contractDto.state);
        Console.print("\tSettlement: " + contractDto.settlement);
        Console.print("\tContract Type Name: " + contractDto.contractTypeName);
        Console.print("\tProfessional Group Name: " +
                contractDto.professionalGroupName);
        Console.print("\tStart Date: " + contractDto.startDate);
        Console.print("\tEnd Date: " + contractDto.endDate);
    }

    public static void displayAllContractsDetailsWithPayrolls(
            List<ContractService.ContractSummaryDto> result) {
        for (ContractService.ContractSummaryDto c : result)
            displayThisContractDetailsWithPayrolls(c);
    }

    public static void printContractType(
            ContractTypeService.ContractTypeDto result) {
        Console.printf(result.id + "\t" + result.name + "\t" +
                result.compensationDays);
    }

    public static void printAllContractTypes(
            List<ContractTypeService.ContractTypeDto> allContractTypes) {
        for (ContractTypeService.ContractTypeDto c : allContractTypes)
            printContractType(c);
    }
}
