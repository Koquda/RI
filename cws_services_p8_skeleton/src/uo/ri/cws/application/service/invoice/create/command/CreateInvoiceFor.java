package uo.ri.cws.application.service.invoice.create.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.List;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private final List<String> workOrderIds;
    private final WorkOrderRepository wrkrsRepo =
            Factory.repository.forWorkOrder();
    private final InvoiceRepository invsRepo = Factory.repository.forInvoice();

    public CreateInvoiceFor(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds);
        ArgumentChecks.isTrue(!workOrderIds.isEmpty());
        workOrderIds.forEach(id -> ArgumentChecks.isNotBlank(id));

        this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
        List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);
        BusinessChecks.isTrue(!workOrders.isEmpty());
        BusinessChecks.isTrue(workOrders.size() == workOrderIds.size());
        BusinessChecks.isTrue(allFinished(workOrders));


        Long numero = invsRepo.getNextInvoiceNumber();
        if (numero == null) {
            numero = 0L;
        }

        Invoice i = new Invoice(numero, workOrders);
        invsRepo.add(i);
        InvoiceDto dto = DtoAssembler.toDto(i);
        dto.id = i.getId();

        return dto;
    }

    private boolean allFinished(List<WorkOrder> wl) {
        for (WorkOrder w : wl) {
            if (!w.isFinished()) return false;
        }
        return true;
    }

}
