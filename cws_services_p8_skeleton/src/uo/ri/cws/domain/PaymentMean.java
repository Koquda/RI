package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

import java.util.HashSet;
import java.util.Set;

public abstract class PaymentMean extends BaseEntity {
    private final Set<Charge> charges = new HashSet<>();
    // natural attributes
    private double accumulated = 0.0;
    // accidental attributes
    private Client client;

    public PaymentMean(Client client) {
        ArgumentChecks.isNotNull(client);

        this.client = client;
    }

    public PaymentMean() {

    }

    public double getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(double accumulated) {
        this.accumulated = accumulated;
    }

    public void pay(double importe) {
        this.accumulated += importe;
    }

    void _setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Set<Charge> getCharges() {
        return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
        return charges;
    }
}
