package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;

import java.util.Set;

import static org.junit.Assert.*;


public class ClassifyTests {
    private Vehicle vehicle;
    private VehicleType vehicleType;

    @Before
    public void setUp() {
        vehicle = new Vehicle("1234 GJI", "seat", "ibiza");
        vehicleType = new VehicleType("coche", 50.0);
        Associations.Classify.link(vehicleType, vehicle);
    }

    @Test
    public void testLinkOnClassify() {
        assertTrue(vehicleType.getVehicles().contains(vehicle));
        assertSame(vehicle.getVehicleType(), vehicleType);
    }

    @Test
    public void testUnlinkOnClassify() {
        Associations.Classify.unlink(vehicleType, vehicle);

        assertFalse(vehicleType.getVehicles().contains(vehicle));
        assertNull(vehicle.getVehicleType());
    }

    @Test
    public void testSafeReturn() {
        Set<Vehicle> vehiculos = vehicleType.getVehicles();
        vehiculos.remove(vehicle);

        assertEquals(0, vehiculos.size());
        assertEquals("It must be a copy of the collection", 1,
                vehicleType.getVehicles().size());
    }

}
