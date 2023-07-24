package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;


public class PayTests {
    private Mechanic mechanic;
    private WorkOrder workOrder;
    private Intervention intervention;
    private SparePart sparePart;
    private Vehicle vehicle;
    private VehicleType vehicleType;
    private Client client;
    private Invoice invoice;
    private Cash cash;
    private Charge charge;

    @Before
    public void setUp() {
        client = new Client("dni-cliente", "nombre", "apellidos");
        vehicle = new Vehicle("1234 GJI", "seat", "ibiza");
        Associations.Own.link(client, vehicle);

        vehicleType = new VehicleType("coche", 50.0);
        Associations.Classify.link(vehicleType, vehicle);

        workOrder = new WorkOrder(vehicle, "falla la junta la trocla");
        mechanic = new Mechanic("dni-mecanico", "nombre", "apellidos");
        workOrder.assignTo(mechanic);

        intervention = new Intervention(mechanic, workOrder, 60);

        sparePart = new SparePart("R1001", "junta la trocla", 100.0);
        new Substitution(sparePart, intervention, 2);

        workOrder.markAsFinished();

        invoice = new Invoice(0L, LocalDate.now());
        invoice.addWorkOrder(workOrder);

        cash = new Cash(client);
        charge = new Charge(invoice, cash, 100.0);
    }

    @Test
    public void testClientChargeLinked() {
        assertTrue(client.getPaymentMeans().contains(cash));
        assertSame(cash.getClient(), client);
    }

    @Test
    public void testClientCahsUnlinked() {
        Associations.Pay.unlink(client, cash);

        assertFalse(client.getPaymentMeans().contains(cash));
        assertEquals(0, client.getPaymentMeans().size());
        assertNull(cash.getClient());
    }

    @Test
    public void testInvoiceChargeLinked() {
        assertTrue(cash.getCharges().contains(charge));
        assertTrue(invoice.getCharges().contains(charge));

        assertSame(charge.getInvoice(), invoice);
        assertSame(charge.getPaymentMean(), cash);

        assertEquals(100.0, cash.getAccumulated(), 0.0);
    }

    @Test
    public void testInvoiceCashUnlinked() {
        Associations.ToCharge.unlink(charge);

        assertFalse(cash.getCharges().contains(charge));
        assertEquals(0, cash.getCharges().size());

        assertFalse(invoice.getCharges().contains(charge));
        assertEquals(0, cash.getCharges().size());

        assertNull(charge.getInvoice());
        assertNull(charge.getPaymentMean());
    }

    /*
     * Alb lo quita ¿ Por qué?
     */
    @Test
    public void testSafeReturn() {
        Set<Charge> chs = cash.getCharges();
        int num = chs.size();

        chs.remove(charge);

        assertEquals(cash.getCharges().size(), num);
        assertTrue("It must be a copy of the collection",
                cash.getCharges().contains(charge));


    }
}