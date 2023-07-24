package uo.ri.cws.application.business.mechanic.crud;

import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.command.AddMechanic;
import uo.ri.cws.application.business.mechanic.crud.command.DeleteMechanic;
import uo.ri.cws.application.business.mechanic.crud.command.FindAllMechanics;
import uo.ri.cws.application.business.mechanic.crud.command.UpdateMechanic;

import java.util.List;
import java.util.Optional;

public class MechanicServiceImpl implements MechanicService {
    @Override
    public MechanicBLDto addMechanic(MechanicBLDto mechanic) throws BusinessException {
        return new AddMechanic(mechanic).execute();
    }

    @Override
    public void deleteMechanic(String idMechanic) throws BusinessException {
        new DeleteMechanic(idMechanic).execute();
    }

    @Override
    public void updateMechanic(MechanicBLDto mechanic) throws BusinessException {
        new UpdateMechanic(mechanic).execute();
    }

    @Override
    public Optional<MechanicBLDto> findMechanicById(String idMechanic) throws BusinessException {
        return Optional.empty();
    }

    @Override
    public Optional<MechanicBLDto> findMechanicByDni(String dniMechanic) throws BusinessException {
        return Optional.empty();
    }

    @Override
    public List<MechanicBLDto> findAllMechanics() throws BusinessException {
        return new FindAllMechanics().execute();
    }
}
