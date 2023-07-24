package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Set;

import static org.junit.Assert.*;


public class FixTests {
    private WorkOrder workOrder;
    private Vehicle vehicle;
    private VehicleType vehicleType;
    private Client client;

    @Before
    public void setUp() {
        client = new Client("dni-cliente", "nombre", "apellidos");
        vehicle = new Vehicle("1234 GJI", "seat", "ibiza");
        Associations.Own.link(client, vehicle);

        vehicleType = new VehicleType("coche", 50.0);
        Associations.Classify.link(vehicleType, vehicle);

        workOrder = new WorkOrder(vehicle, "falla la junta la trocla");
    }

    @Test
    public void testLinkOnOrder() {
        // The constructor of "WorkOrder" creates the link with "vehicle"
        // It calls Association.Averiar.link(...)
        assertTrue(vehicle.getWorkOrders().contains(workOrder));
        assertSame(workOrder.getVehicle(), vehicle);
    }

    @Test
    public void testUnlinkOnOrder() {
        Associations.Fix.unlink(vehicle, workOrder);

        assertFalse(vehicle.getWorkOrders().contains(workOrder));
        assertNull(workOrder.getVehicle());
    }

    @Test
    public void testUnlinkTwiceOnOrder() {
        Associations.Fix.unlink(vehicle, workOrder);
        Associations.Fix.unlink(vehicle, workOrder);

        assertFalse(vehicle.getWorkOrders().contains(workOrder));
        assertNull(workOrder.getVehicle());
    }

    @Test
    public void testSafeReturn() {
        Set<WorkOrder> workOrders = vehicle.getWorkOrders();
        workOrders.remove(workOrder);

        assertEquals(0, workOrders.size());
        assertEquals("It must be a copy of the collection or a read-only " +
                "version", 1, vehicle.getWorkOrders().size());
    }


}
