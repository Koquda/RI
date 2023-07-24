package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Address {
    private String street;
    private String city;
    private String zipCode;

    public Address() {
        this("no-street", "no-city", "no-zip");
    }

    public Address(String street, String city, String zipCode) {
        ArgumentChecks.isNotEmpty(street);
        ArgumentChecks.isNotEmpty(city);
        ArgumentChecks.isNotEmpty(zipCode);

        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null) return false;
        } else if (!city.equals(other.city)) return false;
        if (street == null) {
            if (other.street != null) return false;
        } else if (!street.equals(other.street)) return false;
        if (zipCode == null) {
            return other.zipCode == null;
        } else {
            return zipCode.equals(other.zipCode);
        }
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", zipCode=" +
                zipCode + "]";
    }

}
