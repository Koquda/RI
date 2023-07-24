package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;

import java.util.Objects;

public class Charge extends BaseEntity {
    // natural attributes
    private double amount = 0.0;

    // accidental attributes
    private Invoice invoice;
    private PaymentMean paymentMean;

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
        // store the amount
        this.amount = amount;
        // increment the paymentMean accumulated -> paymentMean.pay( amount )
        paymentMean.pay(amount);
        // link invoice, this and paymentMean
        Associations.ToCharge.link(paymentMean, this, invoice);
    }

    public Charge() {

    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean
     *
     * @throws IllegalStateException if the invoice is already settled
     */
    public void rewind() {
        // asserts the invoice is not in PAID status
        // decrements the payment mean accumulated ( paymentMean.pay( -amount) )
        // unlinks invoice, this and paymentMean
    }

    public double getAmount() {
        return Math.round(amount * 100.0) / 100.0;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMean paymentMean) {
        this.paymentMean = paymentMean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return Objects.equals(paymentMean, charge.paymentMean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMean);
    }

}
