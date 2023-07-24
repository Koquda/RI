package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WorkOrder extends BaseEntity {
    private final Set<Intervention> interventions = new HashSet<>();
    // natural attributes
    private LocalDateTime date;
    private String description;
    private double amount = 0.0;
    private WorkOrderState status = WorkOrderState.OPEN;
    // accidental attributes
    private Vehicle vehicle;
    private Mechanic mechanic;
    private Invoice invoice;

    public WorkOrder() {
    }

    public WorkOrder(Vehicle vehicle) {
        this(vehicle, LocalDateTime.now(), "no-description");
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date) {
        this(vehicle, date, "no-description");
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
        ArgumentChecks.isNotNull(vehicle);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isNotBlank(description);

        this.date = date.truncatedTo(ChronoUnit.MILLIS);
        this.description = description;
        Associations.Fix.link(vehicle, this);
    }

    public WorkOrder(Vehicle vehicle, String description) {
        this(vehicle, LocalDateTime.now(), description);
    }

    /**
     * Changes it to INVOICED state given the right conditions
     * This method is called from Invoice.addWorkOrder(...)
     * // @see UML_State diagrams on the problem statement document
     *
     * @throws IllegalStateException if
     *                               - The work order is not FINISHED, or
     *                               - The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
        if (!this.isFinished() || this.invoice == null) {
            throw new IllegalStateException("Cannot mark as invoiced");
        }
        status = WorkOrderState.INVOICED;
    }

    /**
     * Changes it to FINISHED state given the right conditions and
     * computes the amount
     * <p>
     * // @see UML_State diagrams on the problem statement document
     *
     * @throws IllegalStateException if
     *                               - The work order is not in ASSIGNED
     *                               state, or
     *                               - The work order is not linked with a
     *                               mechanic
     */
    public void markAsFinished() {
        if (status != WorkOrderState.ASSIGNED) {
            throw new IllegalStateException("Work order is not assigned");
        }
        if (mechanic == null) {
            throw new IllegalStateException(
                    "Work order is not linked with a " + "mechanic");
        }
        status = WorkOrderState.FINISHED;

        computeAmount();
    }

    private void computeAmount() {
        this.amount = 0.0;
        for (Intervention i : interventions) {
            this.amount += i.getAmount();
        }
    }

    /**
     * Changes it back to FINISHED state given the right conditions
     * This method is called from Invoice.removeWorkOrder(...)
     * // @see UML_State diagrams on the problem statement document
     *
     * @throws IllegalStateException if
     *                               - The work order is not INVOICED, or
     *                               - The work order is still linked with
     *                               the invoice
     */
    public void markBackToFinished() {
        if (!this.status.equals(WorkOrderState.INVOICED) ||
                this.invoice != null) {
            throw new IllegalStateException(
                    "Cannot move workorder back to " + "finished");
        }
        this.status = WorkOrderState.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its status
     * to ASSIGNED
     * // @see UML_State diagrams on the problem statement document
     *
     * @throws IllegalStateException if
     *                               - The work order is not in OPEN status, or
     *                               - The work order is already linked with
     *                               another mechanic
     */
    public void assignTo(Mechanic mechanic) {
        if (status != WorkOrderState.OPEN) {
            throw new IllegalStateException("Work order is not open");
        }
        if (this.mechanic != null) {
            throw new IllegalStateException("Work order is already assigned");
        }
        Associations.Assign.link(mechanic, this);
        this.status = WorkOrderState.ASSIGNED;
    }

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes
     * its status back to OPEN
     * // @see UML_State diagrams on the problem statement document
     *
     * @throws IllegalStateException if
     *                               - The work order is not in ASSIGNED status
     */
    public void desassign() {
        if (!this.status.equals(WorkOrderState.ASSIGNED)) {
            throw new IllegalStateException();
        }
        Associations.Assign.unlink(mechanic, this);
        this.status = WorkOrderState.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic is first have to
     * be moved back to OPEN state and unlinked from the previous mechanic.
     * // @see UML_State diagrams on the problem statement document
     *
     * @throws IllegalStateException if
     *                               - The work order is not in FINISHED status
     */
    public void reopen() {
        if (status != WorkOrderState.FINISHED) {
            throw new IllegalStateException("Work order is not finished");
        }
        Associations.Assign.unlink(mechanic, this);
        this.status = WorkOrderState.OPEN;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public WorkOrderState getStatus() {
        return status;
    }

    public void setStatus(WorkOrderState status) {
        this.status = status;
    }

    public WorkOrderState getState() {
        return status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Set<Intervention> getInterventions() {
        return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
        return interventions;
    }

    void _setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    void _setInvoice(Invoice invoice) {
        if (invoice == null) {
            setStatus(WorkOrderState.INVOICED);
        } else {
            setStatus(WorkOrderState.FINISHED);
        }
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, date);
    }

    @Override
    public String toString() {
        return "WorkOrder{" + "date=" + date + ", description='" + description +
                '\'' + ", amount=" + amount + ", status=" + status +
                ", vehicle=" + vehicle + ", mechanic=" + mechanic +
                ", invoice=" + invoice + '}';
    }

    public boolean isInvoiced() {
        return this.status == WorkOrderState.INVOICED;
    }

    public boolean isFinished() {
        return this.status == WorkOrderState.FINISHED;
    }

    public void setStatusForTesting(WorkOrderState workOrderState) {
        this.status = workOrderState;
    }

    public void setAmountForTesting(double money) {
        this.amount = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WorkOrder workOrder = (WorkOrder) o;
        return Objects.equals(date, workOrder.date) &&
                Objects.equals(vehicle, workOrder.vehicle);
    }

    public void setStateForTesting(WorkOrderState workOrderState) {
        this.status = workOrderState;
    }

    public enum WorkOrderState {
        OPEN, ASSIGNED, FINISHED, INVOICED
    }
}
