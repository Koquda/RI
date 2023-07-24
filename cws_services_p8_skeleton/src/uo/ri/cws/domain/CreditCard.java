package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard extends PaymentMean {
    private String number;
    private String type;
    private LocalDate validThru;


    public CreditCard(String number, String type, LocalDate validThru) {
        super(new Client("no-dni"));
        ArgumentChecks.isNotBlank(number);
        ArgumentChecks.isNotBlank(type);
        ArgumentChecks.isNotNull(validThru);

        this.number = number;
        this.type = type;
        this.validThru = validThru;
    }

    public CreditCard(String number) {
        super(new Client());
        ArgumentChecks.isNotBlank(number);

        this.number = number;
        this.validThru = LocalDate.now().plusDays(1);
        this.type = "UNKNOWN";
    }

    public CreditCard() {

    }

    @Override
    public void pay(double importe) {
        if (!isValidNow()) {
            throw new IllegalStateException("Credit card is not valid");
        }
        super.pay(importe);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(number, that.number) &&
                Objects.equals(type, that.type) &&
                Objects.equals(validThru, that.validThru);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "CreditCard{" + "number='" + number + '\'' + ", type='" + type +
                '\'' + ", validThru=" + validThru + '}';
    }

    public boolean isValidNow() {
        return validThru.isAfter(LocalDate.now());
    }
}
