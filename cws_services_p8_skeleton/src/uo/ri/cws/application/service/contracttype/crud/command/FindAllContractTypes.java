package uo.ri.cws.application.service.contracttype.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

import java.util.List;

public class FindAllContractTypes
        implements Command<List<ContractTypeService.ContractTypeDto>> {


    @Override
    public List<ContractTypeService.ContractTypeDto> execute()
            throws BusinessException {
        return DtoAssembler.toContractTypeDtoList(
                Factory.repository.forContractType().findAll());
    }
}
