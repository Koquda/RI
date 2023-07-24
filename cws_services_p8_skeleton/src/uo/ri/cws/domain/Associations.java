package uo.ri.cws.domain;

public class Associations {

    public static class Own {

        public static void link(Client client, Vehicle vehicle) {
            vehicle.setClient(client);
            client._getVehicles().add(vehicle);
        }

        public static void unlink(Client client, Vehicle vehicle) {
            client._getVehicles().remove(vehicle);
            vehicle.setClient(null);
        }

    }

    public static class Classify {

        public static void link(VehicleType vehicleType, Vehicle vehicle) {
            vehicle.setVehicleType(vehicleType);
            vehicleType._getVehicles().add(vehicle);
        }

        public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
            vehicleType._getVehicles().remove(vehicle);
            vehicle.setVehicleType(null);
        }

    }

    public static class Pay {

        public static void link(PaymentMean pm, Client client) {
            pm._setClient(client);
            client._getPaymentMeans().add(pm);
        }

        public static void unlink(Client client, PaymentMean pm) {
            client._getPaymentMeans().remove(pm);
            pm._setClient(null);
        }

    }

    public static class Fix {

        public static void link(Vehicle vehicle, WorkOrder workOrder) {
            workOrder._setVehicle(vehicle);
            vehicle._getWorkOrders().add(workOrder);
        }

        public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
            vehicle._getWorkOrders().remove(workOrder);
            workOrder._setVehicle(null);
        }

    }

    public static class ToInvoice {

        public static void link(Invoice invoice, WorkOrder workOrder) {
            workOrder._setInvoice(invoice);
            invoice._getWorkOrders().add(workOrder);
        }

        public static void unlink(Invoice invoice, WorkOrder workOrder) {
            invoice._getWorkOrders().remove(workOrder);
            workOrder._setInvoice(null);
        }
    }

    public static class ToCharge {

        public static void link(PaymentMean pm, Charge charge,
                                Invoice inovice) {
            charge.setPaymentMean(pm);
            charge.setInvoice(inovice);
            pm._getCharges().add(charge);
            inovice._getCharges().add(charge);
        }

        public static void unlink(Charge charge) {
            charge.getPaymentMean()._getCharges().remove(charge);
            charge.getInvoice()._getCharges().remove(charge);
            charge.setPaymentMean(null);
            charge.setInvoice(null);
        }

    }

    public static class Assign {

        public static void link(Mechanic mechanic, WorkOrder workOrder) {
            workOrder._setMechanic(mechanic);
            mechanic._getAssigned().add(workOrder);
        }

        public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
            mechanic._getAssigned().remove(workOrder);
            workOrder._setMechanic(null);
        }

    }

    public static class Intervene {

        public static void link(WorkOrder workOrder, Intervention intervention,
                                Mechanic mechanic) {
            intervention._setMechanic(mechanic);
            intervention._setWorkOrder(workOrder);
            workOrder._getInterventions().add(intervention);
            mechanic._getInterventions().add(intervention);
        }

        public static void unlink(Intervention intervention) {
            intervention.getMechanic()._getInterventions().remove(intervention);
            intervention.getWorkOrder()._getInterventions()
                    .remove(intervention);
            intervention._setMechanic(null);
            intervention._setWorkOrder(null);
        }

    }

    public static class Substitute {

        public static void link(SparePart spare, Substitution sustitution,
                                Intervention intervention) {
            sustitution._setIntervention(intervention);
            sustitution._setSparePart(spare);
            spare._getSubstitutions().add(sustitution);
            intervention._getSubstitutions().add(sustitution);
        }

        public static void unlink(Substitution sustitution) {
            sustitution.getIntervention()._getSubstitutions()
                    .remove(sustitution);
            sustitution.getSparePart()._getSubstitutions().remove(sustitution);
            sustitution._setIntervention(null);
            sustitution._setSparePart(null);
        }

    }


    // -----------------------------------------------------
    // Extensiones
    // -----------------------------------------------------

    public static class Fire {
        public static void link(Contract contract, Mechanic mechanic) {
            contract.setFiredMechanic(contract.getMechanic().get());
            mechanic._getTerminatedContracts().add(contract);
        }

        public static void unlink(Contract contract, Mechanic mechanic) {
            mechanic._getTerminatedContracts().remove(contract);
            contract.setFiredMechanic(null);
        }

    }


    public static class Group {
        public static void link(Contract contract,
                                ProfessionalGroup professionalGroup) {
            contract.setProfessionalGroup(professionalGroup);
            professionalGroup._getContracts().add(contract);
        }

        public static void unlink(Contract contract,
                                  ProfessionalGroup professionalGroup) {
            professionalGroup._getContracts().remove(contract);
            contract.setProfessionalGroup(null);
        }

    }


    public static class Hire {
        public static void link(Contract contract, Mechanic mechanic) {
            contract.setMechanic(mechanic);
            mechanic.setContractInForce(contract);
        }

        public static void unlink(Contract contract, Mechanic mechanic) {
            mechanic.setContractInForce(null);
        }

    }

    public static class Run {
        public static void link(Payroll payroll, Contract contract) {
            contract._getPayrolls().add(payroll);
            payroll.setContract(contract);
        }

        public static void unlink(Contract contract, Payroll payroll) {
            contract._getPayrolls().remove(payroll);
            payroll.setContract(null);
        }
    }

    public static class Type {
        public static void link(Contract contract, ContractType contractType) {
            contract.setContractType(contractType);
            contractType._getContracts().add(contract);
        }

        public static void unlink(Contract contract,
                                  ContractType contractType) {
            contractType._getContracts().remove(contract);
            contract.setContractType(null);
        }
    }

}
