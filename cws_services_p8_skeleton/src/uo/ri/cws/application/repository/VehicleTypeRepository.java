package uo.ri.cws.application.repository;

import uo.ri.cws.domain.VehicleType;

import java.util.List;

public interface VehicleTypeRepository extends Repository<VehicleType> {

    List<VehicleType> findAll();

}
