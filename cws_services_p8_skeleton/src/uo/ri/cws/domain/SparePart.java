package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SparePart extends BaseEntity {
    // natural attributes
    private String code;
    private String description;
    private double price;

    // accidental attributes
    private Set<Substitution> substitutions = new HashSet<>();


    public SparePart(String code, String description, double price) {
        ArgumentChecks.isNotBlank(code);
        ArgumentChecks.isNotBlank(description);
        ArgumentChecks.isTrue(price >= 0);

        this.code = code;
        this.description = description;
        this.price = price;
    }

    public SparePart() {

    }


    public SparePart(String code) {
        this(code, "no-description", 0.0, null);
    }

    public SparePart(String code, String description, double price,
                     Set<Substitution> substitutions) {
        ArgumentChecks.isNotBlank(code);
        ArgumentChecks.isNotBlank(description);
        ArgumentChecks.isNotNull(price);
        ArgumentChecks.isTrue(price >= 0.0);

        this.code = code;
        this.description = description;
        this.price = price;
        this.substitutions = substitutions;
    }

    public Set<Substitution> getSustitutions() {
        return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
        return substitutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SparePart sparePart = (SparePart) o;
        return Objects.equals(code, sparePart.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "SparePart{" + "code='" + code + '\'' + ", description='" +
                description + '\'' + ", price=" + price + '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Substitution> getSubstitutions() {
        return new HashSet<>(substitutions);
    }

    public void setSubstitutions(Set<Substitution> substitutions) {
        this.substitutions = substitutions;
    }
}
