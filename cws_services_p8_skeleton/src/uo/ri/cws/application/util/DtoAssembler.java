package uo.ri.cws.application.util;

import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.invoice.InvoicingService.*;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicleType.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.cws.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoAssembler {

    public static PayrollService.PayrollSummaryBLDto toSummaryDto(Payroll v) {
        PayrollService.PayrollSummaryBLDto dto =
                new PayrollService.PayrollSummaryBLDto();
        dto.id = v.getId();
        dto.version = v.getVersion();
        dto.date = v.getDate();
        dto.netWage = getNetWage(v);
        return dto;
    }

    public static PayrollService.PayrollBLDto toDto(Payroll v) {
        PayrollService.PayrollBLDto dto = new PayrollService.PayrollBLDto();
        dto.id = v.getId();
        dto.version = v.getVersion();
        dto.bonus = v.getBonus();
        dto.trienniumPayment = v.getTrienniumPayment();
        dto.date = v.getDate();
        dto.contractId = v.getContract().getId();
        dto.productivityBonus = v.getProductivityBonus();
        dto.incomeTax = v.getIncomeTax();
        dto.monthlyWage = v.getMonthlyWage();
        dto.netWage = getNetWage(v);
        dto.nic = v.getNIC();
        return dto;
    }

    private static double getNetWage(Payroll v) {

        return v.getMonthlyWage() + v.getBonus() + v.getProductivityBonus() +
                v.getTrienniumPayment() - v.getIncomeTax() - v.getNIC();
    }

    public static List<PayrollService.PayrollSummaryBLDto> toPayrollSummaryBLDtoList(
            List<Payroll> list) {
        return list.stream().map(DtoAssembler::toSummaryDto)
                .collect(Collectors.toList());
    }

    public static ClientDto toDto(Client c) {
        ClientDto dto = new ClientDto();

        dto.id = c.getId();
        dto.version = c.getVersion();

        dto.dni = c.getDni();
        dto.name = c.getName();
        dto.surname = c.getSurname();

        return dto;
    }

    public static List<ClientDto> toClientDtoList(List<Client> clientes) {
        List<ClientDto> res = new ArrayList<>();
        for (Client c : clientes) {
            res.add(DtoAssembler.toDto(c));
        }
        return res;
    }

    public static MechanicDto toDto(Mechanic m) {
        MechanicDto dto = new MechanicDto();
        dto.id = m.getId();
        dto.version = m.getVersion();

        dto.dni = m.getDni();
        dto.name = m.getName();
        dto.surname = m.getSurname();
        return dto;
    }

    public static List<MechanicDto> toMechanicDtoList(List<Mechanic> list) {
        List<MechanicDto> res = new ArrayList<>();
        for (Mechanic m : list) {
            res.add(toDto(m));
        }
        return res;
    }

    public static List<VoucherDto> toVoucherDtoList(List<Voucher> list) {
        List<VoucherDto> res = new ArrayList<>();
        for (Voucher b : list) {
            res.add(toDto(b));
        }
        return res;
    }

    public static VoucherDto toDto(Voucher v) {
        VoucherDto dto = new VoucherDto();
        dto.id = v.getId();
        dto.version = v.getVersion();

        dto.clientId = v.getClient().getId();
        dto.accumulated = v.getAccumulated();
        dto.code = v.getCode();
        dto.description = v.getDescription();
        dto.available = v.getAvailable();
        return dto;
    }

    public static CardDto toDto(CreditCard cc) {
        CardDto dto = new CardDto();
        dto.id = cc.getId();
        dto.version = cc.getVersion();

        dto.clientId = cc.getClient().getId();
        dto.accumulated = cc.getAccumulated();
        dto.cardNumber = cc.getNumber();
        dto.cardExpiration = cc.getValidThru();
        dto.cardType = cc.getType();
        return dto;
    }

    public static CashDto toDto(Cash m) {
        CashDto dto = new CashDto();
        dto.id = m.getId();
        dto.version = m.getVersion();

        dto.clientId = m.getClient().getId();
        dto.accumulated = m.getAccumulated();
        return dto;
    }

    public static InvoiceDto toDto(Invoice invoice) {
        InvoiceDto dto = new InvoiceDto();
        dto.id = invoice.getId();
        dto.version = invoice.getVersion();

        dto.number = invoice.getNumber();
        dto.date = invoice.getDate();
        dto.total = invoice.getAmount();
        dto.vat = invoice.getVat();
        dto.state = invoice.getState().toString();
        return dto;
    }

    public static List<PaymentMeanDto> toPaymentMeanDtoList(
            List<PaymentMean> list) {
        return list.stream().map(mp -> toDto(mp)).collect(Collectors.toList());
    }

    public static PaymentMeanDto toDto(PaymentMean mp) {
        if (mp instanceof Voucher) {
            return toDto((Voucher) mp);
        } else if (mp instanceof CreditCard) {
            return toDto((CreditCard) mp);
        } else if (mp instanceof Cash) {
            return toDto((Cash) mp);
        } else {
            throw new RuntimeException("Unexpected type of payment mean");
        }
    }

    public static WorkOrderDto toDto(WorkOrder a) {
        WorkOrderDto dto = new WorkOrderDto();
        dto.id = a.getId();
        dto.version = a.getVersion();

        dto.vehicleId = a.getVehicle().getId();
        dto.description = a.getDescription();
        dto.date = a.getDate();
        dto.total = a.getAmount();
        dto.state = a.getState().toString();

        dto.invoiceId = a.getInvoice() == null ? null : a.getInvoice().getId();

        return dto;
    }

    public static VehicleDto toDto(Vehicle v) {
        VehicleDto dto = new VehicleDto();
        dto.id = v.getId();
        dto.version = v.getVersion();

        dto.plate = v.getPlateNumber();
        dto.clientId = v.getClient().getId();
        dto.make = v.getMake();
        dto.vehicleTypeId = v.getVehicleType().getId();
        dto.model = v.getModel();

        return dto;
    }

    public static List<WorkOrderDto> toWorkOrderDtoList(List<WorkOrder> list) {
        return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }

    public static VehicleTypeDto toDto(VehicleType vt) {
        VehicleTypeDto dto = new VehicleTypeDto();

        dto.id = vt.getId();
        dto.version = vt.getVersion();

        dto.name = vt.getName();
        dto.pricePerHour = vt.getPricePerHour();

        return dto;
    }

    public static List<VehicleTypeDto> toVehicleTypeDtoList(
            List<VehicleType> list) {
        return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }

    // -----------------------------------------------------------
    // Métodos del assembler para las extensiones
    // -----------------------------------------------------------

    public static ContractTypeService.ContractTypeDto toDto(
            ContractType contractType) {
        ContractTypeService.ContractTypeDto dto =
                new ContractTypeService.ContractTypeDto();
        dto.id = contractType.getId();
        dto.version = contractType.getVersion();
        dto.name = contractType.getName();
        dto.compensationDays = contractType.getCompensationDays();
        return dto;
    }

    public static List<ContractTypeService.ContractTypeDto> toContractTypeDtoList(
            List<ContractType> list) {
        return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }

    public static ContractService.ContractDto toDto(Contract contract) {
        ContractService.ContractDto dto = new ContractService.ContractDto();
        dto.id = contract.getId();
        dto.version = contract.getVersion();
        dto.startDate = contract.getStartDate();
        dto.annualBaseWage = contract.getAnnualBaseWage();
        dto.contractTypeName = contract.getContractType().getName();
        if (contract.getMechanic().isPresent()) {
            dto.dni = contract.getMechanic().get().getDni();
        }
        dto.settlement = contract.getSettlement();
        dto.state = contract.getState();
        dto.professionalGroupName = contract.getProfessionalGroup().getName();
        if (contract.getEndDate().isPresent()) {
            dto.endDate = contract.getEndDate().get();
        }
        return dto;
    }

    public static ContractService.ContractSummaryDto toSummaryDto(
            Contract contract) {
        ContractService.ContractSummaryDto dto =
                new ContractService.ContractSummaryDto();
        dto.id = contract.getId();
        dto.version = contract.getVersion();
        if (contract.getMechanic().isPresent()) {
            dto.dni = contract.getMechanic().get().getDni();
        }
        dto.settlement = contract.getSettlement();
        dto.state = contract.getState();
        dto.numPayrolls = contract.getPayrolls().size();
        return dto;
    }

    public static List<ContractService.ContractSummaryDto> toContractSummaryDtoList(
            List<Contract> list) {
        return list.stream().map(a -> toSummaryDto(a))
                .collect(Collectors.toList());
    }


    public static ProfessionalGroupService.ProfessionalGroupBLDto toDto(
            ProfessionalGroup professionalGroup) {
        ProfessionalGroupService.ProfessionalGroupBLDto dto =
                new ProfessionalGroupService.ProfessionalGroupBLDto();
        dto.id = professionalGroup.getId();
        dto.version = professionalGroup.getVersion();
        dto.name = professionalGroup.getName();
        dto.trieniumSalary = professionalGroup.getTrienniumSalary();
        dto.productivityRate = professionalGroup.getProductivityRate();
        return dto;
    }

    public static List<ProfessionalGroupService.ProfessionalGroupBLDto> toProfessionalGroupList(
            List<ProfessionalGroup> professionalGroupBLDtos) {
        List<ProfessionalGroupService.ProfessionalGroupBLDto> res =
                new ArrayList<>();
        for (ProfessionalGroup g : professionalGroupBLDtos) {
            res.add(toOptionalDto(g));
        }
        return res;
    }

    private static ProfessionalGroupService.ProfessionalGroupBLDto toOptionalDto(
            ProfessionalGroup g) {
        ProfessionalGroupService.ProfessionalGroupBLDto result = null;
        if (g != null) {
            result = new ProfessionalGroupService.ProfessionalGroupBLDto();
            result.id = g.getId();
            result.name = g.getName();
            result.trieniumSalary = g.getTrienniumPayment();
            result.productivityRate = g.getProductivityBonusPercentage();
            result.version = g.getVersion();
        }
        return result;
    }
}
