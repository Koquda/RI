package uo.ri.cws.domain;


public class Cash extends PaymentMean {

    //@ManyToOne
    //private Client client;

    //public Client getClient() {
    //    return super.getClient();
    //}

    public Cash() {

    }

    //public Client getClient() {
    //    return client;
    //}

    // public void setClient(Client client) {
    //     this.client = client;
    // }

    public Cash(Client client) {
        super();
        Associations.Pay.link(this, client);

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
