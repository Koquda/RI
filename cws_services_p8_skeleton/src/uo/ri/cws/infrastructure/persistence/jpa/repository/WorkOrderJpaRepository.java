package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.util.List;

public class WorkOrderJpaRepository extends BaseJpaRepository<WorkOrder>
        implements WorkOrderRepository {

    @Override
    public List<WorkOrder> findByIds(List<String> idsAveria) {
        return Jpa.getManager()
                .createNamedQuery("WorkOrder.findByIds", WorkOrder.class)
                .setParameter(1, idsAveria).getResultList();
    }

    @Override
    public List<WorkOrder> findNotInvoicedWorkOrdersByClientDni(String dni) {
        return null;
    }

    @Override
    public List<WorkOrder> findByMechanic(String mechanic_id) {
        return null;
    }

}
