package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Intervention extends BaseEntity {
    private final Set<Substitution> substitutions = new HashSet<>();
    // natural attributes
    private LocalDateTime date;
    private int minutes;
    // accidental attributes
    private WorkOrder workOrder;
    private Mechanic mechanic;


    public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
        this(mechanic, workOrder, LocalDateTime.now(), minutes);
    }

    public Intervention(Mechanic mechanic, WorkOrder workOrder,
                        LocalDateTime date, int minutes) {
        ArgumentChecks.isNotNull(mechanic);
        ArgumentChecks.isNotNull(workOrder);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isTrue(minutes >= 0);

        this.date = date.truncatedTo(ChronoUnit.MILLIS);
        this.minutes = minutes;
        this.mechanic = mechanic;
        this.workOrder = workOrder;
        Associations.Intervene.link(workOrder, this, mechanic);
    }

    public Intervention() {

    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    void _setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Set<Substitution> getSubstitutions() {
        return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
        return substitutions;
    }

    public double getAmount() {
        double a = 0;
        for (Substitution s : substitutions) {
            if (s.getIntervention() == this) {
                a += s.getAmount();
            }
        }
        return (workOrder.getVehicle().getVehicleType().getPricePerHour() /
                60) * minutes + a;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Intervention that = (Intervention) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(workOrder, that.workOrder) &&
                Objects.equals(mechanic, that.mechanic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, workOrder);
    }
}
