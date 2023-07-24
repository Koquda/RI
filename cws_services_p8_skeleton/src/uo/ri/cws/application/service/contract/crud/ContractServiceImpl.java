package uo.ri.cws.application.service.contract.crud;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contract.crud.command.*;

import java.util.List;
import java.util.Optional;

public class ContractServiceImpl implements ContractService {
    @Override
    public ContractDto addContract(ContractDto c) throws BusinessException {
        return Factory.executor.forExecutor().execute(new AddContract(c));
    }

    @Override
    public void updateContract(ContractDto dto) throws BusinessException {
        Factory.executor.forExecutor().execute(new UpdateContract(dto));
    }

    @Override
    public void deleteContract(String id) throws BusinessException {
        Factory.executor.forExecutor().execute(new DeleteContract(id));
    }

    @Override
    public void terminateContract(String contractId) throws BusinessException {
        Factory.executor.forExecutor()
                .execute(new TerminateContract(contractId));
    }

    @Override
    public Optional<ContractDto> findContractById(String id)
            throws BusinessException {
        return Factory.executor.forExecutor().execute(new FindContractById(id));
    }

    @Override
    public List<ContractSummaryDto> findContractsByMechanicDni(
            String mechanicDni) throws BusinessException {
        return Factory.executor.forExecutor()
                .execute(new FindContractsByMechanic(mechanicDni));
    }

    @Override
    public List<ContractSummaryDto> findAllContracts()
            throws BusinessException {
        return Factory.executor.forExecutor().execute(new FindAllContracts());
    }
}
