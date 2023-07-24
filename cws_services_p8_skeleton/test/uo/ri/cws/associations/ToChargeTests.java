package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;


public class ToChargeTests {

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
        // Constructor invokes associations.link
//		Associations.Charges.link(invoice, charge, cash);

    }


    @Test
    public void testInvoiceChargeLinkOnAssign() {

        assertSame(charge.getInvoice(), invoice);
        assertTrue(invoice.getCharges().contains(charge));
    }

    @Test
    public void testCashChargeLinkOnAssign() {
        assertSame(charge.getPaymentMean(), cash);
        assertTrue(cash.getCharges().contains(charge));
    }


    @Test
    public void testInvoiceChargeUnlinkOnAssign() {
        Associations.ToCharge.unlink(charge);

        assertNull(charge.getInvoice());
        assertFalse(invoice.getCharges().contains(charge));
    }

    @Test
    public void testCashChargeUnlinkOnAssign() {
        Associations.ToCharge.unlink(charge);

        assertFalse(cash.getCharges().contains(charge));
        assertNull(charge.getPaymentMean());
    }

    @Test
    public void testInvoiceSafeReturn() {
        Set<Charge> chs = invoice.getCharges();
        int num = chs.size();

        chs.remove(charge);

        assertEquals(invoice.getCharges().size(), num);
        assertTrue("It must be a copy of the collection",
                invoice.getCharges().contains(charge));


    }


    @Test
    public void testCashInvoiceSafeReturn() {
        Set<Charge> chs = cash.getCharges();
        int num = chs.size();

        chs.remove(charge);

        assertEquals(cash.getCharges().size(), num);
        assertTrue("It must be a copy of the collection",
                cash.getCharges().contains(charge));


    }

}