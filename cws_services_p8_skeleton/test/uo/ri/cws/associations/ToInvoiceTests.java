package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;


public class ToInvoiceTests {
    private Mechanic mechanic;
    private WorkOrder workOrder;
    private Intervention intervention;
    private SparePart sparePart;
    private Vehicle vehicle;
    private VehicleType vehicleType;
    private Client client;
    private Invoice invoice;

    @Before
    public void setUp() {
        client = new Client("dni-cliente", "nombre", "apellidos");
        vehicle = new Vehicle("1234 GJI", "seat", "ibiza");
        Associations.Own.link(client, vehicle);

        vehicleType = new VehicleType("coche", 50.0);
        Associations.Classify.link(vehicleType, vehicle);

        workOrder = new WorkOrder(vehicle, "falla la junta la trocla");
        mechanic = new Mechanic("dni-mecanico", "nombre", "apellidos");

        workOrder.assignTo(mechanic); // workorder changes to assigned

        intervention = new Intervention(mechanic, workOrder, 60);

        sparePart = new SparePart("R1001", "junta la trocla", 100.0);
        new Substitution(sparePart, intervention, 2);

        workOrder.markAsFinished(); // changes status & compute price

        invoice = new Invoice(0L, LocalDate.now());
        invoice.addWorkOrder(workOrder);
    }

    @Test
    public void testLinkOnInvoice() {
        assertTrue(invoice.getWorkOrders().contains(workOrder));
        assertTrue(invoice.getAmount() > 0.0);

        assertSame(workOrder.getInvoice(), invoice);
        assertTrue(workOrder.isInvoiced());
    }

    @Test
    public void testUnlinkOnInvoice() {
        invoice.removeWorkOrder(workOrder);

        assertFalse(invoice.getWorkOrders().contains(workOrder));
        assertEquals(0, invoice.getWorkOrders().size());
        assertEquals(0.0, invoice.getAmount(), 0.0);

        assertNull(workOrder.getInvoice());
        assertTrue(workOrder.isFinished());
    }

    @Test
    public void testSafeReturn() {
        Set<WorkOrder> returned = invoice.getWorkOrders();
        returned.remove(workOrder);

        assertEquals(0, returned.size());
        assertEquals("It must be a copy of the collection or a read-only " +
                "version", 1, invoice.getWorkOrders().size());
    }

}