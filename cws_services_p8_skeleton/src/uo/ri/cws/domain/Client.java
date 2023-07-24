package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Client extends BaseEntity {
    private final Set<Vehicle> vehicles = new HashSet<>();
    private String dni;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;
    private Set<PaymentMean> paymentMeans = new HashSet<>();


    public Client(String dni) {
        this(dni, "no-name", "no-surname", "no-email", "no-phone", null);
    }

    public Client(String dni, String name, String surname) {
        this(dni, name, surname, "no@email", "no phone", null);
    }

    public Client(String dni, String name, String surname, String email,
                  String phone, Address address) {
        ArgumentChecks.isNotBlank(dni);
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotBlank(surname);
        ArgumentChecks.isNotBlank(email);
        ArgumentChecks.isNotBlank(String.valueOf(address));

        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client() {

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    Set<PaymentMean> _getPaymentMeans() {
        return paymentMeans;
    }

    public Set<PaymentMean> getPaymentMeans() {
        return new HashSet<>(paymentMeans);
    }

    public void setPaymentMeans(Set<PaymentMean> paymentMeans) {
        this.paymentMeans = paymentMeans;
    }

    Set<Vehicle> _getVehicles() {
        return vehicles;
    }

    public Set<Vehicle> getVehicles() {
        return new HashSet<>(vehicles);
    }

    @Override
    public String toString() {
        return "Client{" + "dni='" + dni + '\'' + ", name='" + name + '\'' +
                ", surname='" + surname + '\'' + ", email='" + email + '\'' +
                ", phone='" + phone + '\'' + ", address=" + address + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(dni, client.dni) &&
                Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(email, client.email) &&
                Objects.equals(phone, client.phone) &&
                Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dni);
    }

}

