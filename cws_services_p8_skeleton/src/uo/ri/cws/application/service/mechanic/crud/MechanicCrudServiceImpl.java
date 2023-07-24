package uo.ri.cws.application.service.mechanic.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.command.*;
import uo.ri.cws.application.util.command.CommandExecutor;

import java.util.List;
import java.util.Optional;

public class MechanicCrudServiceImpl implements MechanicCrudService {

    private final CommandExecutor executor = Factory.executor.forExecutor();

    @Override
    public MechanicDto addMechanic(MechanicDto dto) throws BusinessException {
        return executor.execute(new AddMechanic(dto));
    }

    @Override
    public void updateMechanic(MechanicDto dto) throws BusinessException {
        executor.execute(new UpdateMechanic(dto));
    }

    @Override
    public void deleteMechanic(String id) throws BusinessException {
        executor.execute(new DeleteMechanic(id));
    }

    @Override
    public List<MechanicDto> findAllMechanics() throws BusinessException {
        return executor.execute(new FindAllMechanics());
    }


    @Override
    public Optional<MechanicDto> findMechanicById(String id)
            throws BusinessException {
        return executor.execute(new FindMechanicById(id));
    }

    @Override
    public List<MechanicDto> findMechanicsInProfessionalGroups(String name)
            throws BusinessException {
        return executor.execute(new FindMechanicsinProfessionalGroups(name));
    }

    @Override
    public List<MechanicDto> findMechanicsWithContractInForceInContractType(
            String permanent) throws BusinessException {
        return executor.execute(new FindInForceInContractType(permanent));
    }

    @Override
    public List<MechanicDto> findMechanicsInForce() throws BusinessException {
        return executor.execute(new FindMechanicsInForce());
    }

}