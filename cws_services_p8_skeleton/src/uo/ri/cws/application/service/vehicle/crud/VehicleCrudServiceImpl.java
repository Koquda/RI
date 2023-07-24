package uo.ri.cws.application.service.vehicle.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.commands.FindVehicleByPlate;
import uo.ri.cws.application.util.command.CommandExecutor;

import java.util.Optional;

public class VehicleCrudServiceImpl implements VehicleCrudService {

    private final CommandExecutor executor = Factory.executor.forExecutor();

    @Override
    public Optional<VehicleDto> findVehicleByPlate(String plate)
            throws BusinessException {
        return executor.execute(new FindVehicleByPlate(plate));
    }

}
