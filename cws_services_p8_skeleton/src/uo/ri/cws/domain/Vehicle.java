package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vehicle extends BaseEntity {
    private final Set<WorkOrder> workOrders = new HashSet<>();
    private String plateNumber;
    private String make;
    private String model;
    private Client client;
    private VehicleType vehicleType;

    public Vehicle(String plateNumber) {
        this(plateNumber, "no-make", "no-model");
    }

    public Vehicle(String plateNumber, String make, String model) {
        ArgumentChecks.isNotBlank(plateNumber);
        ArgumentChecks.isNotBlank(make);
        ArgumentChecks.isNotBlank(model);

        this.plateNumber = plateNumber;
        this.make = make;
        this.model = model;
    }

    public Vehicle() {

    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Client getClient() {
        return client;
    }

    void setClient(Client client) {
        this.client = client;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    Set<WorkOrder> _getWorkOrders() {
        return workOrders;
    }

    public Set<WorkOrder> getWorkOrders() {
        return new HashSet<>(workOrders);
    }

    @Override
    public String toString() {
        return "Vehicle{" + "plateNumber='" + plateNumber + '\'' + ", make='" +
                make + '\'' + ", model='" + model + '\'' + ", owner=" + client +
                ", vehicleType=" + vehicleType + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(plateNumber, vehicle.plateNumber);
    }
}
