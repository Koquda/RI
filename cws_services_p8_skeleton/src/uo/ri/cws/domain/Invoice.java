package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

import java.time.LocalDate;
import java.util.*;

public class Invoice extends BaseEntity {


    // natural attributes
    private Long number;
    private LocalDate date;
    private double amount;
    private double vat;
    private InvoiceState status = InvoiceState.NOT_YET_PAID;
    // accidental attributes
    private Set<WorkOrder> workOrders = new HashSet<>();
    private Set<Charge> charges = new HashSet<>();

    public Invoice() {
    }

    public Invoice(Long number) {
        // call full constructor with sensible defaults
        this(number, LocalDate.now(), new ArrayList<>());
    }


    public Invoice(Long number, LocalDate date) {
        // call full constructor with sensible defaults
        this(number, date, new ArrayList<>());
    }

    public Invoice(Long number, List<WorkOrder> workOrders) {
        this(number, LocalDate.now(), workOrders);
    }

    // full constructor
    public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
        // check arguments (always), through IllegalArgumentException
        ArgumentChecks.isNotNull(number);
        ArgumentChecks.isTrue(number >= 0.0);
        ArgumentChecks.isNotNull(date);
        checkWorkOrders(workOrders);

        // store the number
        this.number = number;
        // store a copy of the date
        this.date = LocalDate.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth());
        // add every work order calling addWorkOrder( w )
        //this.workOrders.addAll(workOrders);
        //this.status = InvoiceStatus.NOT_YET_PAID;


        // Computes de amount of the invoice
        computeAmount();

        for (WorkOrder workOrder : workOrders) {
            addWorkOrder(workOrder);
        }
    }

    private void checkWorkOrders(List<WorkOrder> workOrders) {
        ArgumentChecks.isNotNull(workOrders);
        for (WorkOrder w : workOrders) {
            if (w.getStatus() != WorkOrder.WorkOrderState.FINISHED) {
                throw new IllegalStateException(
                        "Work order " + w.getId() + " is not finished");
            }
            w.setStatus(WorkOrder.WorkOrderState.INVOICED);
        }
    }

    /**
     * Computes amount and vat (vat depends on the date)
     */
    private void computeAmount() {
        double amount = 0.0;
        for (WorkOrder w : workOrders) {
            amount += w.getAmount();
        }
        vat = amount * getVatType();
        amount += vat;
        this.amount = Round.twoCents(amount);
    }

    private double getVatType() {
        // Calculates the vat depending on the date
        double vat = 0.0;
        if (date.isBefore(LocalDate.of(2012, 7, 1))) {
            vat = 0.18;
        } else {
            vat = 0.21;
        }
        return vat;
    }

    /**
     * Adds (double links) the workOrder to the invoice and updates the
     * amount and vat
     *
     * @param workOrder // @see UML_State diagrams on the problem statement
     *                  document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void addWorkOrder(WorkOrder workOrder) {
        if (!this.status.equals(InvoiceState.NOT_YET_PAID)) {
            throw new IllegalStateException("Invoice status is paid");
        }
        ArgumentChecks.isNotNull(workOrder);

        Associations.ToInvoice.link(this, workOrder);
        workOrder.markAsInvoiced();
        computeAmount();
    }

    /**
     * Removes a work order from the invoice and recomputes amount and vat
     *
     * @param workOrder // @see UML_State diagrams on the problem statement
     *                  document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void removeWorkOrder(WorkOrder workOrder) {
        if (!this.status.equals(InvoiceState.NOT_YET_PAID)) {
            throw new IllegalStateException("Invoice status is paid");
        }
        ArgumentChecks.isNotNull(workOrder);

        Associations.ToInvoice.unlink(this, workOrder);
        workOrder.markBackToFinished();
        computeAmount();
    }

    /**
     * Marks the invoice as PAID, but
     *
     * @throws IllegalStateException if
     *                               - Is already settled
     *                               - Or the amounts paid with charges to
     *                               payment means do not cover
     *                               the total of the invoice
     */
    public void settle() {
        if (this.status.equals(InvoiceState.PAID)) {
            throw new IllegalStateException("Invoice already PAID");
        }

        double currentPaid = 0.0;
        for (Charge c : charges) {
            currentPaid += c.getAmount();
        }

        if (currentPaid < this.getAmount() - .01) {
            throw new IllegalStateException("Invoice's charges do not cover " +
                    "the ammount of the invoice");
        }

        if (currentPaid > this.getAmount() + .02) {
            throw new IllegalStateException("Invoice is being overpaid");
        }

        this.status = InvoiceState.PAID;
    }

    public Set<WorkOrder> getWorkOrders() {
        return new HashSet<>(workOrders);
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    Set<WorkOrder> _getWorkOrders() {
        return workOrders;
    }

    public Set<Charge> getCharges() {
        return new HashSet<>(charges);
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    Set<Charge> _getCharges() {
        return charges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Double.compare(invoice.amount, amount) == 0 &&
                Double.compare(invoice.vat, vat) == 0 &&
                Objects.equals(number, invoice.number) &&
                Objects.equals(date, invoice.date) &&
                status == invoice.status &&
                Objects.equals(workOrders, invoice.workOrders) &&
                Objects.equals(charges, invoice.charges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Invoice{" + "number=" + number + ", date=" + date + ", amount" +
                "=" + amount + ", vat=" + vat + ", status=" + status + '}';
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public InvoiceState getStatus() {
        return status;
    }

    public void setStatus(InvoiceState status) {
        this.status = status;
    }

    public InvoiceState getState() {
        return status;
    }

    public boolean isNotSettled() {
        return status == InvoiceState.NOT_YET_PAID;
    }

    public boolean isSettled() {
        return this.status.equals(InvoiceState.PAID);
    }

    public enum InvoiceState {NOT_YET_PAID, PAID}
}
