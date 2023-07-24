package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Set;

import static org.junit.Assert.*;


public class AssignTests {
    private Mechanic mechanic;
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

        mechanic = new Mechanic("dni-mecanico", "nombre", "apellidos");
        Associations.Assign.link(mechanic, workOrder);
    }

    @Test
    public void testLinkOnAssign() {
        assertTrue(mechanic.getAssigned().contains(workOrder));
        assertSame(workOrder.getMechanic(), mechanic);
    }

    @Test
    public void testUnlinkOnAssign() {
        Associations.Assign.unlink(mechanic, workOrder);

        assertFalse(mechanic.getAssigned().contains(workOrder));
        assertEquals(0, mechanic.getAssigned().size());
        assertNull(workOrder.getMechanic());
    }

    @Test
    public void testSafeReturn() {
        Set<WorkOrder> assigned = mechanic.getAssigned();
        assigned.remove(workOrder);

        assertEquals(0, assigned.size());
        assertEquals("It must be a copy of the collection", 1,
                mechanic.getAssigned().size());
    }

}
