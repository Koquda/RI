package uo.ri.cws.application.repository;

import uo.ri.cws.domain.Vehicle;

import java.util.Optional;

public interface VehicleRepository extends Repository<Vehicle> {

    Optional<Vehicle> findByPlate(String plate);

}
