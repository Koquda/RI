package uo.ri.cws.application.service.contract.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;

import java.util.List;

public class FindAllContracts
        implements Command<List<ContractService.ContractSummaryDto>> {
    @Override
    public List<ContractService.ContractSummaryDto> execute()
            throws BusinessException {
        List<Contract> contracts = Factory.repository.forContract().findAll();
        return DtoAssembler.toContractSummaryDtoList(contracts);
    }
}
