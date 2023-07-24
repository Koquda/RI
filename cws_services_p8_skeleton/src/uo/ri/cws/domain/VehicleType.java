package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class VehicleType extends BaseEntity {
    // natural attributes
    private String name;
    private double pricePerHour;

    // accidental attributes
    private Set<Vehicle> vehicles = new HashSet<>();

    public VehicleType() {

    }


    public VehicleType(String name) {
        this(name, 0.0);
    }

    public VehicleType(String name, double pricePerHour) {
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotNull(pricePerHour);
        ArgumentChecks.isTrue(pricePerHour >= 0.0);

        this.name = name;
        this.pricePerHour = pricePerHour;
    }

    public Set<Vehicle> getVehicles() {
        return new HashSet<>(vehicles);
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    Set<Vehicle> _getVehicles() {
        return vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleType that = (VehicleType) o;
        return Double.compare(that.pricePerHour, pricePerHour) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(vehicles, that.vehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "VehicleType{" + "name='" + name + '\'' + ", pricePerHour=" +
                pricePerHour + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
