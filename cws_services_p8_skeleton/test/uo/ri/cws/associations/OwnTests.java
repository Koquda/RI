package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Vehicle;

import java.util.Set;

import static org.junit.Assert.*;


public class OwnTests {
    private Vehicle vehicle;
    private Client client;

    @Before
    public void setUp() {
        client = new Client("dni-cliente", "nombre", "apellidos");
        vehicle = new Vehicle("1234 GJI", "seat", "ibiza");
        Associations.Own.link(client, vehicle);
    }

    @Test
    public void testLinkOnOwn() {
        assertTrue(client.getVehicles().contains(vehicle));
        assertSame(vehicle.getClient(), client);
    }

    @Test
    public void testUnlinkOnOwn() {
        Associations.Own.unlink(client, vehicle);

        assertFalse(client.getVehicles().contains(vehicle));
        assertNull(vehicle.getClient());
    }

    @Test
    public void testSafeReturn() {
        Set<Vehicle> vehicles = client.getVehicles();
        vehicles.remove(vehicle);

        assertEquals(0, vehicles.size());
        assertEquals("It must be a copy of the collection or a read-only " +
                "version", 1, client.getVehicles().size());
    }


}
