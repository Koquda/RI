package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Mechanic extends BaseEntity {
    private final Set<Intervention> interventions = new HashSet<>();
    private final Set<Contract> terminatedContracts = new HashSet<>();
    // natural attributes
    private String dni;
    private String surname;
    private String name;
    // accidental attributes
    private Set<WorkOrder> assigned = new HashSet<>();
    private Contract contractInForce;


    public Mechanic() {

    }

    public Mechanic(String dni) {
        this(dni, "no-name", "no-surname");
    }

    public Mechanic(String dni, String name, String surname) {
        ArgumentChecks.isNotBlank(dni);
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotBlank(surname);

        this.dni = dni;
        this.name = name;
        this.surname = surname;
    }

    public Optional<Contract> getContractInForce() {
        if (contractInForce == null) {
            return Optional.empty();
        }
        return Optional.of(contractInForce);
    }

    public void setContractInForce(Contract contractInForce) {
        this.contractInForce = contractInForce;
    }

    public Set<WorkOrder> getAssigned() {
        return new HashSet<>(assigned);
    }

    public void setAssigned(Set<WorkOrder> assigned) {
        this.assigned = assigned;
    }

    Set<WorkOrder> _getAssigned() {
        return assigned;
    }

    public Set<Intervention> getInterventions() {
        return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
        return interventions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Mechanic{" + "dni='" + dni + '\'' + ", surname='" + surname +
                '\'' + ", name='" + name + '\'' + '}';
    }

    public Set<Contract> getTerminatedContracts() {
        return new HashSet<>(terminatedContracts);
    }

    public Set<Contract> _getTerminatedContracts() {
        return terminatedContracts;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Mechanic mechanic = (Mechanic) o;
        return Objects.equals(dni, mechanic.dni);
    }

    public boolean isInForce() {
        return contractInForce != null;
    }
}
