package uo.ri.cws.associations;

import org.junit.Before;
import org.junit.Test;
import uo.ri.cws.domain.*;

import java.util.Set;

import static org.junit.Assert.*;


public class SubstituteTests {
    private Mechanic mechanic;
    private WorkOrder workOrder;
    private Intervention intervention;
    private SparePart sparePart;
    private Substitution sustitution;
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

        intervention = new Intervention(mechanic, workOrder, 60);

        sparePart = new SparePart("R1001", "junta la trocla", 100.0);
        sustitution = new Substitution(sparePart, intervention, 2);
    }

    @Test
    public void testSustitutionInterventionLinked() {
        assertEquals(sustitution.getIntervention(), intervention);
        assertEquals(sustitution.getSparePart(), sparePart);

        assertTrue(sparePart.getSustitutions().contains(sustitution));
        assertTrue(intervention.getSubstitutions().contains(sustitution));
    }

    @Test
    public void testSustitutionSparePartUnlinked() {
        Associations.Substitute.unlink(sustitution);

        assertNull(sustitution.getIntervention());
        assertNull(sustitution.getSparePart());

        assertFalse(sparePart.getSustitutions().contains(sustitution));
        assertEquals(0, sparePart.getSustitutions().size());

        assertFalse(intervention.getSubstitutions().contains(sustitution));
        assertEquals(0, intervention.getSubstitutions().size());
    }

    @Test
    public void testSafeReturnOnIntervention() {
        Set<Substitution> sustituciones = intervention.getSubstitutions();
        sustituciones.remove(sustitution);

        assertEquals(0, sustituciones.size());
        assertEquals("It must be a copy of the collection or a read-only " +
                "version", 1, intervention.getSubstitutions().size());
    }

    @Test
    public void testSafeReturnOnSparePart() {
        Set<Substitution> sustituciones = sparePart.getSustitutions();
        sustituciones.remove(sustitution);

        assertEquals(0, sustituciones.size());
        assertEquals("It must be a copy of the collection or a read-only " +
                "version", 1, sparePart.getSustitutions().size());
    }

}
