package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

import java.util.Objects;

public class Voucher extends PaymentMean {
    private String code;
    private double available = 0.0;
    private String description;

    public Voucher(String code, String description, double available) {
        super(new Client());
        ArgumentChecks.isNotBlank(code);
        ArgumentChecks.isNotBlank(description);
        ArgumentChecks.isTrue(available >= 0.0);

        this.code = code;
        this.description = description;
        this.available = available;
    }

    public Voucher(String code, double available) {
        this(code, "no-description", available);
    }


    public Voucher() {

    }


    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     *
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
        if (amount > available) {
            throw new IllegalStateException("Not enough available");
        }

        super.pay(amount);
        available -= amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Double.compare(voucher.available, available) == 0 &&
                Objects.equals(code, voucher.code) &&
                Objects.equals(description, voucher.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Voucher{" + "code='" + code + '\'' + ", available=" +
                available + ", description='" + description + '\'' + '}';
    }

}
