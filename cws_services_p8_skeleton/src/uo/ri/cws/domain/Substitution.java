package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.Objects;

public class Substitution extends BaseEntity {
    // natural attributes
    private int quantity;

    // accidental attributes
    private SparePart sparePart;
    private Intervention intervention;

    public Substitution(SparePart sparePart, Intervention intervention,
                        int quantity) {
        ArgumentChecks.isNotNull(sparePart);
        ArgumentChecks.isNotNull(intervention);
        ArgumentChecks.isNotNull(quantity);
        ArgumentChecks.isTrue(quantity > 0);

        this.sparePart = sparePart;
        this.intervention = intervention;
        this.quantity = quantity;
        Associations.Substitute.link(sparePart, this, intervention);
    }

    public Substitution() {
    }


    void _setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    void _setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    public double getAmount() {
        return quantity * sparePart.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Substitution that = (Substitution) o;
        return Objects.equals(sparePart, that.sparePart) &&
                Objects.equals(intervention, that.intervention);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sparePart, intervention);
    }

    public long getQuantity() {
        return quantity;
    }
}